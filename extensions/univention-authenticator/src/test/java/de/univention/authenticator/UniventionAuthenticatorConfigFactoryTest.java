package de.univention.authenticator;

import de.univention.authenticator.config.UniventionAuthenticatorConfig;
import de.univention.authenticator.config.UniventionAuthenticatorConfigFactory;
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
public class UniventionAuthenticatorConfigFactoryTest {

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

        // Mock behavior for context
        lenient().when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);
    }

    @Test
    void testCreateConfigReturnsValidInstance() {
        UniventionAuthenticatorConfig config = UniventionAuthenticatorConfigFactory.createConfig(mockContext);
        assertNotNull(config, "Expected IdentityMappingConfig instance but got null");

        // Verify that the returned config contains expected values
        assertEquals("idp-12345", config.getSourceIdentityProviderID_KeycloakAndUDMKey());
        assertEquals("udm-67890", config.getSourceUserPrimaryID_UDMKey());
    }

    @Test
    void testCreateConfigFailsWithNullContext() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            UniventionAuthenticatorConfigFactory.createConfig(null);
        });

        assertEquals("AuthenticationFlowContext cannot be null", exception.getMessage());
    }

    @Test
    void testCreateConfigFailsWithNullConfigModel() {
        when(mockContext.getAuthenticatorConfig()).thenReturn(null);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            UniventionAuthenticatorConfigFactory.createConfig(mockContext);
        });

        assertEquals("Configuration is missing, authentication cannot proceed", exception.getMessage());

        verify(mockContext).failure(AuthenticationFlowError.CREDENTIAL_SETUP_REQUIRED);
    }

    @Test
    void testCreateConfigFailsWithNullConfigMap() {
        when(mockConfigModel.getConfig()).thenReturn(null);
        when(mockContext.getAuthenticatorConfig()).thenReturn(mockConfigModel);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            UniventionAuthenticatorConfigFactory.createConfig(mockContext);
        });

        assertEquals("Configuration is missing, authentication cannot proceed", exception.getMessage());

        verify(mockContext).failure(AuthenticationFlowError.CREDENTIAL_SETUP_REQUIRED);
    }
}
