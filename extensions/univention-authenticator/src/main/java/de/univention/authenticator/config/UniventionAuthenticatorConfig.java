
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

package de.univention.authenticator.config;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.models.AuthenticatorConfigModel;
import de.univention.authenticator.UniventionAuthenticatorFactory;

import java.util.*;

public class UniventionAuthenticatorConfig {
    private static final Logger logger = Logger.getLogger(UniventionAuthenticatorConfig.class);

    /**
     * List of expected config keys (should be defined somewhere in UniventionAuthenticatorFactory)
     */
    private static final List<String> REQUIRED_KEYS = List.of(UniventionAuthenticatorFactory.requiredConfigPropertyNames);

    private final String sourceIdentityProviderID_KeycloakAndUDMKey;
    private static final String SOURCE_USER_PRIMARY_ID_KEYCLOAK_KEY = "objectGUID";
    private final String sourceUserPrimaryID_UDMKey;
    private final String udmUserPrimaryGroupDn;
    private final String udmBaseUrl;
    private final String udmEndpoint;
    private final String udmUsername;
    private final String udmPassword;
    private final Map<String, String> fullConfig; // Store all values for future extensions

    /**
     * Constructor integrates the validation & config retrieval logic
     */
    public UniventionAuthenticatorConfig(AuthenticationFlowContext context) {
        if (context == null) {
            throw new IllegalArgumentException("AuthenticationFlowContext cannot be null");
        }

        AuthenticatorConfigModel configModel = context.getAuthenticatorConfig();

        // Validate config model
        if (configModel == null || configModel.getConfig() == null) {
            logger.error("Univention Authenticator ConfigModel is null or empty");
            context.failure(AuthenticationFlowError.CREDENTIAL_SETUP_REQUIRED);
            throw new IllegalStateException("Configuration is missing, authentication cannot proceed");
        }

        Map<String, String> config = configModel.getConfig();
        this.fullConfig = Collections.unmodifiableMap(new HashMap<>(config));

        // Ensure all required properties exist
        List<String> missingKeys = new ArrayList<>();
        logger.errorf("Config Model: %s", config.toString());
        // Initialize fields with safe retrieval
        this.sourceIdentityProviderID_KeycloakAndUDMKey = config.get(UniventionAuthenticatorFactory.configPropertyNames[3]);
        this.sourceUserPrimaryID_UDMKey = config.get(UniventionAuthenticatorFactory.configPropertyNames[4]);
        this.udmUserPrimaryGroupDn = config.get(UniventionAuthenticatorFactory.configPropertyNames[5]);
        this.udmBaseUrl = config.get(UniventionAuthenticatorFactory.configPropertyNames[0]);
        this.udmEndpoint = config.get(UniventionAuthenticatorFactory.configPropertyNames[0]);
        this.udmUsername = config.get(UniventionAuthenticatorFactory.configPropertyNames[1]);
        this.udmPassword = config.get(UniventionAuthenticatorFactory.configPropertyNames[2]);

        // ✅ Fail authentication if SourceUserPrimaryID_UDMKey (remIdGUID_value) is null or empty
        if (isNullOrEmpty(this.sourceUserPrimaryID_UDMKey)) {
            logger.error("SourceUserPrimaryID_UDMKey (remIdGUID_value) is null or empty → Authentication failed");
            throw new IllegalStateException("Authentication failed due to missing SourceUserPrimaryID_UDMKey");
        }

        // ✅ Fail authentication if SourceIdentityProviderID_KeycloakAndUDMKey is null or empty
        if (isNullOrEmpty(this.sourceIdentityProviderID_KeycloakAndUDMKey)) {
            logger.error("SourceIdentityProviderID_KeycloakAndUDMKey is null or empty → Authentication failed");
            throw new IllegalStateException("Authentication failed due to missing SourceIdentityProviderID_KeycloakAndUDMKey");
        }

        for (String key : REQUIRED_KEYS) {
            if (!config.containsKey(key) || config.get(key) == null || config.get(key).isEmpty()) {
                logger.errorf("Missing required config value: %s", key);
                missingKeys.add(key);
            }
        }

        if (!missingKeys.isEmpty()) {
            context.failure(AuthenticationFlowError.CREDENTIAL_SETUP_REQUIRED);
            throw new IllegalStateException("Missing required configuration keys: " + String.join(", ", missingKeys));
        }
        logger.errorf("Config Value: %s", config.toString());
    }

    /**
     * Getters to access config values
     */
    public String getSourceIdentityProviderID_KeycloakAndUDMKey() {
        return sourceIdentityProviderID_KeycloakAndUDMKey;
    }

    public static String getSourceUserPrimaryID_KeycloakKey() {
        return SOURCE_USER_PRIMARY_ID_KEYCLOAK_KEY;
    }

    public String getSourceUserPrimaryID_UDMKey() {
        return sourceUserPrimaryID_UDMKey;
    }

    public String getUdmUserPrimaryGroupDn() {
        return udmUserPrimaryGroupDn;
    }

    public String getUdmBaseUrl() {
        return udmBaseUrl;
    }

    public String getUdmUsername() {
        return udmUsername;
    }

    public String getUdmPassword() {
        return udmPassword;
    }

    public String getUdmEndpoint() {
        return udmEndpoint;
    }

    /**
     * Retrieve the full config map (read-only)
     */
    public Map<String, String> getFullConfig() {
        return fullConfig;
        //TODO adapt to not log pw
    }

    /**
     * Helper method to check for null or empty values
     */
    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
