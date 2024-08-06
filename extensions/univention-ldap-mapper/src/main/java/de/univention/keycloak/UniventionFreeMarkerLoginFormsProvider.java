package de.univention.keycloak;

import jakarta.ws.rs.core.Response;
import org.keycloak.forms.login.LoginFormsPages;
import org.keycloak.forms.login.freemarker.FreeMarkerLoginFormsProvider;
import org.keycloak.models.KeycloakSession;


public class UniventionFreeMarkerLoginFormsProvider extends FreeMarkerLoginFormsProvider {

    public UniventionFreeMarkerLoginFormsProvider(KeycloakSession session) {
        super(session);
    }

    @Override
    protected Response createResponse(LoginFormsPages page) {
        final Object errorDetail = this.session.getAttribute("errorDetail");
        if (errorDetail != null) {
            this.attributes.put("errorDetail", errorDetail);
        }
        return super.createResponse(page);
    }
}
