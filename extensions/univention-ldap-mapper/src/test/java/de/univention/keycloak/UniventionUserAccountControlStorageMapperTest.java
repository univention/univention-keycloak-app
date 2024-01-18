package de.univention.keycloak;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.idm.model.LDAPObject;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class UniventionUserAccountControlStorageMapperTest {

    private final String username = "testUser";
    private final String realmName = "testRealm";

    private final Map<String, Set<String>> attrs = new HashMap<>();

    @Mock
    ComponentModel mapperModel;

    @Mock
    LDAPStorageProvider ldapProvider;

    @Mock
    UserModel user;

    @Mock
    LDAPObject ldapUser;

    @Mock
    RealmModel realm;

    UniventionUserAccountControlStorageMapper mapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Anonymous class overriding processAuthErrorCode with public access, so that we can mock/spy without
        // introducing a new class just for testing.
        mapper = new UniventionUserAccountControlStorageMapper(mapperModel, ldapProvider) {
            @Override
            public boolean processAuthError(UserModel user, Map<String, Set<String>> attributes) {
                return super.processAuthError(user, attributes);
            }

            @Override
            public void addPasswordChangeAction(UserModel user) {
                super.addPasswordChangeAction(user);
            }

            @Override
            public String getRealmName() {
                return realmName;
            }
        };
    }


    @Test
    public void ldapNoErrorMatching() {
        final AuthenticationException exc = new AuthenticationException("Generic error");
        UniventionUserAccountControlStorageMapper spyMapper = Mockito.spy(mapper);

        final boolean res = spyMapper.onAuthenticationFailure(ldapUser, user, exc, realm);
        assertFalse(res);

        Mockito.verify(spyMapper, times(0)).processAuthError(user, null);
    }

    @Test
    public void ldapNotError49() {
        final AuthenticationException exc = new AuthenticationException("[LDAP: error code 80 - implementation specific]");
        UniventionUserAccountControlStorageMapper spyMapper = Mockito.spy(mapper);

        final boolean res = spyMapper.onAuthenticationFailure(ldapUser, user, exc, realm);
        assertFalse(res);

        Mockito.verify(spyMapper, times(0)).processAuthError(user, attrs);
    }

    @Test
    public void ldapError49InvalidCredentials() {
        final AuthenticationException exc = new AuthenticationException("[LDAP: error code 49 - Invalid Credentials]");
        UniventionUserAccountControlStorageMapper spyMapper = Mockito.spy(mapper);

        final boolean res = spyMapper.onAuthenticationFailure(ldapUser, user, exc, realm);
        assertFalse(res);

        Mockito.verify(spyMapper, times(0)).processAuthError(user, null);
    }

    @Test
    public void ldapError49PasswordExpired() {
        final AuthenticationException exc = new AuthenticationException("[LDAP: error code 49 - password expired]");
        UniventionUserAccountControlStorageMapper spyMapper = Mockito.spy(mapper);

        final boolean res = spyMapper.onAuthenticationFailure(ldapUser, user, exc, realm);
        assertFalse(res);

        Mockito.verify(spyMapper, times(1)).processAuthError(user, attrs);
    }

    @Test
    public void passwordChangeNeeded() {
        final AuthenticationException exc = new AuthenticationException("[LDAP: error code 49 - password expired]");
        UniventionUserAccountControlStorageMapper spyMapper = Mockito.spy(mapper);

        try (MockedConstruction<UniventionUserAccountControlStorageMapper.AccountAttributesHelper> mocked = Mockito.mockConstruction(UniventionUserAccountControlStorageMapper.AccountAttributesHelper.class,
            (mock, context) -> {
                when(mock.isPasswordChangeNeeded()).thenReturn(true);
                doNothing().when(spyMapper).addPasswordChangeAction(user);
            })) {

            final boolean res = spyMapper.onAuthenticationFailure(ldapUser, user, exc, realm);
            assertTrue(res);

            Mockito.verify(spyMapper, times(1)).processAuthError(user, attrs);
        }
    }
}
