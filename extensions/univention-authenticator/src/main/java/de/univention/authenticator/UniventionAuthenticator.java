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

import de.univention.authenticator.config.UniventionAuthenticatorConfig;
import de.univention.authenticator.config.UniventionAuthenticatorConfigFactory;
import de.univention.udm.UniventionDirectoryManagerClient;
import de.univention.udm.UniventionDirectoryManagerClientFactory;
import de.univention.udm.models.User;
import de.univention.udm.models.UserSearchParams;
import de.univention.udm.models.UserSearchResult;

import java.io.IOException;
import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserManager;
import org.keycloak.models.UserModel;
import org.keycloak.storage.ldap.idm.store.ldap.LDAPUtil;

public class UniventionAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(UniventionAuthenticator.class);
    private final UniventionDirectoryManagerClientFactory udmClientFactory;
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

        // ✅ Create IdentityMappingConfig instance using the factory
        UniventionAuthenticatorConfig config;
        try {
            config = UniventionAuthenticatorConfigFactory.createConfig(context);
        } catch (IllegalStateException | IllegalArgumentException e) {
            logger.error("Failed to load configuration", e);
            context.failure(AuthenticationFlowError.CREDENTIAL_SETUP_REQUIRED);
            userManager.removeUser(context.getRealm(), context.getUser());
            return;
        }

        UserModel user = context.getUser();
        logger.debugf("Keycloak user object: %s", user.getAttributes());

        // Retrieve user attributes
        String firstname = user.getFirstName();
        String lastname = user.getLastName();
        String username = user.getUsername();
        String email = user.getEmail();

        // Get identity provider attributes
        String sourceIdentityProviderID_KeycloakAndUDMKey = config.getSourceIdentityProviderID_KeycloakAndUDMKey();
        String sourceUserPrimaryID_UDMKey = config.getSourceUserPrimaryID_UDMKey();

        // TODO adapt to use built in string method of config
        logger.infof(
                "Federated user attempted to log in: username: %s, firstname: %s, lastname: %s, email: %s, remSourceIden_key: %s, remObjIden_key: %s, userAttributes: %s",
                username, firstname, lastname, email, sourceIdentityProviderID_KeycloakAndUDMKey, sourceUserPrimaryID_UDMKey, config.getUdmUserPrimaryGroupDn(), user.getAttributes()
        );

        String password = generateRandomPassword();

        // ✅ Validate objectGUID
        String SourceUserPrimaryID_Value = user.getFirstAttribute("objectGUID");
        if (SourceUserPrimaryID_Value == null || SourceUserPrimaryID_Value.trim().isEmpty()) {
            logger.error("ObjectGUID is null or empty, authentication failed");
            context.failure(AuthenticationFlowError.INVALID_USER);
            return;
        }

        // ✅ Validate Identity Provider Source ID
        String remSourceID_value = user.getFirstAttribute(sourceIdentityProviderID_KeycloakAndUDMKey);
        if (remSourceID_value == null || remSourceID_value.trim().isEmpty()) {
            logger.error("SourceIdentityProviderID_KeycloakAndUDMKey is null or empty, authentication failed");
            context.failure(AuthenticationFlowError.INVALID_USER);
            userManager.removeUser(context.getRealm(), context.getUser());
            return;
        }

        String univentionTargetFederationLink = user.getFirstAttribute("univentionTargetFederationLink");

        logger.infof("Additional user attributes for username: %s, sourceUserPrimaryID_UDMKey %s, SourceUserPrimaryID_Value %s, sourceIdentityProviderID_KeycloakAndUDMKey %s, remSourceID_value %s, univentionTargetFederationLink: %s",
                username, sourceUserPrimaryID_UDMKey, SourceUserPrimaryID_Value, sourceIdentityProviderID_KeycloakAndUDMKey, remSourceID_value, univentionTargetFederationLink
        );

        String decoded_remoteGUID;
        try {
            decoded_remoteGUID = LDAPUtil.decodeObjectGUID(Base64.getDecoder().decode(SourceUserPrimaryID_Value.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            logger.warnf("Failed to decode remote GUID of username: %s, guid: %s", username, SourceUserPrimaryID_Value);
            context.failure(AuthenticationFlowError.INTERNAL_ERROR);
            userManager.removeUser(context.getRealm(), context.getUser());
            return;
        }

        // ✅ Build UDM properties
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("firstname", firstname);
        properties.put("lastname", lastname);
        properties.put("password", password);
        properties.put("e-mail", new String[]{email});
        properties.put("description", "Shadow copy of user");
        properties.put(sourceUserPrimaryID_UDMKey, decoded_remoteGUID);
        properties.put(sourceIdentityProviderID_KeycloakAndUDMKey, remSourceID_value);

        if (config.getUdmUserPrimaryGroupDn() != null && !config.getUdmUserPrimaryGroupDn().isEmpty()) {
            properties.put("primaryGroup", config.getUdmUserPrimaryGroupDn());
        }

        User udmUser = new User();
        udmUser.setProperties(properties);

        // ✅ Initialize UDM Client
        UniventionDirectoryManagerClient udmClient = udmClientFactory.create(
                config.getUdmBaseUrl(), config.getUdmUsername(), config.getUdmPassword()
        );

        try {
            // ✅ Check if the user already exists
            if (existsRemoteUser(udmClient, username)) {
                logger.infof("User with username: %s already exists in UDM and does not need to be created", username);
                context.success();
                return;
            }

            // ✅ Create UDM user
            User createdUser = udmClient.createUser(udmUser);
            if (createdUser == null) {
                logger.errorf("Failed to create user with username: %s", username);
                context.failure(AuthenticationFlowError.INTERNAL_ERROR);
                userManager.removeUser(context.getRealm(), context.getUser());
                return;
            }

            // ✅ Update Keycloak user with UDM attributes
            user.setSingleAttribute("LDAP_ID", createdUser.getUuid().toString());
            user.setSingleAttribute("LDAP_ENTRY_DN", createdUser.getDn());
            user.setSingleAttribute("uid", username);
            user.setFederationLink(univentionTargetFederationLink);
            logger.infof("Federated user ad-hoc provisioning for user with username: %s was successful", username);
            context.success();
        } catch (IOException | InterruptedException e) {
            logger.errorf("Failed to create user with username: %s, error: %s", username, e);
            context.failure(AuthenticationFlowError.INTERNAL_ERROR);
            userManager.removeUser(context.getRealm(), context.getUser());
        }
    }

    @Override
    public void close() {}

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {}

    @Override
    public boolean requiresUser() {
        return true;
    }

    private boolean existsRemoteUser(UniventionDirectoryManagerClient client, String username) throws IOException, InterruptedException {
        UserSearchParams params = UserSearchParams.builder().query(Map.of("username", username)).scope("sub").build();
        UserSearchResult result = client.searchUsers(params);
        return !result.getUsers().isEmpty();
    }

    private String generateRandomPassword() {
        return Base64.getEncoder().encodeToString(new byte[256]);
    }
}
