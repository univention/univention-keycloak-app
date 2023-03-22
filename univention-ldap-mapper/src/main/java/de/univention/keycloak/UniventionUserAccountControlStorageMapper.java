package de.univention.keycloak;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.ModelException;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.sessions.AuthenticationSessionModel;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.idm.model.LDAPObject;
import org.keycloak.storage.ldap.idm.query.internal.LDAPQuery;
import org.keycloak.storage.ldap.mappers.AbstractLDAPStorageMapper;
import org.keycloak.storage.ldap.mappers.LDAPOperationDecorator;
import org.keycloak.storage.ldap.mappers.PasswordUpdateCallback;
import org.keycloak.storage.ldap.mappers.TxAwareLDAPUserModelDelegate;
import org.keycloak.storage.ldap.mappers.msad.LDAPServerPolicyHintsDecorator;

import javax.naming.AuthenticationException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
    public static final String SELF_SERVICE_EMAIL_VERIFIED = "univentionPasswordRecoveryEmailVerified";
    public static final String SELF_SERVICE_REGISTERED = "univentionRegisteredThroughSelfService";
    public static final String LDAP_PASSWORD_POLICY_HINTS_ENABLED = "ldap.password.policy.hints.enabled";
    private static final Pattern AUTH_EXCEPTION_REGEX = Pattern.compile("^(?=.*LDAP: error code ([0-9]+))(?!.*Invalid Credentials).*");
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
        query.addReturningLdapAttribute(SELF_SERVICE_EMAIL_VERIFIED);
        query.addReturningLdapAttribute(SELF_SERVICE_REGISTERED);
    }

    public boolean onAuthenticationFailure(LDAPObject ldapUser, UserModel user, AuthenticationException ldapException, RealmModel realm) {
        final String exceptionMessage = ldapException.getMessage();
        Matcher m = AUTH_EXCEPTION_REGEX.matcher(exceptionMessage);
        if (m.matches()) {
            final String errorCode = m.group(1);
            return processAuthErrorCode(errorCode, user, ldapUser.getAttributes());
        } else {
            return false;
        }
    }

    protected boolean processAuthErrorCode(String errorCode, UserModel user, Map<String, Set<String>> attributes) {
        logger.debugf("Univention Error code is '%s' after failed LDAP login of user '%s'. Realm is '%s'", errorCode, user.getUsername(), getRealmName());

        if (errorCode.equals("49")) {
            final AccountAttributesHelper accountAttributesHelper = new AccountAttributesHelper(attributes);

            if (accountAttributesHelper.isAccountDisabled()) {
                logger.debugf("Disabled user '%s' attempt to login. Realm is '%s'", user.getUsername(), getRealmName());
            } else if (accountAttributesHelper.isAccountExpired()) {
                logger.debugf("Expired user '%s' attempt to login. Realm is '%s'", user.getUsername(), getRealmName());
            } else if (accountAttributesHelper.isAccountLocked()) {
                logger.debugf("Locked user '%s' attempt to login. Realm is '%s'", user.getUsername(), getRealmName());
            } else if (accountAttributesHelper.isPasswordChangeNeeded()) {
                addPasswordChangeAction(user);
                return true;
            }
        }

        return false;
    }

    protected void addPasswordChangeAction(UserModel user) {
        // User needs to change his Univention password. Allow him to login, but add UPDATE_PASSWORD required action to authenticationSession
        if (user.getRequiredActionsStream().noneMatch(action -> Objects.equals(action, UniventionUpdatePassword.ID))) {
            // This usually happens when 49 was returned, which means that "shadowMax" is set to some positive value, which is older than Univention password expiration policy.
            AuthenticationSessionModel authSession = session.getContext().getAuthenticationSession();
            if (authSession != null) {
                if (authSession.getRequiredActions().stream().noneMatch(action -> Objects.equals(action, UniventionUpdatePassword.ID))) {
                    logger.debugf("Adding requiredAction UPDATE_PASSWORD to the authenticationSession of user %s", user.getUsername());
                    authSession.addRequiredAction(UniventionUpdatePassword.ID);
                }
            } else {
                // Just a fallback. It should not happen during normal authentication process
                logger.debugf("Adding requiredAction UPDATE_PASSWORD to the user %s", user.getUsername());
                user.addRequiredAction(UniventionUpdatePassword.ID);
            }
        } else {
            // This typically happens when "shadowMax" is set to 1 and password was manually set
            // by administrator (or user) to expire
            logger.debugf("Skip adding required action UPDATE_PASSWORD. It was already set on user '%s' in realm '%s'", user.getUsername(), getRealmName());
        }
    }

    protected ModelException processFailedPasswordUpdateException(ModelException e) {
        if (e.getCause() == null || e.getCause().getMessage() == null) {
            return e;
        }

        String exceptionMessage = e.getCause().getMessage().replace('\n', ' ');
        logger.debugf("Failed to update Univention password. Exception message: %s", exceptionMessage);
        exceptionMessage = exceptionMessage.toUpperCase();
//
//        Matcher m = AUTH_INVALID_NEW_PASSWORD.matcher(exceptionMessage);
//        if (m.matches()) {
//            String errorCode = m.group(1);
//            String errorCode2 = m.group(2);
//
//            // 52D corresponds to ERROR_PASSWORD_RESTRICTION. See https://msdn.microsoft.com/en-us/library/windows/desktop/ms681385(v=vs.85).aspx
//            if ((errorCode.equals("53")) && errorCode2.endsWith("52D")) {
//                ModelException me = new ModelException("invalidPasswordGenericMessage", e);
//                return me;
//            }
//        }

        return e;
    }

    protected String getRealmName() {
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
    public void passwordUpdated(UserModel user, LDAPObject ldapUser, UserCredentialModel password) {

//        // Normally it's read-only
//        ldapUser.removeReadOnlyAttributeName(LDAPConstants.PWD_LAST_SET);
//
//        ldapUser.setSingleAttribute(LDAPConstants.PWD_LAST_SET, "-1");

//        control.remove(UserAccountControl.PASSWD_NOTREQD);
//        control.remove(UserAccountControl.PASSWORD_EXPIRED);
    }

    @Override
    public void passwordUpdateFailed(UserModel user, LDAPObject ldapUser, UserCredentialModel password, ModelException exception) throws ModelException {
        throw processFailedPasswordUpdateException(exception);
    }

    static class AccountAttributesHelper {

        private final Instant now;
        private final Map<String, Set<String>> attributes;

        public AccountAttributesHelper(Map<String, Set<String>> attributes) {
            this.now = Instant.now();
            this.attributes = attributes;
        }

        public boolean isAccountDisabled() {
            if (attributes.containsKey(SHADOW_EXPIRE)) {
                final long shadowExpire = Long.parseLong(attributes.get(SHADOW_EXPIRE).iterator().next());
                if (1 == shadowExpire) {
                    return true;
                }
            }
            if (attributes.containsKey(SAMBA_ACCT_FLAGS)) {
                if (attributes.get(SAMBA_ACCT_FLAGS).iterator().next().contains("L")) {
                    return true;
                }
            }
            if (attributes.containsKey(KRB5_KDC_FLAGS)) {
                if (254 == Long.parseLong(attributes.get(KRB5_KDC_FLAGS).iterator().next())) {
                    return true;
                }
            }

            return false;
        }

        public boolean isAccountExpired() {
            if (attributes.containsKey(SHADOW_EXPIRE)) {
                final long shadowExpire = Long.parseLong(attributes.get(SHADOW_EXPIRE).iterator().next());
                if (shadowExpire <= floor(now.getEpochSecond() / 86400.0)){
                    return true;
                }
            }
            if (attributes.containsKey(KRB5_VALID_END)) {
                try {
                    final Date timeValidEnd = krb5Format.parse(attributes.get(KRB5_VALID_END).iterator().next());
                    return timeValidEnd.before(Date.from(now));
                } catch(java.text.ParseException e) {
                    logger.errorf("Could not parse krb5ValidEnd Attribute: %s", e.getMessage());
                }
            }

            return false;
        }

        public boolean isAccountLocked() {
            if (attributes.containsKey(SAMBA_KICKOFF_TIME)) {
                final long timeKickOff = Long.parseLong(attributes.get(SAMBA_KICKOFF_TIME).iterator().next());
                return timeKickOff < now.getEpochSecond();
            }

            return false;
        }

        public boolean isAccountVerifiedThroughSelfService() {
            if (attributes.containsKey(SELF_SERVICE_EMAIL_VERIFIED) && attributes.containsKey(SELF_SERVICE_REGISTERED)) {
                    final String verified = attributes.get(SELF_SERVICE_EMAIL_VERIFIED).iterator().next();
                    final String registered = attributes.get(SELF_SERVICE_REGISTERED).iterator().next();
                    if (registered.equals("FALSE") || ((registered.equals("TRUE") && verified.equals("TRUE")))) {
                        return true;
                    }
                    return false;
            }
            return true;
        }

        public boolean isPasswordChangeNeeded() {
            if (attributes.containsKey(SHADOW_MAX) && attributes.containsKey(SHADOW_LAST_CHANGE)) {
                final long shadowMax = Long.parseLong(attributes.get(SHADOW_MAX).iterator().next());
                final long shadowLastChange = Long.parseLong(attributes.get(SHADOW_LAST_CHANGE).iterator().next());
                if (shadowMax + shadowLastChange < floor(now.getEpochSecond() / 86400.0)) {
                    return true;
                }
            }
            if (attributes.containsKey(KRB5_PASSWORD_END)) {
               try {
                   final Date timePasswordEnd = krb5Format.parse(attributes.get(KRB5_PASSWORD_END).iterator().next());
                   if (timePasswordEnd.before(Date.from(now))) {
                       return true;
                   }
               } catch(java.text.ParseException e) {
                   logger.errorf("Could not parse krb5PasswordEnd Attribute: %s", e.getMessage());
               }
            }
            if (attributes.containsKey(SAMBA_PWD_MUST_CHANGE)) {
               final long sambaPwdMustChange = Long.parseLong(attributes.get(SAMBA_PWD_MUST_CHANGE).iterator().next());
               if (sambaPwdMustChange == 0) {
                   return true;
               }
            }
            return false;
        }
    }

    public class UniventionUserModelDelegate extends TxAwareLDAPUserModelDelegate {

        private final LDAPObject ldapUser;
        private final AccountAttributesHelper accountAttributesHelper;

        public UniventionUserModelDelegate(UserModel delegate, LDAPObject ldapUser) {
            super(delegate, ldapProvider, ldapUser);
            this.ldapUser = ldapUser;
            this.accountAttributesHelper = new AccountAttributesHelper(ldapUser.getAttributes());
        }

        @Override
        public boolean isEnabled() {
            return !accountAttributesHelper.isAccountDisabled();
        }

        @Override
        public void addRequiredAction(RequiredAction action) {
            String actionName = action.name();
            addRequiredAction(actionName);
        }

        @Override
        public void addRequiredAction(String action) {
            if (UniventionUpdatePassword.ID.equals(action)) {
                UniventionUserAccountControlStorageMapper.logger.debugf("Going to propagate required action UPDATE_PASSWORD to Univention for ldap user '%s'. Keycloak user '%s' in realm '%s'",
                        ldapUser.getDn().toString(), getUsername(), getRealmName());

            } else {
                UniventionUserAccountControlStorageMapper.logger.debugf("Going to add required action '%s' of user '%s' in realm '%s' to the DB", action, getUsername(), getRealmName());
                super.addRequiredAction(action);
            }
        }

        @Override
        public void removeRequiredAction(RequiredAction action) {
            String actionName = action.name();
            removeRequiredAction(actionName);
        }

        @Override
        public void removeRequiredAction(String action) {
            if (UniventionSelfService.ID.equals(action)) {
                UniventionUserAccountControlStorageMapper.logger.debugf("Going to propagate required action UNIVENTION_SELF_SERVICE to Univention for ldap user '%s'. Keycloak user '%s' in realm '%s'",
                        ldapUser.getDn().toString(), getUsername(), getRealmName());

            } else {
                super.removeRequiredAction(action);
            }
        }

        @Override
        public Stream<String> getRequiredActionsStream() {
            Stream<String> requiredActions  = super.getRequiredActionsStream();
            if (accountAttributesHelper.isPasswordChangeNeeded()) {
                UniventionUserAccountControlStorageMapper.logger.debugf("Required action UPDATE_PASSWORD is set in LDAP for user '%s' in realm '%s'", getUsername(), getRealmName());
                requiredActions = Stream.concat(requiredActions, Stream.of(UniventionUpdatePassword.ID)).distinct();
            }
            String checkVerification = System.getenv("UCS_SELF_REGISTRATION_CHECK_EMAIL_VERIFICATION");
            if (checkVerification.equals("True") && !accountAttributesHelper.isAccountVerifiedThroughSelfService()) {
                UniventionUserAccountControlStorageMapper.logger.debugf("Required action SELF_SERVICE_VERIFICATION is set in LDAP for user '%s'", requiredActions.toString());
                requiredActions = Stream.concat(requiredActions, Stream.of(UniventionSelfService.ID)).distinct();
            }
            return requiredActions;
        }
    }
}
