/*
  Copyright 2021-2025 Univention GmbH

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

import de.univention.udm.UniventionDirectoryManagerClient;
import de.univention.udm.UniventionDirectoryManagerClientFactory;
import de.univention.udm.models.User;
import de.univention.udm.models.UserSearchParams;
import de.univention.udm.models.UserSearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserManager;
import org.keycloak.models.UserModel;
import org.keycloak.storage.ldap.idm.store.ldap.LDAPUtil;

// TODO: Make sure that it even makes sense to implement this SPI
// Since we are not authenticating here,
// it is rather likely that some other SPI
// perhaps the user storage spi would be better for this job
public class UniventionAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(UniventionAuthenticator.class);
    private  final UniventionDirectoryManagerClientFactory udmClientFactory;
    // TODO: Make this into a config class instead of a bare list.
    private final UserManager userManager;


    public UniventionAuthenticator(UserManager userManager, UniventionDirectoryManagerClientFactory udmClientFactory) {
        this.userManager = userManager;
        this.udmClientFactory = udmClientFactory;
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        logger.debugf("Univention Authenticator, action has been called.");
    }

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        logger.debugf("Univention Authenticator, authenticate has been called.");

        List<String> config = getValidConfig(context);
        UserModel user = context.getUser();
        logger.debugf("Keycloak user object: %s", user.getAttributes());
        String firstname = user.getFirstName();
        String lastname = user.getLastName();
        String username = user.getUsername();
        String email = user.getEmail();

        String sourceIdentityProviderID_KeycloakAndUDMKey = config.get(3);
        String sourceUserPrimaryID_UDMKey = config.get(4);
        String UDMUserPiramryGroupDn = config.get(5);

        logger.infof(
            """
            Federated user attempted to log in: username: %s, firstname: %s, lastname: %s, \
            email: %s, remSourceIden_key: %s, remObjIden_key: %s, userAttributes: %s
            """,
            username,
            firstname,
            lastname,
            email,
            defaultGroupDn,
            sourceIdentityProviderID_KeycloakAndUDMKey,
            sourceUserPrimaryID_UDMKey,
            UDMUserPiramryGroupDn,

            user.getAttributes()
        );
        // The IDP doesn't provide a password attribute,
        // and the KC UserModel doesn't have such field either,
        // but UDM curently needs a non null password, so we generate one
        // TODO: Get rid of this as soon as it is possible to create a user
        // via UDM without a password
        String password = generateRandomPassword();

        // TODO: Find out if this is even the right way of handling the Ad-hoc federation data
        // Univention Ad-Hoc Federation specific attributes:
        // TODO: If this is empty, fail and delete the shadow user in keycloak
        String remIdGUID_value = user.getFirstAttribute("objectGUID");

        // TODO: Do we care If this is null?
        // TODO: Test what happens if this is null: Misconfigure the mapper and find out what happens.
        String remSourceID_value =  user.getFirstAttribute(sourceIdentityProviderID_KeycloakAndUDMKey);

        // This attribute is only for Keycloak, this won't be propagated via UDM
        // TODO: Currently it doesn't seem to be possible to set a mapper for this in KC
        // because normal attribute mappers would write into the { "attributes": { "federationLink"}} field
        // instead of the top level { "federationLink"} field. Only keep this mechanism/code
        // if it's absolutely certain, that there is no way to simply configure keycloak to set the desired field.
        String univentionTargetFederationLink =  user.getFirstAttribute("univentionTargetFederationLink");

        logger.infof(
            "Additional user attributes for username: %s, sourceUserPrimaryID_UDMKey %s, remIdGUID_value %s, sourceIdentityProviderID_KeycloakAndUDMKey %s, remSourceID_value %s, univentionTargetFederationLink: %s",
            username,
            sourceUserPrimaryID_UDMKey,
            remIdGUID_value,
                sourceIdentityProviderID_KeycloakAndUDMKey,
            remSourceID_value,
            univentionTargetFederationLink
        );

        String decoded_remoteGUID;
        try{
            decoded_remoteGUID = LDAPUtil.decodeObjectGUID(Base64.getDecoder().decode(remIdGUID_value.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException | ArrayIndexOutOfBoundsException | IllegalArgumentException e){
            logger.warnf("Failed to decode remote GUID of username: %s, guid: %s", username, remIdGUID_value);
            decoded_remoteGUID = remIdGUID_value;
            //propagate the error
            context.failure(AuthenticationFlowError.INTERNAL_ERROR);
            return;
        }

        // TODO: When the UDM allows it, avoid passing the password here
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("firstname", firstname);
        properties.put("lastname", lastname);
        properties.put("password", password);
        properties.put("e-mail", new String[]{email});
        properties.put("description", "Shadow copy of user");
        properties.put(sourceUserPrimaryID_UDMKey, decoded_remoteGUID);
        properties.put(sourceIdentityProviderID_KeycloakAndUDMKey, remSourceID_value);

        // Add default group DN if configured
        if (UDMUserPiramryGroupDn != null && !UDMUserPiramryGroupDn.isEmpty()) {
            properties.put("primaryGroup", UDMUserPiramryGroupDn);
        }

        User udmUser = new User();
        udmUser.setProperties(properties);


        // TODO: Review and re-test this whole error handling here,
        // probably there are more meaningfull errors that we could propagate here,
        // and ENSURE THAT THIS STOPS the flow!!!
        // So far the observation is that s, simply returning after failure here doesn't stop the flow
        // which has grave consequences, so fix that!
        UniventionDirectoryManagerClient udmClient = udmClientFactory.create(
            config.get(0),  // baseUrl
            config.get(1),  // username
            config.get(2)   // password
        );
        try {
            // Check if user exists first
            if (existsRemoteUser(udmClient, username)) {
                logger.infof("User with username: %s already exists in UDM and does not need to be created", username);
                context.success();
                return;
            }
            User createdUser = udmClient.createUser(udmUser);
            if (createdUser == null) {
                logger.errorf("Failed to create user with username: %s", username);
                context.failure(AuthenticationFlowError.INTERNAL_ERROR);
                userManager.removeUser(context.getRealm(), context.getUser());
                logger.infof("Removed keycloak shadow user because the UDM shadow user could not be created and both shadow databases must stay in sync. Username: %s", username);
                return;
            }

            // Update Keycloak user with UDM attributes
            user.setSingleAttribute("LDAP_ID", createdUser.getUuid().toString());
            user.setSingleAttribute("LDAP_ENTRY_DN", createdUser.getDn());
            user.setSingleAttribute("uid", username);
            user.setFederationLink(univentionTargetFederationLink);
            logger.infof("Federated user ad-hoc provisioning for user with username: %s was succesful", username);
            context.success();
        } catch (InterruptedException | IOException e) {
            logger.errorf("Failed to create user with username: %s, error: %s", username, e);
            context.failure(AuthenticationFlowError.INTERNAL_ERROR);
            userManager.removeUser(context.getRealm(), context.getUser());
            logger.infof("Removed keycloak shadow user because the UDM shadow user could not be identified/created and both shadow databases must stay in sync. Username: %s", username);
        }
    }

    @Override
    public void close() {
        logger.debugf("Univention Authenticator, close has been called.");
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


    private boolean existsRemoteUser(UniventionDirectoryManagerClient client, String username) throws IOException, InterruptedException {
        UserSearchParams params = UserSearchParams.builder()
            .query(Map.of("username", username))
            .scope("sub")
            .build();

        UserSearchResult result = client.searchUsers(params);
        return !result.getUsers().isEmpty();
    }

    private String generateRandomPassword() {
        byte[] token = new byte[256];
        new java.security.SecureRandom().nextBytes(token);
        return Base64.getEncoder().encodeToString(token);
    }

    // TODO: has to be public for the moment to mock it from the tests.
    // While refactoring the config, we should find a better alternative. (DependencyInjection)
    // TODO: If somehow possible make sure that we validate the config
    // in configuration time and not here in runtime when it's too late,
    // also the user who configured is long gone by this code is executed
    /*
     * Ideally this validation should happen in the factory, but
     * how should we validate the config, if AuthenticatorFactory doesn't have
     * a method which can get on the config?
     */
    public List<String> getValidConfig(AuthenticationFlowContext context) {

        AuthenticatorConfigModel configModel = context.getAuthenticatorConfig();

        if (null == configModel || null == configModel.getConfig()) {
            logger.errorf("Univention Authenticator ConfigModel is null");

            context.failure(
                    AuthenticationFlowError.CREDENTIAL_SETUP_REQUIRED
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
                logger.errorf("Fereration ad-hoc provisioning udmConfig value: %s is null", i);
                /* TODO: Return a more useful jakarta.ws.rs.core.Response response or something better*/
                context.failure(AuthenticationFlowError.CREDENTIAL_SETUP_REQUIRED);
                /* maybe throw an exception? */
            }
            udmConfig.add(value);
        }
        logger.debugf("Federation ad-hoc provisioning udmConfig: %s", udmConfig);
        // Check if the URI is valid or available?

        return udmConfig;
    }
}
