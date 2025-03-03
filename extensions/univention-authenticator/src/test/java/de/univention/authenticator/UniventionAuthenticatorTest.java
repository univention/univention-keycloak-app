package de.univention.authenticator;

import de.univention.authenticator.config.UniventionAuthenticatorConfig;
import de.univention.authenticator.config.UniventionAuthenticatorConfigFactory;
import de.univention.udm.UniventionDirectoryManagerClient;
import de.univention.udm.UniventionDirectoryManagerClientFactory;
import de.univention.udm.models.User;
import de.univention.udm.models.UserSearchResult;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.models.UserManager;
import org.keycloak.models.UserModel;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UniventionAuthenticatorTest {

    private UniventionAuthenticator authenticator;

    @Mock
    private AuthenticationFlowContext mockContext;
    @Mock
    private UserModel mockUser;
    @Mock
    private UniventionDirectoryManagerClient mockUDMClient;
    @Mock
    private UniventionDirectoryManagerClientFactory mockUdmClientFactory;
    @Mock
    private UserManager mockUserManager;
    @Mock
    private UniventionAuthenticatorConfig mockConfig;

    private MockedStatic<UniventionAuthenticatorConfigFactory> mockedFactory;

    @BeforeEach
    void setUp() {
        lenient().when(mockContext.getUser()).thenReturn(mockUser);
        lenient().when(mockUser.getFirstName()).thenReturn("John");
        lenient().when(mockUser.getLastName()).thenReturn("Doe");
        lenient().when(mockUser.getUsername()).thenReturn("jdoe");
        lenient().when(mockUser.getEmail()).thenReturn("john.doe@example.com");
        lenient().when(mockUser.getFirstAttribute("objectGUID")).thenReturn(base64Encode("878ce8b7-2713-41a9-a765-5e3905ab5ef2"));
        lenient().when(mockUser.getFirstAttribute("idp-12345")).thenReturn(base64Encode("878ce8b7-2713-41a9-a765-5e390d219313925ab5ef2"));
        lenient().when(mockUdmClientFactory.create(any(), any(), any())).thenReturn(mockUDMClient);
        // Mock IdentityMappingConfig values
        lenient().when(mockConfig.getSourceIdentityProviderID_KeycloakAndUDMKey()).thenReturn("idp-12345");
        lenient().when(mockConfig.getSourceUserPrimaryID_UDMKey()).thenReturn("udm-67890");
        lenient().when(mockConfig.getUdmUserPrimaryGroupDn()).thenReturn("cn=users,dc=example,dc=com");
        lenient().when(mockConfig.getUdmBaseUrl()).thenReturn("https://udm.example.com");
        lenient().when(mockConfig.getUdmUsername()).thenReturn("admin");
        lenient().when(mockConfig.getUdmPassword()).thenReturn("secret");

        // ✅ Ensure factory method is mocked **persistently** for all tests
        mockedFactory = mockStatic(UniventionAuthenticatorConfigFactory.class);
        mockedFactory.when(() -> UniventionAuthenticatorConfigFactory.createConfig(any(AuthenticationFlowContext.class)))
                .thenReturn(mockConfig);

        authenticator = new UniventionAuthenticator(mockUserManager, mockUdmClientFactory);
    }

    @AfterEach
    void tearDown() {
        if (mockedFactory != null) {
            mockedFactory.close();
        }
    }

    @Test
    void testAuthenticationFailsWhenObjectGUIDIsNull() {
        when(mockUser.getFirstAttribute("objectGUID")).thenReturn(null);

        authenticator.authenticate(mockContext);

        verify(mockContext, times(1)).failure(AuthenticationFlowError.INVALID_USER);
        verify(mockContext, times(0)).success();
        verify(mockUserManager, times(0)).removeUser(any(), any());
    }

    @Test
    void testAuthenticationFailsWhenGUIDIsNotBase64Encoded() {
        when(mockUser.getFirstAttribute("objectGUID")).thenReturn("dummy");

        authenticator.authenticate(mockContext);

        verify(mockContext, times(1)).failure(AuthenticationFlowError.INTERNAL_ERROR);
        verify(mockContext, times(0)).success();
    }

    @Test
    void testAuthenticationFailsWhenSourceIDIsNull() {
        when(mockUser.getFirstAttribute("objectGUID")).thenReturn(base64Encode("valid-guid"));
        when(mockUser.getFirstAttribute(mockConfig.getSourceIdentityProviderID_KeycloakAndUDMKey())).thenReturn(null);

        authenticator.authenticate(mockContext);

        verify(mockContext, times(1)).failure(AuthenticationFlowError.INVALID_USER);
        verify(mockContext, times(0)).success();
    }

    @Test
    void testSuccessWhenUserDoesNotExistInUDM() throws IOException, InterruptedException {
        // Mock user search returning empty list (user doesn't exist)
        UserSearchResult mockResult = mock(UserSearchResult.class);
        when(mockResult.getUsers()).thenReturn(Collections.emptyList());
        when(mockUDMClient.searchUsers(any())).thenReturn(mockResult);

        // Mock successful user creation
        User createdUser = new User();
        createdUser.setUuid(UUID.randomUUID());
        when(mockUDMClient.createUser(any())).thenReturn(createdUser);

        authenticator.authenticate(mockContext);

        verify(mockContext, times(0)).failure(AuthenticationFlowError.INTERNAL_ERROR);
        verify(mockContext, times(1)).success();
    }

    @Test
    void testSuccessWhenUserAlreadyExistsInUDM() throws IOException, InterruptedException {
        // Mock user search returning an existing user
        UserSearchResult mockResult = mock(UserSearchResult.class);
        User existingUser = new User();
        List<User> t = new ArrayList<>();
        t.add(existingUser);
        when(mockResult.getUsers()).thenReturn(t);
        when(mockUDMClient.searchUsers(any())).thenReturn(mockResult);

        authenticator.authenticate(mockContext);

        verify(mockContext, times(0)).failure(any());
        verify(mockContext, times(1)).success();
    }

    @Test
    void testAuthenticationFailsWhenUDMThrowsIOException() throws IOException, InterruptedException {
        when(mockUDMClient.searchUsers(any())).thenThrow(new IOException());

        authenticator.authenticate(mockContext);

        verify(mockContext, times(1)).failure(AuthenticationFlowError.INTERNAL_ERROR);
        verify(mockContext, times(0)).success();
        verify(mockUserManager, times(1)).removeUser(any(), any());
    }

    @Test
    void testAuthenticationFailsWhenUserCreationFails() throws IOException, InterruptedException {
        // Mock user search returning empty list (user doesn't exist)
        UserSearchResult mockResult = mock(UserSearchResult.class);
        when(mockResult.getUsers()).thenReturn(Collections.emptyList());
        when(mockUDMClient.searchUsers(any())).thenReturn(mockResult);

        // Mock user creation failure (null return)
        when(mockUDMClient.createUser(any())).thenReturn(null);

        authenticator.authenticate(mockContext);

        verify(mockContext, times(1)).failure(AuthenticationFlowError.INTERNAL_ERROR);
        verify(mockContext, times(0)).success();
        verify(mockUserManager, times(1)).removeUser(any(), any());
    }

    private String base64Encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }
}
