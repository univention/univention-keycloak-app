/*
  Copyright 2021-2024 Univention GmbH

  https://www.univention.de/

  All rights reserved.

  The source code of this program is made available
  under the terms of the GNU Affero General Public License version 3
  (GNU AGPL V3) as published by the Free Software Foundation.

  Binary versions of this program provided by Univention to you as
  well as other copyrighted, protected or trademarked materials like
  Logos, graphics, fonts, specific documentations and configurations,
  cryptographic keys etc. are subject to a license agreement between
  you and Univention and not subject to the GNU AGPL V3.

  In the case you use this program under the terms of the GNU AGPL V3,
  the program is provided in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
  GNU Affero General Public License for more details.

  You should have received a copy of the GNU Affero General Public
  License with the Debian GNU/Linux or Univention distribution in file
  /usr/share/common-licenses/AGPL-3; if not, see
  <https://www.gnu.org/licenses/>.
*/


package de.univention.authenticator;

import org.openapitools.client.ApiClient;
import org.openapitools.client.api.UsersUserApi;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.UsersUser;
import org.openapitools.client.model.UsersUserList;
import org.openapitools.client.model.UsersUserListEmbedded;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Base64;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.ldap.idm.store.ldap.LDAPUtil;

// TODO: Make sure that it even makes sense to implement this SPI
// Since we are not authenticating here,
// it is rather likely that some other SPI
// perhaps the user storage spi would be better for this job
public class UniventionAuthenticator implements Authenticator {

    private static final String REM_ID_GUID_KEY_DEFAULT = "univentionObjectIdentifier";
    private static final String REM_SOURCE_ID_KEY_DEFAULT = "univentionSourceIAM";

    private static final Logger logger =
        Logger.getLogger(UniventionAuthenticator.class);

    @Override
    public void action(AuthenticationFlowContext context) {
        logger.infof("Univention Authenticator, action has been called.");
    }

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        logger.infof("Univention Authenticator, authenticate has been called.");
        List<String> config = getValidConfig(context);
        UsersUserApi api = getUsersUserApiReference(config);

        UserModel user = context.getUser();
        String firstname = user.getFirstName();
        String lastname = user.getLastName();
        String username = user.getUsername();
        String email = user.getEmail();

        String remIdGUID_key = Objects.requireNonNullElse(
            System.getenv("KEYCLOAK_FEDERATION_REMOTE_IDENTIFIER"), REM_ID_GUID_KEY_DEFAULT);
        String remSourceID_key = Objects.requireNonNullElse(
            System.getenv("KEYCLOAK_FEDERATION_SOURCE_IDENTIFIER"), REM_SOURCE_ID_KEY_DEFAULT);

        logger.infof("User:" + firstname);
        logger.infof("lastname:" + lastname);
        logger.infof("username:" + username);
        logger.infof("user: " + user.getAttributes());
        logger.infof("remSourceIden_key: " + remSourceID_key);
        logger.infof("remObjIden_key: " + remIdGUID_key);
        // The IDP doesn't provide a password attribute,
        // and the KC UserModel doesn't have such field either,
        // but UDM curently needs a non null password, so we generate one
        // TODO: Get rid of this as soon as it is possible to create a user
        // via UDM without a password
        int numberOfRandomBytes = 256;
        byte[] token = new byte[numberOfRandomBytes];
        java.security.SecureRandom secureRandom = new java.security.SecureRandom();
        secureRandom.nextBytes(token);
        String password = java.util.Base64.getEncoder().encodeToString(token); // Base64 encoding

        // TODO: Find out if this is even the right way of handling the Ad-hoc federation data
        // Univention Ad-Hoc Federation specific attributes:
        String remIdGUID_value = user.getFirstAttribute("objectGUID");
        String remSourceID_value =  user.getFirstAttribute(remSourceID_key);

