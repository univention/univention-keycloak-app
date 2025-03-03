package de.univention.authenticator.config;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.models.AuthenticatorConfigModel;
import de.univention.authenticator.UniventionAuthenticatorFactory;

import java.util.*;

public class IdentityMappingConfig {
    private static final Logger logger = Logger.getLogger(IdentityMappingConfig.class);

    /**
     * List of expected config keys (should be defined somewhere in UniventionAuthenticatorFactory)
     */
    private static final List<String> REQUIRED_KEYS = List.of(UniventionAuthenticatorFactory.configPropertyNames);

    private final String sourceIdentityProviderID_KeycloakAndUDMKey;
    private static final String SOURCE_USER_PRIMARY_ID_KEYCLOAK_KEY = "objectGUID";
    private final String sourceUserPrimaryID_UDMKey;
    private final String udmUserPrimaryGroupDn;
    private final String udmBaseUrl;
    private final String udmUsername;
    private final String udmPassword;
    private final Map<String, String> fullConfig; // Store all values for future extensions

    /**
     * Constructor integrates the validation & config retrieval logic
     */
    public IdentityMappingConfig(AuthenticationFlowContext context) {
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

        // Initialize fields with safe retrieval
        this.sourceIdentityProviderID_KeycloakAndUDMKey = config.get("sourceIdentityProviderID_KeycloakAndUDMKey");
        this.sourceUserPrimaryID_UDMKey = config.get("sourceUserPrimaryID_UDMKey");
        this.udmUserPrimaryGroupDn = config.get("udmUserPrimaryGroupDn");
        this.udmBaseUrl = config.get("udmBaseUrl");
        this.udmUsername = config.get("udmUsername");
        this.udmPassword = config.get("udmPassword");

        // ✅ Fail authentication if SourceUserPrimaryID_UDMKey (remIdGUID_value) is null or empty
        if (isNullOrEmpty(this.sourceUserPrimaryID_UDMKey)) {
            logger.error("SourceUserPrimaryID_UDMKey (remIdGUID_value) is null or empty → Authentication failed");
            context.failure(AuthenticationFlowError.INVALID_USER);
            throw new IllegalStateException("Authentication failed due to missing SourceUserPrimaryID_UDMKey");
        }

        // ✅ Fail authentication if SourceIdentityProviderID_KeycloakAndUDMKey is null or empty
        if (isNullOrEmpty(this.sourceIdentityProviderID_KeycloakAndUDMKey)) {
            logger.error("SourceIdentityProviderID_KeycloakAndUDMKey is null or empty → Authentication failed");
            context.failure(AuthenticationFlowError.INVALID_USER);
            throw new IllegalStateException("Authentication failed due to missing SourceIdentityProviderID_KeycloakAndUDMKey");
        }
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

    /**
     * Retrieve the full config map (read-only)
     */
    public Map<String, String> getFullConfig() {
        return fullConfig;
    }

    /**
     * Helper method to check for null or empty values
     */
    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
