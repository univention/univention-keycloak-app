#!/usr/bin/env python3
"""
Keycloak Ad-Hoc Federation Setup Tool

This script automates the setup of Keycloak ad-hoc federation between realms, including:
- Creates a dummy realm with a test user (test_adhoc:univention)
- Configures federation client and authentication flow
- Sets up Identity Provider (IDP) with OIDC
- Configures Univention authentication and UDM connection
- Establishes IDP mappers for user attributes

Usage:
    ./setup_adhoc_provisioning.py \
        --keycloak-url https://keycloak.example.com \
        --admin-username admin \
        --admin-password secret \
        --udm-url http://nubus-udm-rest-api:9979/udm \
        --udm-username cn=admin \
        --udm-password secret

Optional arguments:
    --existing-realm  Name of existing realm (default: nubus)
    --dummy-realm    Name of dummy realm to create (default: adhoc)


Dependencies:
    pip install python-keycloak
"""

import argparse
import json
import logging
from typing import Any, Dict

from keycloak import KeycloakAdmin
from keycloak.exceptions import KeycloakError, KeycloakPostError


# Set up logging
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s",
)
logger = logging.getLogger(__name__)


def parse_args():
    parser = argparse.ArgumentParser(description="Setup Keycloak federation")
    parser.add_argument("--keycloak-url", required=True, help="Base Keycloak URL")
    parser.add_argument(
        "--admin-username", default="admin", help="Keycloak admin username",
    )
    parser.add_argument(
        "--admin-password", required=True, help="Keycloak admin password",
    )
    parser.add_argument("--existing-realm", default="nubus", help="Existing realm name")
    parser.add_argument("--dummy-realm", default="adhoc", help="Dummy realm name")
    parser.add_argument("--udm-url", required=True, help="UDM API URL")
    parser.add_argument("--udm-username", required=True, help="UDM username")
    parser.add_argument("--udm-password", required=True, help="UDM password")
    return parser.parse_args()


def get_realm_payload(realm: str, keycloak_url: str) -> Dict[str, Any]:
    return {
        "id": realm,
        "realm": realm,
        "enabled": True,
        "loginTheme": "keycloak",
        "browserSecurityHeaders": {
            "contentSecurityPolicyReportOnly": "",
            "xContentTypeOptions": "nosniff",
            "xRobotsTag": "none",
            "xFrameOptions": "SAMEORIGIN",
            "contentSecurityPolicy": f"frame-src 'self'; frame-ancestors 'self' {keycloak_url}; object-src 'none';",
            "xXSSProtection": "1; mode=block",
            "strictTransportSecurity": "max-age=31536000; includeSubDomains",
        },
    }


def get_test_user_payload() -> Dict[str, Any]:
    return {
        "username": "test_adhoc",
        "enabled": True,
        "firstName": "Test",
        "lastName": "User",
        "email": "test@adhoc.com",
        "emailVerified": True,
        "credentials": [
            {
                "type": "password",
                "value": "univention",
                "temporary": False,
            },
        ],
        "attributes": {
            "uuid_remote": "RSlyYLgv5Em10MKDijkDZQ==",
            "uid": "test_adhoc",
        },
    }


def get_idp_payload(keycloak_url: str, dummy_realm: str) -> Dict[str, Any]:
    return {
        "alias": f"oidc-{dummy_realm}",
        "displayName": f"OIDC {dummy_realm}",
        "providerId": "oidc",
        "enabled": True,
        "trustEmail": True,
        "storeToken": False,
        "addReadTokenRoleOnCreate": False,
        "firstBrokerLoginFlowAlias": "adhoc",
        "config": {
            "clientId": "federation-client",
            "clientSecret": "",  # Will be set after client creation
            "tokenUrl": f"{keycloak_url}/realms/{dummy_realm}/protocol/openid-connect/token",
            "authorizationUrl": f"{keycloak_url}/realms/{dummy_realm}/protocol/openid-connect/auth",
            "userInfoUrl": f"{keycloak_url}/realms/{dummy_realm}/protocol/openid-connect/userinfo",
            "defaultScope": "openid profile email",
            "validateSignature": "true",
            "useJwksUrl": "true",
            "jwksUrl": f"{keycloak_url}/realms/{dummy_realm}/protocol/openid-connect/certs",
        },
    }


