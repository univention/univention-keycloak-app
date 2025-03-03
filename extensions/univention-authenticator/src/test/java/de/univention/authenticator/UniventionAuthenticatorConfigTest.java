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
        configValues.put("sourceIdentityProviderID_KeycloakAndUDMKey", "idp-12345");
        configValues.put("sourceUserPrimaryID_UDMKey", "udm-67890");
        configValues.put("udmUserPrimaryGroupDn", "cn=users,dc=example,dc=com");
        configValues.put("udmBaseUrl", "https://udm.example.com");
        configValues.put("udmUsername", "admin");
        configValues.put("udmPassword", "secret");

        // Additional properties
        configValues.put(UDM_ENDPOINT_CONFIG_PROPERTY_NAME, "https://udm.example.com/api");
        configValues.put(UDM_USER_CONFIG_PROPERTY_NAME, "udm_admin");
        configValues.put(UDM_PASSWORD_CONFIG_PROPERTY_NAME, "supersecret");
        configValues.put(KEYCLOAK_FEDERATION_SOURCE_IDENTIFIER_NAME, "keycloak-source-id");
        configValues.put(KEYCLOAK_FEDERATION_REMOTE_IDENTIFIER_NAME, "keycloak-remote-id");
        configValues.put(DEFAULT_GROUP_DN_CONFIG_PROPERTY_NAME, "cn=default,dc=example,dc=com");

        // Mock behavior for config model
        lenient().when(mockConfigModel.getConfig()).thenReturn(configValues);

        // Mock behavior for context to return the config model
        when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);
    }

    @Test
    void testValidConfigCreation() {
        UniventionAuthenticatorConfig univentionAuthenticatorConfig = new UniventionAuthenticatorConfig(mockContext);

        assertEquals("idp-12345", univentionAuthenticatorConfig.getSourceIdentityProviderID_KeycloakAndUDMKey());
        assertEquals("udm-67890", univentionAuthenticatorConfig.getSourceUserPrimaryID_UDMKey());
        assertEquals("cn=users,dc=example,dc=com", univentionAuthenticatorConfig.getUdmUserPrimaryGroupDn());
        assertEquals("https://udm.example.com", univentionAuthenticatorConfig.getUdmBaseUrl());
        assertEquals("admin", univentionAuthenticatorConfig.getUdmUsername());
        assertEquals("secret", univentionAuthenticatorConfig.getUdmPassword());
    }

    @Test
    void testFailAuthenticationIfSourceUserPrimaryID_UDMKeyIsNull() {
        configValues.put("sourceUserPrimaryID_UDMKey", null);
        when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new UniventionAuthenticatorConfig(mockContext));
        assertEquals("Authentication failed due to missing SourceUserPrimaryID_UDMKey", exception.getMessage());

    }

    @Test
    void testFailAuthenticationIfSourceUserPrimaryID_UDMKeyIsEmpty() {
        configValues.put("sourceUserPrimaryID_UDMKey", "");
        when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new UniventionAuthenticatorConfig(mockContext));
        assertEquals("Authentication failed due to missing SourceUserPrimaryID_UDMKey", exception.getMessage());

    }

    @Test
    void testFailAuthenticationIfSourceIdentityProviderID_KeycloakAndUDMKeyIsNull() {
        configValues.put("sourceIdentityProviderID_KeycloakAndUDMKey", null);
        when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new UniventionAuthenticatorConfig(mockContext));
        assertEquals("Authentication failed due to missing SourceIdentityProviderID_KeycloakAndUDMKey", exception.getMessage());

    }

    @Test
    void testFailAuthenticationIfSourceIdentityProviderID_KeycloakAndUDMKeyIsEmpty() {
        configValues.put("sourceIdentityProviderID_KeycloakAndUDMKey", "");
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