        // This attribute is only for Keycloak, this won't be propagated via UDM
        // TODO: Currently it doesn't seem to be possible to set a mapper for this in KC
        // because normal attribute mappers would write into the { "attributes": { "federationLink"}} field
        // instead of the top level { "federationLink"} field. Only keep this mechanism/code
        // if it's absolutely certain, that there is no way to simply configure keycloak to set the desired field.
        String univentionTargetFederationLink =  user.getFirstAttribute("univentionTargetFederationLink");

        logger.infof(remIdGUID_key + ":" + remIdGUID_value);
        logger.infof(remSourceID_key + ":" + remSourceID_value);
        //logger.infof("univentionTenant:" + univentionTenant);
        logger.infof("targetFederationLink:" + univentionTargetFederationLink);
        // TODO: Perhaps do some real life performance testing
        // and change this to debug remove if not needed.
        logger.infof("User attempted login. First name: %s, Last name: %s, Username: %s, E-Mail: %s",
                     firstname, lastname, username, email);

        // TODO: (Assumption) The approach to handle this could be improved if
        // the concern of handling the encoding/decoding is implemented as part
        // of the attribute mapping. This code could then just use the attribute
        // value without worrying about the encoding at all.
        String decoded_remoteGUID;
        try{
            decoded_remoteGUID = LDAPUtil.decodeObjectGUID(Base64.getDecoder().decode(remIdGUID_value.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e){
            decoded_remoteGUID = remIdGUID_value;
            // propagate the error
            // TODO: Interim commented out to allow to use a value which is not base64 encoded
            // context.failure(AuthenticationFlowError.INTERNAL_ERROR);
            logger.infof(remIdGUID_key + " was not base64 encoded. Using value without decoding it.");
        }

        // TODO: When the UDM allows it, avoid passing the password here
        // TODO: The set of attributes is currently hardcoded.
        // The UDM does allow many more attributes and a customization may make attributes
        // required which are not provided here. An option might be to make this dynamic and
        // try to use all configured attributes or filter the available attributes based on
        // the generated API client.
        Map<String, Object> userData = Map.of(
            "firstname", firstname,
            "lastname", lastname,
            "username", username,
            "password", password, // random password ???
            "e-mail", new String[]{email},
            "description", "Shadow copy of user",

            remIdGUID_key, decoded_remoteGUID,
            remSourceID_key, remSourceID_value
            );

        // TODO: Review and re-test this whole error handling here,
        // probably there are more meaningfull errors that we could propagate here,
        // and ENSURE THAT THIS STOPS the flow!!!
        // So far the observation is that s, simply returning after failure here doesn't stop the flow
        // which has grave consequences, so fix that!
        UsersUser userInUcs = createUserViaUDM(api, userData);

        if (null == userInUcs) {
                context.failure(AuthenticationFlowError.IDENTITY_PROVIDER_NOT_FOUND);
                return;
        }

        String uuid = userInUcs.getUuid().toString();
        String dn = userInUcs.getDn();
        user.setSingleAttribute("LDAP_ID", uuid);
        user.setSingleAttribute("LDAP_ENTRY_DN", dn);
        user.setSingleAttribute("uid", username);
        user.setFederationLink(univentionTargetFederationLink);

        context.success();
    }

    @Override
    public void close() {
        logger.infof("Univention Authenticator, close has been called.");
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
       return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    }

    @Override
    public boolean requiresUser() {
        /* The KC documentation appears to be buggy,
         * it is talking about a non-existent "AuthenticatorContext"
         * Assuming that was meant to be the "AuthenticationFlowContext",
         * but the statement about .getUser() is true,
         * then this method should return true.
         */
        return true;
    }


    // TODO: If somehow possible make sure that we validate the config
    // in configuration time and not here in runtime when it's too late,
    // also the user who configured is long gone by this code is executed
    /*
     * Ideally this validation should happen in the factory, but
     * how should we validate the config, if AuthenticatorFactory doesn't have
     * a method which can work on the config?
     *
     */
    private List<String> getValidConfig(AuthenticationFlowContext context) {

        AuthenticatorConfigModel configModel = context.getAuthenticatorConfig();

        if (null == configModel || null == configModel.getConfig()) {
            logger.infof("Univention Authenticator, ConfigModell is null ");

            context.failure(
                    AuthenticationFlowError.IDENTITY_PROVIDER_NOT_FOUND
                    /* TODO: Return a more useful jakarta.ws.rs.core.Response response */);
        }
        Map<String, String> config = configModel.getConfig();

        List<String> udmConfig = new ArrayList<String>();
        /* TODO: Despite the check above somehow this can still raise a NullPointerExeption
         * most probably the context.failure doesn't terminate the execution?
         */
        for (String i: UniventionAuthenticatorFactory.configPropertyNames) {
            String value = config.get(i);
            if (null == value) {
                /* TODO: Return a more useful jakarta.ws.rs.core.Response response or something better*/
                context.failure(AuthenticationFlowError.IDENTITY_PROVIDER_NOT_FOUND);
                /* maybe throw an exception? */
            }
            udmConfig.add(value);
        }
        // Check if the URI is valid or available?

        return udmConfig;
    }

    private UsersUserApi getUsersUserApiReference(List<String> endpointUsernamePassword) {
        UsersUserApi api = new UsersUserApi();
        ApiClient client = api.getApiClient();

        // TODO: There could be a number of better and fool proof ways of unpacking the endpoint data
        client.setBasePath(endpointUsernamePassword.get(0));
        client.setUsername(endpointUsernamePassword.get(1));
        client.setPassword(endpointUsernamePassword.get(2));
        return api;
    }

    // TODO: The original Phoenix plans called for not just a "create" but
    // also a "query" and further methods but at this stage it's a bit unclear why
    // and so far those were not needed.
    // Review those plans and see if further methods need to be implemented
    private UsersUser createUserViaUDM(UsersUserApi api, Map<String, Object> userData) {
        UsersUser userTemplate = null;
        try {
            userTemplate = api.udmUsersUserObjectTemplate(null, null, null, null);
        } catch(ApiException e) {
            logger.errorf("Failed to get UDM template: %s", e);
            // TODO: Better error propagation
            return null;
        }

        userTemplate.getProperties().putAll(userData);
        UsersUser result = null;
        try {
            result = api.udmUsersUserObjectCreate(userTemplate);
            logger.infof("User create result: %s", result);
        } catch(ApiException e) {
            logger.errorf("Failed to create user via UDM: %s", e);
            // TODO: Better error propagation
            return null;
        }
        return result;
    }

    private boolean existsRemoteUser(UsersUserApi api, Map<String, Object> userData){
        Map<String, String> query = Map.of(
//            "firstname", userData.get("firstname"),
//            "lastname", userData.get("lastname"),
            "username", userData.get("username").toString()
            // TODO: SEARCH HASH PASSWORD
//            "password", userData.get("password")
            //"e-mail", new String[]{email},


        //    "univentionRemoteIdentifier", univentionRemoteIdentifier,
        //    "univentionCustomer", univentionCustomer,
        //    "univentionTenant", univentionTenant
            );

        try {
            UsersUserList listUser = api.udmUsersUserObjectSearch(null, null, "sub", query, true, "", "en_EN", "", "");
            UsersUserListEmbedded luser = listUser.getEmbedded();
            List<UsersUser> listUserl = luser.getUdmColonObject();
            if (listUserl != null && listUserl.size() > 0){
                logger.infof("Univention Authenticator existsRemoteUser, listUser is greater than 0.");
                logger.infof("Univention Authenticator existsRemoteUser, %s", listUserl.get(0));
                return true;
            } else {
                logger.infof("Univention Authenticator, listUser is 0 or null.");
                return false;
            }
        } catch(ApiException e) {
            logger.errorf("Failed to get users via UDM: %s", e);
            // TODO: Better error propagation
            return false;
        }

    }
}
