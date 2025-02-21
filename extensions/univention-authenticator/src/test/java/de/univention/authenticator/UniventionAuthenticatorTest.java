package de.univention.authenticator;

import de.univention.udm.UniventionDirectoryManagerClient;
import de.univention.udm.UniventionDirectoryManagerClientFactory;
import de.univention.udm.models.User;
import de.univention.udm.models.UserSearchResult;
import org.junit.jupiter.api.*;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.models.UserManager;
import org.keycloak.models.UserModel;

import java.io.IOException;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UniventionAuthenticatorTest {

    private UniventionAuthenticator authenticator;
    private AuthenticationFlowContext context;
    private UserModel user;
    private UniventionDirectoryManagerClient mockUDMClient;
    private UserManager mockUserManager;

    @BeforeEach
    void setUp() {
        context = mock(AuthenticationFlowContext.class);
        user = mock(UserModel.class);
        when(context.getUser()).thenReturn(user);
        when(user.getFirstName()).thenReturn("John");
        when(user.getLastName()).thenReturn("Doe");
        when(user.getUsername()).thenReturn("jdoe");
        // Mock valid objectGUID from the keycloak user object
        when(user.getFirstAttribute("objectGUID")).thenReturn(base64Encode("878ce8b7-2713-41a9-a765-5e3905ab5ef2"));

        mockUDMClient = mock(UniventionDirectoryManagerClient.class);
        UniventionDirectoryManagerClientFactory mockUdmClientFactory = mock(UniventionDirectoryManagerClientFactory.class);
        when(mockUdmClientFactory.create(any(), any(), any())).thenReturn(mockUDMClient);
        mockUserManager = mock(UserManager.class);
        authenticator = spy(new UniventionAuthenticator(mockUserManager, mockUdmClientFactory));

        List<String> mockConfig = Arrays.asList("http://udm.url", "dummy-username", "dummy-password", "sourceKey", "guidKey", "primaryGroupKey");
        doReturn(mockConfig).when(authenticator).getValidConfig(any(AuthenticationFlowContext.class));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Disabled("expected failure: a null objectGUID needs to be handled properly in the future")
    void testObjectGUIDIsNull() {
        when(user.getFirstAttribute("objectGUID")).thenReturn(null);
        authenticator.authenticate(context);

        // TODO: Decide if there's a better alternative
        verify(context, times(1)).failure(AuthenticationFlowError.INVALID_CLIENT_SESSION);
        verify(context, times(0)).success();
        verify(mockUserManager, times(1)).removeUser(any(), any());
    }

    @Test
    void testInvalidObjectGUID() {
        when(user.getFirstAttribute("objectGUID")).thenReturn(base64Encode("invalid"));

        authenticator.authenticate(context);
        verify(context, times(1)).failure(AuthenticationFlowError.INTERNAL_ERROR);
        verify(context, times(0)).success();
        // TODO: Implement keycloak user deletion in this failure case.
        // verify(mockUserManager, times(1)).removeUser(any(), any());
    }

    @Test
    void testSuccess() throws IOException, InterruptedException {
        // Mock user search returns empty list -> Mock no existing UDM user
        UserSearchResult mockResult = mock(UserSearchResult.class);
        when(mockResult.getUsers()).thenReturn(Collections.emptyList());
        when(mockUDMClient.searchUsers(any())).thenReturn(mockResult);

        // Mock successful user creation
        User user = new User();
        user.setUuid(UUID.randomUUID());
        when(mockUDMClient.createUser(any())).thenReturn(user);

        authenticator.authenticate(context);
        verify(context, times(0)).failure(AuthenticationFlowError.INTERNAL_ERROR);
        verify(context, times(1)).success();
    }

    @Test
    void testUdmThrowsIOExceptionAbortsTheFlow() throws IOException, InterruptedException {
        // Mock user search returns empty list -> Mock no existing UDM user
        when(mockUDMClient.searchUsers(any())).thenThrow(new IOException());

        authenticator.authenticate(context);
        verify(context, times(1)).failure(AuthenticationFlowError.INTERNAL_ERROR);
        verify(context, times(0)).success();
        verify(mockUserManager, times(1)).removeUser(any(), any());
    }

    String base64Encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }
}