def create_keycloak_admin(server_url, username, password, realm_name, user_realm_name):
    return KeycloakAdmin(
        server_url=server_url,
        username=username,
        password=password,
        realm_name=realm_name,
        client_id="admin-cli",
        verify=True,
        user_realm_name=user_realm_name,
    )


def setup_univention_auth_flow(
    kc: KeycloakAdmin, realm: str, udm_url: str, udm_username: str, udm_password: str,
) -> None:
    """Set up the Univention authentication flow based on First Broker Login"""
    flow_alias = "adhoc"

    # Copy the first broker login flow
    logger.info("Creating authentication flow by copying 'first broker login'")
    payload_authflow = {"newName": flow_alias}
    try:
        kc.copy_authentication_flow(
            payload=payload_authflow,
            flow_alias="first broker login",
        )
    except KeycloakError as e:
        if e.response_code != 409:  # Ignore if already exists
            raise
        logger.info("Flow already exists, continuing with configuration")

    # Get the flow executions
    executions = kc.get_authentication_flow_executions(flow_alias)

    # Configure Review Profile to DISABLED
    review_profile = next(
        (e for e in executions if e["displayName"] == "Review Profile"),
        None,
    )
    if review_profile:
        logger.info("Configuring Review Profile")
        config = {
            "id": review_profile["id"],
            "requirement": "DISABLED",
            "displayName": review_profile["displayName"],
            "requirementChoices": review_profile["requirementChoices"],
            "configurable": review_profile.get("configurable", True),
            "providerId": review_profile["providerId"],
            "level": review_profile.get("level", 0),
            "index": review_profile.get("index", 0),
        }
        kc.update_authentication_flow_executions(
            payload=config,
            flow_alias=flow_alias,
        )

    # Add Univention authenticator execution
    logger.info("Adding Univention Authenticator execution")
    exec_payload = {"provider": "univention-authenticator"}
    kc.create_authentication_flow_execution(
        payload=exec_payload,
        flow_alias=flow_alias,
    )

    # Get updated executions to find the new authenticator
    executions = kc.get_authentication_flow_executions(flow_alias)
    ua_execution = next(
        (e for e in executions if e["displayName"] == "Univention Authenticator"),
        None,
    )

    if not ua_execution:
        raise KeycloakError(
            "Univention Authenticator execution not found after creation",
        )

    # Configure Univention Authenticator to REQUIRED
    logger.info("Configuring Univention Authenticator")
    config = {
        "id": ua_execution["id"],
        "requirement": "REQUIRED",
        "displayName": "Univention Authenticator",
        "requirementChoices": ["REQUIRED", "DISABLED"],
        "configurable": True,
        "providerId": "univention-authenticator",
        "level": 0,
        "index": 2,
        "priority": 30,
    }
    kc.update_authentication_flow_executions(
        payload=config,
        flow_alias=flow_alias,
    )

    # Configure UDM connection
    logger.info("Configuring UDM connection")
    udm_config = {
        "alias": "udm-config",
        "config": {
            "udm_endpoint": udm_url,
            "udm_user": udm_username,
            "udm_password": udm_password,
        },
    }
    try:
        kc.connection.raw_post(
            f'/admin/realms/{realm}/authentication/executions/{ua_execution["id"]}/config',
            data=json.dumps(udm_config),
        )
    except KeycloakPostError as e:
        logger.error(f"Failed to configure UDM connection: {e}")
        raise

    logger.info("Univention authentication flow setup completed")


