package de.univention.keycloak;

import jakarta.ws.rs.core.Response;
import org.keycloak.forms.login.LoginFormsPages;
import org.keycloak.forms.login.freemarker.FreeMarkerLoginFormsProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.services.messages.Messages;

import java.util.List;


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
        replaceAccountDisabledMessage();
        return super.createResponse(page);
    }

    /**
     * Replaces the generic "account disabled" message with the UCS account state.
     *
     * <p>Only changes a message that Keycloak already shows, so it reveals no state that Keycloak hides.</p>
     */
    protected void replaceAccountDisabledMessage() {
        final Object reason = this.session.getAttribute(UniventionUserAccountControlStorageMapper.ACCOUNT_STATE_REASON);
        if (reason == null || this.messages == null) {
            return;
        }
        this.messages = this.messages.stream()
                .map(m -> Messages.ACCOUNT_DISABLED.equals(m.getMessage()) ? new FormMessage(m.getField(), reason.toString()) : m)
                .toList();
    }

    List<FormMessage> getMessages() {
        return this.messages;
    }
}
