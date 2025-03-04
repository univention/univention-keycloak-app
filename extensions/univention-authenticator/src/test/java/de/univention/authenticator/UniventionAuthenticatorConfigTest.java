package de.univention.authenticator;

import de.univention.authenticator.config.UniventionAuthenticatorConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.models.AuthenticatorConfigModel;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniventionAuthenticatorConfigTest {

    @Mock
    private AuthenticationFlowContext mockContext;

    @Mock
    private AuthenticatorConfigModel mockConfigModel;

    private Map<String, String> configValues;

    // Additional config property names
    private static final String UDM_ENDPOINT_CONFIG_PROPERTY_NAME = "udm_endpoint";
    private static final String UDM_USER_CONFIG_PROPERTY_NAME = "udm_user";
    private static final String UDM_PASSWORD_CONFIG_PROPERTY_NAME = "udm_password";
    private static final String KEYCLOAK_FEDERATION_SOURCE_IDENTIFIER_NAME = "keycloak_federation_source_identifier";
    private static final String KEYCLOAK_FEDERATION_REMOTE_IDENTIFIER_NAME = "keycloak_federation_remote_identifier";
    private static final String DEFAULT_GROUP_DN_CONFIG_PROPERTY_NAME = "default_group_dn";

    @BeforeEach
    void setUp() {
        // Mock configuration values
        configValues = new HashMap<>();
        configValues.put(UDM_ENDPOINT_CONFIG_PROPERTY_NAME, "https://udm.example.com");
        configValues.put(UDM_USER_CONFIG_PROPERTY_NAME, "udm_admin");
        configValues.put(UDM_PASSWORD_CONFIG_PROPERTY_NAME, "supersecret");
        configValues.put(KEYCLOAK_FEDERATION_SOURCE_IDENTIFIER_NAME, "13337");
        configValues.put(KEYCLOAK_FEDERATION_REMOTE_IDENTIFIER_NAME, "13338");
        configValues.put(DEFAULT_GROUP_DN_CONFIG_PROPERTY_NAME, "cn=default,dc=example,dc=com");

        // Mock behavior for config model
        lenient().when(mockConfigModel.getConfig()).thenReturn(configValues);

        // Mock behavior for context to return the config model
        when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);
    }

    @Test
    void testValidConfigCreation() {
        UniventionAuthenticatorConfig univentionAuthenticatorConfig = new UniventionAuthenticatorConfig(mockContext);

        assertEquals("13337", univentionAuthenticatorConfig.getSourceIdentityProviderID_KeycloakAndUDMKey());
        assertEquals("13338", univentionAuthenticatorConfig.getSourceUserPrimaryID_UDMKey());
        assertEquals("cn=default,dc=example,dc=com", univentionAuthenticatorConfig.getUdmUserPrimaryGroupDn());
        assertEquals("https://udm.example.com", univentionAuthenticatorConfig.getUdmEndpoint());
        assertEquals("udm_admin", univentionAuthenticatorConfig.getUdmUsername());
        assertEquals("supersecret", univentionAuthenticatorConfig.getUdmPassword());
    }

    @Test
    void testFailAuthenticationIfSourceUserPrimaryID_UDMKeyIsNull() {
        configValues.put(UniventionAuthenticatorFactory.configPropertyNames[4], null);
        when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new UniventionAuthenticatorConfig(mockContext));
        assertEquals("Authentication failed due to missing SourceUserPrimaryID_UDMKey", exception.getMessage());

    }

    @Test
    void testFailAuthenticationIfSourceUserPrimaryID_UDMKeyIsEmpty() {
        configValues.put(UniventionAuthenticatorFactory.configPropertyNames[4], "");
        when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new UniventionAuthenticatorConfig(mockContext));
        assertEquals("Authentication failed due to missing SourceUserPrimaryID_UDMKey", exception.getMessage());

    }

    @Test
    void testFailAuthenticationIfSourceIdentityProviderID_KeycloakAndUDMKeyIsNull() {
        configValues.put(KEYCLOAK_FEDERATION_SOURCE_IDENTIFIER_NAME, null);
        when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new UniventionAuthenticatorConfig(mockContext));
        assertEquals("Authentication failed due to missing SourceIdentityProviderID_KeycloakAndUDMKey", exception.getMessage());

    }

    @Test
    void testFailAuthenticationIfSourceIdentityProviderID_KeycloakAndUDMKeyIsEmpty() {
        configValues.put(UniventionAuthenticatorFactory.configPropertyNames[3], "");
        when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new UniventionAuthenticatorConfig(mockContext));
        assertEquals("Authentication failed due to missing SourceIdentityProviderID_KeycloakAndUDMKey", exception.getMessage());

    }

    @Test
    void testFailAuthenticationIfRequiredConfigValuesAreMissing() {
        configValues.remove("udm_password");

        when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new UniventionAuthenticatorConfig(mockContext));
        assertTrue(exception.getMessage().contains("Missing required configuration keys"));

        verify(mockContext).failure(AuthenticationFlowError.CREDENTIAL_SETUP_REQUIRED);
    }

    @Test
    void testFailAuthenticationIfConfigModelIsNull() {
        when(mockContext.getAuthenticatorConfig()).thenReturn(null);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new UniventionAuthenticatorConfig(mockContext));
        assertEquals("Configuration is missing, authentication cannot proceed", exception.getMessage());

        verify(mockContext).failure(AuthenticationFlowError.CREDENTIAL_SETUP_REQUIRED);
    }

    @Test
    void testFailAuthenticationIfConfigMapIsNull() {
        when(mockConfigModel.getConfig()).thenReturn(null);
        when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new UniventionAuthenticatorConfig(mockContext));
        assertEquals("Configuration is missing, authentication cannot proceed", exception.getMessage());

        verify(mockContext).failure(AuthenticationFlowError.CREDENTIAL_SETUP_REQUIRED);
    }
}
