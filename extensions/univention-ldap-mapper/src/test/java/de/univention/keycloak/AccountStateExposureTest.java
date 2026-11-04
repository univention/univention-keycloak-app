package de.univention.keycloak;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.idm.model.LDAPObject;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static de.univention.keycloak.UniventionUserAccountControlStorageMapper.ACCOUNT_STATE;
import static de.univention.keycloak.UniventionUserAccountControlStorageMapper.ERROR_DETAIL;
import static de.univention.keycloak.UniventionUserAccountControlStorageMapper.SHADOW_EXPIRE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Checks that the account state is not exposed to the login form before the password is verified.
 */
public class AccountStateExposureTest {

    private static final Map<String, Set<String>> DISABLED_ATTRIBUTES = Map.of(SHADOW_EXPIRE, Collections.singleton("1"));

    private KeycloakSession session;
    private UniventionUserAccountControlStorageMapper mapper;

    /**
     * Creates a mapper whose session can be inspected.
     */
    @BeforeEach
    public void setUp() {
        session = mock(KeycloakSession.class);
        when(session.getContext()).thenReturn(mock(KeycloakContext.class));
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

    /**
     * Creates a Keycloak user model with a username.
     *
     * @return the mocked user model
     */
    private static UserModel user() {
        final UserModel user = mock(UserModel.class);
        when(user.getUsername()).thenReturn("testUser");
        return user;
    }

    /**
     * Creates the user model of a disabled LDAP user.
     *
     * @return the user model wrapped by the mapper
     */
    private UserModel disabledUser() {
        final LDAPObject ldapUser = new LDAPObject();
        DISABLED_ATTRIBUTES.forEach(ldapUser::setAttribute);
        return mapper.new UniventionUserModelDelegate(user(), ldapUser);
    }

    /**
     * Keycloak calls isEnabled() before the password check, so the reason must not reach the login form.
     */
    @Test
    public void isEnabledDoesNotExposeReasonToLoginForm() {
        assertFalse(disabledUser().isEnabled());

        verify(session).setAttribute(ACCOUNT_STATE, "accountDisabled");
        verify(session, never()).setAttribute(eq(ERROR_DETAIL), any());
    }

    /**
     * A failed LDAP bind with an account state error confirms the password, so the reason is shown.
     */
    @Test
    public void verifiedPasswordExposesReasonToLoginForm() {
        mapper.processAuthError(user(), DISABLED_ATTRIBUTES);

        verify(session).setAttribute(ERROR_DETAIL, "accountDisabled");
    }

    /**
     * The reason found by isEnabled() must not stop the verified reason from being shown later in the same request.
     */
    @Test
    public void verifiedPasswordExposesReasonAfterIsEnabled() {
        when(session.getAttribute(ACCOUNT_STATE)).thenReturn("accountDisabled");

        mapper.processAuthError(user(), DISABLED_ATTRIBUTES);

        verify(session).setAttribute(ERROR_DETAIL, "accountDisabled");
    }
}