def setup_user_profile_attributes(kc: KeycloakAdmin, realm: str, is_dummy_realm: bool) -> None:
    logger.info(f"Setting up user profile attributes for realm: {realm}")

    # Get the current user profile
    try:
        user_profile = kc.connection.raw_get(f"/admin/realms/{realm}/users/profile").json()
    except KeycloakError as e:
        logger.error(f"Failed to get user profile for realm {realm}: {e}")
        raise

    # Define the attributes to add
    attributes_to_add = []
    if is_dummy_realm:
        attributes_to_add = [
            {
                "name": "uid",
                "displayName": "${profile.attributes.uid}",
                "permissions": {"view": [], "edit": ["admin"]},
                "validations": {},
                "annotations": {},
                "multivalued": False,
            },
            {
                "name": "uuid_remote",
                "displayName": "${profile.attributes.uuid_remote}",
                "permissions": {"view": [], "edit": ["admin"]},
                "validations": {},
                "annotations": {},
                "multivalued": False,
            },
        ]
    else:
        attributes_to_add = [
            {
                "name": "univentionSourceIAM",
                "displayName": "${profile.attributes.univentionSourceIAM}",
                "permissions": {"view": [], "edit": ["admin"]},
                "validations": {},
                "annotations": {},
                "multivalued": False,
            },
            {
                "name": "objectGUID",
                "displayName": "${profile.attributes.objectGUID}",
                "permissions": {"view": [], "edit": ["admin"]},
                "validations": {},
                "annotations": {},
                "multivalued": False,
            },
        ]

    # Add new attributes to the existing attributes
    existing_attributes = user_profile.get("attributes", [])
    for attr in attributes_to_add:
        if not any(existing_attr["name"] == attr["name"] for existing_attr in existing_attributes):
            existing_attributes.append(attr)
        else:
            logger.info(f"Attribute {attr['name']} already exists in realm {realm}")

    # Update the user profile
    user_profile["attributes"] = existing_attributes
    try:
        kc.connection.raw_put(f"/admin/realms/{realm}/users/profile", data=json.dumps(user_profile))
        logger.info(f"User profile updated for realm {realm}")
    except KeycloakError as e:
        logger.error(f"Failed to update user profile for realm {realm}: {e}")
        raise

    logger.info(f"User profile attributes setup completed for realm: {realm}")


def setup_user_attributes(kc: KeycloakAdmin, realm: str) -> None:
    attributes = [
        {"name": "username", "displayName": "${username}"},
        {"name": "email", "displayName": "${email}"},
        {"name": "firstName", "displayName": "${firstName}"},
        {"name": "lastName", "displayName": "${lastName}"},
        {"name": "univentionSourceIAM", "displayName": "${profile.attributes.univentionSourceIAM}"},
        {"name": "objectGUID", "displayName": "${profile.attributes.objectGUID}"},
    ]

    for attr in attributes:
        scope_name = attr["name"]
        try:
            # Get all client scopes
            client_scopes = kc.get_client_scopes()

            # Check if the client scope already exists
            existing_scope = next((scope for scope in client_scopes if scope['name'] == scope_name), None)

            if existing_scope:
                logger.info(f"Client scope '{scope_name}' already exists")
                scope_id = existing_scope['id']
            else:
                # Create the client scope
                scope_payload = {
                    "name": scope_name,
                    "protocol": "openid-connect",
                    "attributes": {
                        "include.in.token.scope": "true",
                        "display.on.consent.screen": "true",
                    },
                }
                scope_id = kc.create_client_scope(scope_payload)
                logger.info(f"Created client scope: {scope_name}")

            # Check if the mapper already exists
            existing_mappers = kc.get_mappers_from_client_scope(scope_id)
            mapper_exists = any(m['name'] == attr["name"] for m in existing_mappers)

            if not mapper_exists:
                # Add a mapper to the client scope
                mapper_payload = {
                    "name": attr["name"],
                    "protocol": "openid-connect",
                    "protocolMapper": "oidc-usermodel-attribute-mapper",
                    "consentRequired": False,
                    "config": {
                        "userinfo.token.claim": "true",
                        "user.attribute": attr["name"],
                        "id.token.claim": "true",
                        "access.token.claim": "true",
                        "claim.name": attr["name"],
                        "jsonType.label": "String",
                    },
                }
                kc.add_mapper_to_client_scope(scope_id, mapper_payload)
                logger.info(f"Added mapper to client scope: {attr['name']}")
            else:
                logger.info(f"Mapper '{attr['name']}' already exists for client scope '{scope_name}'")
        except KeycloakError as e:
            logger.error(f"Failed to setup attribute {attr['name']}: {e}")
            raise
    logger.info("User attributes setup completed")


