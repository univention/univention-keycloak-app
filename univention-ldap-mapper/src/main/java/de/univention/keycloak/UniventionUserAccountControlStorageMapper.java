package de.univention.keycloak;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.*;
import org.keycloak.sessions.AuthenticationSessionModel;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.idm.model.LDAPObject;
import org.keycloak.storage.ldap.idm.query.internal.LDAPQuery;
import org.keycloak.storage.ldap.mappers.AbstractLDAPStorageMapper;
import org.keycloak.storage.ldap.mappers.LDAPOperationDecorator;
import org.keycloak.storage.ldap.mappers.PasswordUpdateCallback;
import org.keycloak.storage.ldap.mappers.TxAwareLDAPUserModelDelegate;
import org.keycloak.storage.ldap.mappers.msad.LDAPServerPolicyHintsDecorator;
import org.keycloak.storage.ldap.mappers.msad.UserAccountControl;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.text.SimpleDateFormat;

import static java.lang.Math.floor;
public class UniventionUserAccountControlStorageMapper extends AbstractLDAPStorageMapper implements PasswordUpdateCallback {

    public static final String SHADOW_MAX = "shadowMax";
    public static final String SHADOW_EXPIRE = "shadowExpire";
    public static final String SHADOW_LAST_CHANGE = "shadowLastChange";
    public static final String KRB5_VALID_END = "krb5ValidEnd";
    public static final String KRB5_PASSWORD_END = "krb5PasswordEnd";
    public static final String KRB5_KDC_FLAGS = "krb5KDCFlags";
    public static final String SAMBA_KICKOFF_TIME = "sambaKickoffTime";
    public static final String SAMBA_ACCT_FLAGS = "sambaAcctFlags";
    public static final String SAMBA_PWD_MUST_CHANGE = "sambaPwdMustChange";
    public static final String LDAP_PASSWORD_POLICY_HINTS_ENABLED = "ldap.password.policy.hints.enabled";
    private static final Pattern AUTH_EXCEPTION_REGEX = Pattern.compile(".*LDAP: error code ([0-9]+) .*");
    private static final Logger logger = Logger.getLogger(UniventionUserAccountControlStorageMapper.class);
    private static final SimpleDateFormat krb5Format = new SimpleDateFormat("yyyyMMddHHmmss'Z'");

    public UniventionUserAccountControlStorageMapper(ComponentModel mapperModel, LDAPStorageProvider ldapProvider) {
        super(mapperModel, ldapProvider);
        ldapProvider.setUpdater(this);
    }

    @Override
    public void onImportUserFromLDAP(LDAPObject ldapUser, UserModel user, RealmModel realm, boolean isCreate) {

    }

    @Override
    public void onRegisterUserToLDAP(LDAPObject ldapUser, UserModel localUser, RealmModel realm) {

    }

    @Override
    public UserModel proxy(LDAPObject ldapUser, UserModel delegate, RealmModel realm) {
        return new UniventionUserModelDelegate(delegate, ldapUser);
    }

    @Override
    public void beforeLDAPQuery(LDAPQuery query) {
        query.addReturningLdapAttribute(SHADOW_MAX);
        query.addReturningLdapAttribute(SHADOW_EXPIRE);
        query.addReturningLdapAttribute(SHADOW_LAST_CHANGE);
        query.addReturningLdapAttribute(KRB5_VALID_END);
        query.addReturningLdapAttribute(KRB5_PASSWORD_END);
        query.addReturningLdapAttribute(KRB5_KDC_FLAGS);
        query.addReturningLdapAttribute(SAMBA_KICKOFF_TIME);
        query.addReturningLdapAttribute(SAMBA_ACCT_FLAGS);
        query.addReturningLdapAttribute(SAMBA_PWD_MUST_CHANGE);
    }

    public boolean onAuthenticationFailure(LDAPObject ldapUser, UserModel user, AuthenticationException ldapException, RealmModel realm) {
        String exceptionMessage = ldapException.getMessage();
        Matcher m = AUTH_EXCEPTION_REGEX.matcher(exceptionMessage);
        if (m.matches()) {
            String errorCode = m.group(1);
            return processAuthErrorCode(errorCode, user, ldapUser.getAttributes());
        } else {
            return false;
        }
    }

