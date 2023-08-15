package de.univention.keycloak;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class UniventionUpdatePasswordFactory implements RequiredActionFactory {

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return new UniventionUpdatePassword(session);
    }

    @Override
    public void init(Config.Scope config) {
        // NOOP
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // NOOP
    }

    @Override
    public void close() {
        // NOOP
    }

    @Override
    public String getId() {
        return UniventionUpdatePassword.ID;
    }

    @Override
    public String getDisplayText() {
        return "Univention update password";
    }

    @Override
    public boolean isOneTimeAction() {
        return true;
    }
}
