## Key in the auth token

## Keycloak mapper remaps it to a new key in the keycloak mapper

e.g. from uuid_remote -> objectGUID.

prefix + auth_claim.uid -> keycloak_user.username

**FIXME: What to do if auth_claim.uid is null! -> leads to username conflicts in Keycloak**

## Java plugin maps keycloak attribute to UDM attribute.

e.g. from objectGUID -> UniventionObjectIdentifier


keycloak_user.username -> udm_user.username
keycloak_user.firstname -> udm_user.firstname
keycloak_user.lastname -> udm_user.lastname
keycloak_user.password -> udm_user.password
keycloak_user.e-mail -> udm_user.e-mail
"Shadow copy of user" -> udm_user.description

keycloak_user[SourceUserPrimaryID_KeycloakKey] -> udm_user[SourceUserPrimaryID_UDMKey]
keycloak_user[SourceIdentityProviderID_KeycloakKey] -> udm_user[SourceIdentityProviderID_UDMKey]

### Proposed renamings

"objectGUID" = SourceUserPrimaryID_KeycloakKey (no need for a variable)

remSourceID_key ->  SourceIdentityProviderID_KeycloakAndUDMKey

remIdGUID_key -> SourceUserPrimaryID_UDMKey
remSourceID_key -> SourceIdentityProviderID_KeycloakAndUDMKey

**FIXME don't hard-code "objectGUID", instead move the mapper functionality from the Java code to the mapper.**
-> Basically the two shadow accounts have the same attribute names.
The mapper is used to map the auth token attribute to the shadow account attributes.
