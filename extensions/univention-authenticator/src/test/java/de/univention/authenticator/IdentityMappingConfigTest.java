package de.univention.authenticator;

import de.univention.authenticator.config.IdentityMappingConfig;
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
public class IdentityMappingConfigTest {

    @Mock
    private AuthenticationFlowContext mockContext;

    @Mock
    private AuthenticatorConfigModel mockConfigModel;

    private Map<String, String> configValues;

    // New constants for additional config properties
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

        // New config properties added
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
        // Create instance of IdentityMappingConfig
        IdentityMappingConfig identityMappingConfig = new IdentityMappingConfig(mockContext);

        // Verify correct values are retrieved
        assertEquals("idp-12345", identityMappingConfig.getSourceIdentityProviderID_KeycloakAndUDMKey());
        assertEquals("udm-67890", identityMappingConfig.getSourceUserPrimaryID_UDMKey());
        assertEquals("cn=users,dc=example,dc=com", identityMappingConfig.getUdmUserPrimaryGroupDn());
        assertEquals("https://udm.example.com", identityMappingConfig.getUdmBaseUrl());
        assertEquals("admin", identityMappingConfig.getUdmUsername());
        assertEquals("secret", identityMappingConfig.getUdmPassword());

        // Verify additional properties
        assertEquals("https://udm.example.com/api", configValues.get(UDM_ENDPOINT_CONFIG_PROPERTY_NAME));
        assertEquals("udm_admin", configValues.get(UDM_USER_CONFIG_PROPERTY_NAME));
        assertEquals("supersecret", configValues.get(UDM_PASSWORD_CONFIG_PROPERTY_NAME));
        assertEquals("keycloak-source-id", configValues.get(KEYCLOAK_FEDERATION_SOURCE_IDENTIFIER_NAME));
        assertEquals("keycloak-remote-id", configValues.get(KEYCLOAK_FEDERATION_REMOTE_IDENTIFIER_NAME));
        assertEquals("cn=default,dc=example,dc=com", configValues.get(DEFAULT_GROUP_DN_CONFIG_PROPERTY_NAME));
    }

    @Test
    void testConfigFailsWithMissingValues() {
        // Remove a required key to simulate misconfiguration
        configValues.remove("udm_password");

        // Since mockConfigModel.getConfig() returns the map reference, no need to re-mock
        when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);

        // Verify that an exception is thrown due to missing values
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new IdentityMappingConfig(mockContext));
        assertTrue(exception.getMessage().contains("Missing required configuration keys"));

        // Ensure context.failure() is triggered
        verify(mockContext).failure(AuthenticationFlowError.CREDENTIAL_SETUP_REQUIRED);
    }

    @Test
    void testConfigFailsWithNullConfigModel() {
        when(mockContext.getAuthenticatorConfig()).thenReturn(null);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new IdentityMappingConfig(mockContext));
        assertEquals("Configuration is missing, authentication cannot proceed", exception.getMessage());

        verify(mockContext).failure(AuthenticationFlowError.CREDENTIAL_SETUP_REQUIRED);
    }

    @Test
    void testConfigFailsWithNullConfigMap() {
        when(mockConfigModel.getConfig()).thenReturn(null);
        when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new IdentityMappingConfig(mockContext));
        assertEquals("Configuration is missing, authentication cannot proceed", exception.getMessage());

        verify(mockContext).failure(AuthenticationFlowError.CREDENTIAL_SETUP_REQUIRED);
    }
}
