package de.univention.keycloak;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.events.Errors;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.KeycloakModelUtils;

import javax.ws.rs.core.Response;

/**
 * Authenticator authenticates if a client specific access role has been granted
 */

public class UniventionAppAuthenticator implements Authenticator {
    private final Logger log = Logger.getLogger(UniventionAppAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        final UserModel user = context.getUser();
        final RealmModel realm = context.getRealm();
        final String clientId = context.getAuthenticationSession().getClient().getClientId();
        final String clientName = context.getAuthenticationSession().getClient().getName();
        log.debugv("Logging into clientId {0}", clientId);

        if (user != null) {
            final String accessRole = String.format("%s.univentionClientAccess", clientId);
            final RoleModel role = KeycloakModelUtils.getRoleFromString(realm, accessRole);
            if (role == null) {
                log.debugv("Role {0} does not exist", role);
                context.success();
                return;
            }
            if (user.hasRole(role)) {
                log.debugv("User {0} has role {1}", user, role);
                context.success();
            } else {
                log.debugv("user {0} doesn't have role {1}", user, role);
                context.getEvent().error(Errors.ACCESS_DENIED);
                final String clientDisplayName = clientName != null && !clientName.trim().isEmpty() ? clientName : clientId;
                final Response response = context.form()
                        .setAttribute("clientDisplayName", clientDisplayName)
                        .createForm("access-denied.ftl");
                context.challenge(response);
            }
        }else {
            context.success();
        }

    }

    @Override
    public void action(AuthenticationFlowContext context) {

    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {

    }

    @Override
    public void close() {

    }
}