def setup_idp_mappers(kc: KeycloakAdmin, realm: str, dummy_realm: str) -> None:
    mappers = [
        {
            "name": "uid",
            "identityProviderMapper": "oidc-username-idp-mapper",
            "config": {
                "template": "external-${ALIAS}-${CLAIM.sAMAccountName}",
                "syncMode": "IMPORT",
            },
        },
        {
            "name": "email",
            "identityProviderMapper": "oidc-user-attribute-idp-mapper",
            "config": {
                "claim": "email",
                "user.attribute": "email",
                "syncMode": "INHERIT",
            },
        },
        {
            "name": "firstName",
            "identityProviderMapper": "oidc-user-attribute-idp-mapper",
            "config": {
                "claim": "given_name",
                "user.attribute": "firstName",
                "syncMode": "INHERIT",
            },
        },
        {
            "name": "lastName",
            "identityProviderMapper": "oidc-user-attribute-idp-mapper",
            "config": {
                "claim": "family_name",
                "user.attribute": "lastName",
                "syncMode": "INHERIT",
            },
        },
        {
            "name": "source_iam",
            "identityProviderMapper": "hardcoded-attribute-idp-mapper",
            "config": {
                "attribute": "univentionSourceIAM",
                "attribute.value": f"Federation from {dummy_realm}",
            },
        },
        {
            "name": "objectid",
            "identityProviderMapper": "oidc-user-attribute-idp-mapper",
            "config": {
                "claim": "uuid_remote",
                "user.attribute": "objectGUID",
                "syncMode": "IMPORT",
            },
        },
    ]

    idp_alias = f"oidc-{dummy_realm}"

    # Get existing mappers for this IDP
    try:
        existing_mappers = kc.get_idp_mappers(idp_alias)
        existing_mapper_names = {mapper['name'] for mapper in existing_mappers}
    except Exception as e:
        logger.warning(f"Could not retrieve existing mappers: {e}")
        existing_mapper_names = set()

    for mapper in mappers:
        mapper["identityProviderAlias"] = idp_alias

        # Only add mapper if it doesn't already exist
        if mapper['name'] not in existing_mapper_names:
            try:
                kc.add_mapper_to_idp(
                    idp_alias=idp_alias,
                    payload=mapper,
                )
                logger.info(f"Added IDP mapper: {mapper['name']}")
            except KeycloakError as e:
                logger.error(f"Failed to add mapper {mapper['name']}: {e}")
        else:
            logger.info(f"IDP mapper {mapper['name']} already exists, skipping")


def setup_client_mappers(kc: KeycloakAdmin, realm: str, client_id: str) -> None:
    mappers = [
        {
            "name": "uid",
            "protocol": "openid-connect",
            "protocolMapper": "oidc-usermodel-attribute-mapper",
            "config": {
                "user.attribute": "uid",
                "claim.name": "sAMAccountName",
                "jsonType.label": "String",
                "access.token.claim": "true",
                "id.token.claim": "true",
            },
        },
        {
            "name": "uuid_remote",
            "protocol": "openid-connect",
            "protocolMapper": "oidc-usermodel-attribute-mapper",
            "config": {
                "user.attribute": "uuid_remote",
                "claim.name": "uuid_remote",
                "jsonType.label": "String",
                "access.token.claim": "true",
                "id.token.claim": "true",
            },
        },
    ]

    # Retrieve existing mappers for this client
    try:
        existing_mappers = kc.get_mappers_from_client(client_id)
        existing_mapper_names = {mapper['name'] for mapper in existing_mappers}
    except Exception as e:
        logger.warning(f"Could not retrieve existing client mappers: {e}")
        existing_mapper_names = set()

    for mapper in mappers:
        # Only add mapper if it doesn't already exist
        if mapper['name'] not in existing_mapper_names:
            try:
                kc.add_mapper_to_client(client_id, mapper)
                logger.info(f"Added client mapper: {mapper['name']}")
            except KeycloakError as e:
                logger.error(f"Failed to add client mapper {mapper['name']}: {e}")
        else:
            logger.info(f"Client mapper {mapper['name']} already exists, skipping")


