package de.univention.keycloak;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.sessions.AuthenticationSessionModel;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginRejectionTest {

    private KeycloakSession session;
    private KeycloakContext context;
    private UniventionUserAccountControlStorageMapper mapper;

    @BeforeEach
    public void setUp() {
        session = mock(KeycloakSession.class);
        context = mock(KeycloakContext.class);
        when(session.getContext()).thenReturn(context);
        mapper = new UniventionUserAccountControlStorageMapper(mock(ComponentModel.class), mock(LDAPStorageProvider.class)) {
            @Override
            protected KeycloakSession getSession() {
                return session;
            }

            @Override
            protected String getRealmName() {
                return "testRealm";
            }
        };
    }

    @Test
    public void storesReason() {
        mapper.rejectLogin("testUser", "accountLocked");

        verify(session).setAttribute(UniventionUserAccountControlStorageMapper.ERROR_DETAIL, "accountLocked");
    }

    @Test
    public void storesReasonOncePerRequest() {
        when(session.getAttribute(UniventionUserAccountControlStorageMapper.ERROR_DETAIL)).thenReturn("accountLocked");

        mapper.rejectLogin("testUser", "accountLocked");

        verify(session, never()).setAttribute(UniventionUserAccountControlStorageMapper.ERROR_DETAIL, "accountLocked");
    }

    @Test
    public void loginRequestWithAuthenticationSession() {
        when(context.getAuthenticationSession()).thenReturn(mock(AuthenticationSessionModel.class));

        assertTrue(mapper.isLoginRequest(session));
    }

    @Test
    public void noLoginRequestWithoutAuthenticationSession() {
        assertFalse(mapper.isLoginRequest(session));
    }
}
