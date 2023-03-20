/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.univention.keycloak;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.AuthenticatorConfigModel;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RoleModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.events.Errors;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.OAuth2Constants;


import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * Authenticator authenticates if a client specific access role has been granted
 */

public class UniventionAppAuthenticator implements Authenticator {
    private final Logger log = Logger.getLogger(UniventionAppAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
    UserModel user = context.getUser();
    RealmModel realm = context.getRealm();
    AuthenticatorConfigModel authConfig = context.getAuthenticatorConfig();
    String clientId = context.getAuthenticationSession().getClient().getClientId();
    String clientName = context.getAuthenticationSession().getClient().getName();
    log.debugv("Logging into clientId {0}", clientId);

    if (user != null) {
        //String requiredRole = authConfig.getuniventionAonfig().cessRole);
        String accessRole = clientId + "." + "univentionClientAccess";
        RoleModel role = KeycloakModelUtils.getRoleFromString(realm, accessRole);
        if (role == null) {
            log.debugv("Role {0} does not exist", role);
        context.success();
        return;
        }
        if (user.hasRole(role)) {
            log.debugv("User {0} has role {1}", user, role);
            context.success();
        }else {
            String errorMessage = "User is not enabled for this client";
            if ( ! clientName.equals("") ) {
                errorMessage = "User is not enabled for " + clientName;
            }
            log.debugv("user {0} doesn't have role {1}", user, role);
            context.getEvent().error(Errors.ACCESS_DENIED);
            Response challenge = context.form()
                .setError(errorMessage)
                .createErrorPage(Response.Status.UNAUTHORIZED);
            context.failure(AuthenticationFlowError.ACCESS_DENIED, challenge);
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