def main():
    args = parse_args()
    logger.info("Starting Keycloak federation setup")

    # Connection to master realm for global operations
    logger.info(f"Connecting to Keycloak master realm at {args.keycloak_url}")
    kc_master = create_keycloak_admin(
        server_url=args.keycloak_url,
        username=args.admin_username,
        password=args.admin_password,
        realm_name="master",
        user_realm_name="master",
    )

    # Create dummy realm
    logger.info(f"Creating dummy realm: {args.dummy_realm}")
    kc_master.create_realm(
        payload=get_realm_payload(args.dummy_realm, args.keycloak_url),
        skip_exists=True,
    )

    # Connection to dummy realm
    logger.info(f"Connecting to dummy realm: {args.dummy_realm}")
    kc_dummy = create_keycloak_admin(
        server_url=args.keycloak_url,
        username=args.admin_username,
        password=args.admin_password,
        realm_name=args.dummy_realm,
        user_realm_name="master",
    )

    # Set up user profile attributes for dummy realm
    setup_user_profile_attributes(kc_dummy, args.dummy_realm, is_dummy_realm=True)

    # Create test user in dummy realm
    logger.info("Creating test user in dummy realm")
    try:
        kc_dummy.create_user(get_test_user_payload())
    except KeycloakError as e:
        if e.response_code != 409:  # Ignore if user already exists
            raise

    # Create federation client in dummy realm
    logger.info("Creating federation client")
    client_payload = {
        "clientId": "federation-client",
        "enabled": True,
        "protocol": "openid-connect",
        "publicClient": False,
        "directAccessGrantsEnabled": True,
        "standardFlowEnabled": True,
        "implicitFlowEnabled": False,
        "serviceAccountsEnabled": False,
        "redirectUris": ["*"],
    }

    try:
        client = kc_dummy.create_client(client_payload)
    except KeycloakError as e:
        if e.response_code != 409:
            raise
    clients = kc_dummy.get_clients()
    client = next(c for c in clients if c["clientId"] == "federation-client")
    logger.info("Retrieving client secret")
    client_secret = kc_dummy.get_client_secrets(client["id"])["value"]

    # Connection to existing realm
    logger.info(f"Connecting to existing realm: {args.existing_realm}")
    kc_existing = create_keycloak_admin(
        server_url=args.keycloak_url,
        username=args.admin_username,
        password=args.admin_password,
        realm_name=args.existing_realm,
        user_realm_name="master",
    )

    # Set up user profile attributes for existing realm
    setup_user_profile_attributes(kc_existing, args.existing_realm, is_dummy_realm=False)

    # Set up user attributes
    setup_user_attributes(kc_existing, args.existing_realm)

    # Set up authentication flow
    logger.info("Setting up Univention authenticator flow")
    setup_univention_auth_flow(
        kc_existing,
        args.existing_realm,
        args.udm_url,
        args.udm_username,
        args.udm_password,
    )

    # Set up IDP
    logger.info(f"Setting up Identity Provider in realm: {args.existing_realm}")
    idp_payload = get_idp_payload(args.keycloak_url, args.dummy_realm)
    idp_payload["config"]["clientSecret"] = client_secret

    try:
        kc_existing.create_idp(idp_payload)
    except KeycloakError as e:
        if e.response_code != 409:
            raise

    # Set up IDP mappers
    logger.info("Setting up IDP mappers")
    setup_idp_mappers(kc_existing, args.existing_realm, args.dummy_realm)

    # Set up client mappers for federation client
    client_id = kc_dummy.get_client_id("federation-client")
    setup_client_mappers(kc_dummy, args.dummy_realm, client_id)

    logger.info("Keycloak federation setup completed successfully")


if __name__ == "__main__":
    try:
        main()
    except Exception as e:
        logger.error(f"Setup failed: {e}", exc_info=True)
        raise