    protected boolean processAuthErrorCode(String errorCode, UserModel user, Map<String, Set<String>> attributes) {
        logger.debugf("Univention Error code is '%s' after failed LDAP login of user '%s'. Realm is '%s'", errorCode, user.getUsername(), getRealmName());

        if (errorCode.equals("49")) {
            final Instant now = Instant.now();

            if (isAccountDisabled(attributes)) {
                // User is disabled in openLDAP. Don't allow them to log in
                logger.debugf("Disabled user '%s' attempt to login. Realm is '%s'", user.getUsername(), getRealmName());
                return false;
            } else if (isAccountExpired(now, attributes)) {
                logger.debugf("Expired user '%s' attempt to login. Realm is '%s'", user.getUsername(), getRealmName());
            } else if (isAccountLocked(now, attributes)) {
                logger.debugf("Locked user '%s' attempt to login. Realm is '%s'", user.getUsername(), getRealmName());
            } else if (isPasswordChangeNeeded(now, attributes)) {
                // User needs to change his Univention password. Allow him to login, but add UPDATE_PASSWORD required action to authenticationSession
                if (user.getRequiredActionsStream().noneMatch(action -> Objects.equals(action, UserModel.RequiredAction.UPDATE_PASSWORD.name()))) {
                    // This usually happens when 49 was returned, which means that "shadowMax" is set to some positive value, which is older than Univention password expiration policy.
                    AuthenticationSessionModel authSession = session.getContext().getAuthenticationSession();
                    if (authSession != null) {
                        if (authSession.getRequiredActions().stream().noneMatch(action -> Objects.equals(action, UserModel.RequiredAction.UPDATE_PASSWORD.name()))) {
                            logger.debugf("Adding requiredAction UPDATE_PASSWORD to the authenticationSession of user %s", user.getUsername());
                            authSession.addRequiredAction(UserModel.RequiredAction.UPDATE_PASSWORD);
                        }
                    } else {
                        // Just a fallback. It should not happen during normal authentication process
                        logger.debugf("Adding requiredAction UPDATE_PASSWORD to the user %s", user.getUsername());
                        user.addRequiredAction(UserModel.RequiredAction.UPDATE_PASSWORD);
                    }
                } else {
                    // This typically happens when "shadowMax" is set to 1 and password was manually set
                    // by administrator (or user) to expire
                    logger.tracef("Skip adding required action UPDATE_PASSWORD. It was already set on user '%s' in realm '%s'", user.getUsername(), getRealmName());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void passwordUpdated(UserModel user, LDAPObject ldapUser, UserCredentialModel password) {
    }

    private boolean isAccountDisabled(Map<String, Set<String>> attributes) {
        if (attributes.containsKey(SHADOW_EXPIRE)) {
            final long shadowExpire = Long.parseLong(attributes.get(SHADOW_EXPIRE).iterator().next());
            return 1 == shadowExpire;
        } else if (attributes.containsKey(SAMBA_ACCT_FLAGS)) {
            return attributes.get(SAMBA_ACCT_FLAGS).iterator().next().contains("L");
        } else if (attributes.containsKey(KRB5_KDC_FLAGS)) {
            return 254 == Long.parseLong(attributes.get(KRB5_KDC_FLAGS).iterator().next());
        }

        return false;
    }

    private boolean isAccountExpired(Instant now, Map<String, Set<String>> attributes)   {
        if (attributes.containsKey(KRB5_VALID_END)) {
            try {
                final Date timeValidEnd = krb5Format.parse(attributes.get(KRB5_VALID_END).iterator().next());
                return timeValidEnd.before(Date.from(now));
            } catch(java.text.ParseException e) {
                logger.warnf("Could not parse krb5ValidEnd Attribute: %s", e.getMessage());
                return false;
            }
        }
        if (attributes.containsKey(SHADOW_EXPIRE)) {
            final long shadowExpire = Long.parseLong(attributes.get(SHADOW_EXPIRE).iterator().next());
            return shadowExpire < floor(now.toEpochMilli() / 86400000.0);
        }
        return false;
    }

    private boolean isAccountLocked(Instant now, Map<String, Set<String>> attributes) {
        if (attributes.containsKey(SAMBA_KICKOFF_TIME)) {
            final long timeKickOff = Long.parseLong(attributes.get(SAMBA_KICKOFF_TIME).iterator().next());
            return timeKickOff * 1000 < now.toEpochMilli();
        }
        return false;
    }

    private boolean isPasswordChangeNeeded(Instant now, Map<String, Set<String>> attributes) {
        if (attributes.containsKey(SHADOW_MAX) && attributes.containsKey(SHADOW_LAST_CHANGE)) {
            final long shadowMax = Long.parseLong(attributes.get(SHADOW_MAX).iterator().next());
            final long shadowLastChange = Long.parseLong(attributes.get(SHADOW_LAST_CHANGE).iterator().next());
            return (shadowMax + shadowLastChange) < (now.toEpochMilli() / 86400000);
        } else if (attributes.containsKey(KRB5_PASSWORD_END)) {
            try {
                final Date timePasswordEnd = krb5Format.parse(attributes.get(KRB5_PASSWORD_END).iterator().next());
                logger.debugf("Could not parse krb5PasswordEnd Attribute:" );
                return timePasswordEnd.before(Date.from(now));
            } catch(java.text.ParseException e) {
                logger.debugf("Could not parse krb5PasswordEnd Attribute: %s", e.getMessage());
                return true;
            }
        } else if (attributes.containsKey(SAMBA_PWD_MUST_CHANGE)) {
            final long sambaPwdMustChange = Long.parseLong(attributes.get(SAMBA_PWD_MUST_CHANGE).iterator().next());
            return 0 == sambaPwdMustChange;
        }

        return false;
    }

    protected ModelException processFailedPasswordUpdateException(ModelException e) {
        if (e.getCause() == null || e.getCause().getMessage() == null) {
            return e;
        }

        String exceptionMessage = e.getCause().getMessage().replace('\n', ' ');
        logger.debugf("Failed to update Univention password. Exception message: %s", exceptionMessage);
        exceptionMessage = exceptionMessage.toUpperCase();
        return e;
    }

    private String getRealmName() {
        RealmModel realm = session.getContext().getRealm();
        return (realm != null) ? realm.getName() : "null";
    }

    @Override
    public LDAPOperationDecorator beforePasswordUpdate(UserModel user, LDAPObject ldapUser, UserCredentialModel password) {
        // Not apply policies if password is reset by admin (not by user themself)
        if (password.isAdminRequest()) {
            return null;
        }

        boolean applyDecorator = mapperModel.get(LDAP_PASSWORD_POLICY_HINTS_ENABLED, false);
        return applyDecorator ? new LDAPServerPolicyHintsDecorator() : null;
    }

    @Override
    public void passwordUpdateFailed(UserModel user, LDAPObject ldapUser, UserCredentialModel password, ModelException exception) throws ModelException {
        throw processFailedPasswordUpdateException(exception);
    }

    public class UniventionUserModelDelegate extends TxAwareLDAPUserModelDelegate {

        private final LDAPObject ldapUser;

        public UniventionUserModelDelegate(UserModel delegate, LDAPObject ldapUser) {
            super(delegate, ldapProvider, ldapUser);
            this.ldapUser = ldapUser;
        }

        @Override
        public boolean isEnabled() {
            boolean kcEnabled = super.isEnabled();

            if (getShadowExpire() > 0) {
                return true;
            }
            // If new Univention user is created and shadowExpire is still 0, Univention account is in disabled state. User is not able to login via Univention anyway
            return false;
        }

        @Override
        public void addRequiredAction(RequiredAction action) {
            String actionName = action.name();
            addRequiredAction(actionName);
        }

        @Override
        public void addRequiredAction(String action) {
            if (RequiredAction.UPDATE_PASSWORD.toString().equals(action)) {
                UniventionUserAccountControlStorageMapper.logger.debugf("Going to propagate required action UPDATE_PASSWORD to Univention for ldap user '%s'. Keycloak user '%s' in realm '%s'",
                        ldapUser.getDn().toString(), getUsername(), getRealmName());

                markUpdatedRequiredActionInTransaction(action);
            } else {
                // Update DB
                UniventionUserAccountControlStorageMapper.logger.debugf("Going to add required action '%s' of user '%s' in realm '%s' to the DB", action, getUsername(), getRealmName());
                super.addRequiredAction(action);
            }
        }

        @Override
        public void removeRequiredAction(String action) {
            // Always update DB
            UniventionUserAccountControlStorageMapper.logger.debugf("Going to remove required action UPDATE_PASSWORD from Univention for ldap user '%s'. Account control: %s, Keycloak user '%s' in realm '%s'");
            super.removeRequiredAction(action);
                markUpdatedRequiredActionInTransaction(action);
        }

        @Override
        public Stream<String> getRequiredActionsStream() {
            if (getShadowExpire() == 0) {
                UniventionUserAccountControlStorageMapper.logger.debugf("Required action UPDATE_PASSWORD is set in LDAP for user '%s' in realm '%s'", getUsername(), getRealmName());
                return Stream.concat(super.getRequiredActionsStream(), Stream.of(RequiredAction.UPDATE_PASSWORD.toString()))
                        .distinct();
            }
            return super.getRequiredActionsStream();
        }

        protected long getShadowExpire() {
            final String shadowExpire = ldapUser.getAttributeAsString(SHADOW_EXPIRE);
            return shadowExpire == null ? 1 : Long.parseLong(shadowExpire);
        }
    }
}
