package de.univention.keycloak;

import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.forms.login.freemarker.FreeMarkerLoginFormsProviderFactory;
import org.keycloak.models.KeycloakSession;


public class UniventionFreeMarkerLoginFormsProviderFactory extends FreeMarkerLoginFormsProviderFactory {

    @Override
    public LoginFormsProvider create(KeycloakSession session) {
        return new UniventionFreeMarkerLoginFormsProvider(session);
    }
}
