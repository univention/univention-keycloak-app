package de.univention.keycloak;

import org.jboss.logging.Logger;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.requiredactions.UpdatePassword;
import org.keycloak.events.Details;
import org.keycloak.events.Errors;
import org.keycloak.events.EventBuilder;
import org.keycloak.events.EventType;
import org.keycloak.forms.login.LoginFormsPages;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.forms.login.freemarker.Templates;
import org.keycloak.models.*;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.services.managers.AuthenticationManager;
import org.keycloak.services.messages.Messages;
import org.keycloak.services.validation.Validation;
import org.keycloak.sessions.AuthenticationSessionModel;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class UniventionUpdatePassword extends UpdatePassword {
    public static final String ID = "UNIVENTION_UPDATE_PASSWORD";

    private static final Logger logger = Logger.getLogger(UniventionUpdatePassword.class);
    private final KeycloakSession session;

    public UniventionUpdatePassword(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {

        // Show form
        context.challenge(createForm(context, null));
    }

    protected Response createForm(RequiredActionContext context, Consumer<LoginFormsProvider> formCustomizer) {

        LoginFormsProvider form = context.form();
        form.setAttribute("username", context.getUser().getUsername());

        if (formCustomizer != null) {
            formCustomizer.accept(form);
        }

        return form.createForm(Templates.getTemplate(LoginFormsPages.LOGIN_UPDATE_PASSWORD));
    }

    public void processAction(RequiredActionContext context) {
        EventBuilder event = context.getEvent();
        AuthenticationSessionModel authSession = context.getAuthenticationSession();
        RealmModel realm = context.getRealm();
        UserModel user = context.getUser();
        KeycloakSession session = context.getSession();
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        event.event(EventType.UPDATE_PASSWORD);
        String passwordNew = formData.getFirst("password-new");
        String passwordConfirm = formData.getFirst("password-confirm");

        EventBuilder errorEvent = event.clone().event(EventType.UPDATE_PASSWORD_ERROR)
                .client(authSession.getClient())
                .user(authSession.getAuthenticatedUser());

        if (Validation.isBlank(passwordNew)) {
            Response challenge = context.form()
                    .setAttribute("username", authSession.getAuthenticatedUser().getUsername())
                    .addError(new FormMessage(Validation.FIELD_PASSWORD, Messages.MISSING_PASSWORD))
                    .createResponse(UserModel.RequiredAction.UPDATE_PASSWORD);
            context.challenge(challenge);
            errorEvent.error(Errors.PASSWORD_MISSING);
            return;
        } else if (!passwordNew.equals(passwordConfirm)) {
            Response challenge = context.form()
                    .setAttribute("username", authSession.getAuthenticatedUser().getUsername())
                    .addError(new FormMessage(Validation.FIELD_PASSWORD_CONFIRM, Messages.NOTMATCH_PASSWORD))
                    .createResponse(UserModel.RequiredAction.UPDATE_PASSWORD);
            context.challenge(challenge);
            errorEvent.error(Errors.PASSWORD_CONFIRM_ERROR);
            return;
        }

        if (getId().equals(authSession.getClientNote(Constants.KC_ACTION_EXECUTING))
                && "on".equals(formData.getFirst("logout-sessions"))) {
            session.sessions().getUserSessionsStream(realm, user)
                    .filter(s -> !Objects.equals(s.getId(), authSession.getParentSession().getId()))
                    .collect(Collectors.toList()) // collect to avoid concurrent modification as backchannelLogout removes the user sessions.
                    .forEach(s -> AuthenticationManager.backchannelLogout(session, realm, s, session.getContext().getUri(),
                            context.getConnection(), context.getHttpRequest().getHttpHeaders(), true));
        }

        try {
            // TODO: call UDM/UMC here
//            user.credentialManager().updateCredential(UserCredentialModel.password(passwordNew, false));
//            context.success();
            context.failure();
        } catch (ModelException me) {
            errorEvent.detail(Details.REASON, me.getMessage()).error(Errors.PASSWORD_REJECTED);
            Response challenge = context.form()
                    .setAttribute("username", authSession.getAuthenticatedUser().getUsername())
                    .setError(me.getMessage(), me.getParameters())
                    .createResponse(UserModel.RequiredAction.UPDATE_PASSWORD);
            context.challenge(challenge);
        } catch (Exception ape) {
            errorEvent.detail(Details.REASON, ape.getMessage()).error(Errors.PASSWORD_REJECTED);
            Response challenge = context.form()
                    .setAttribute("username", authSession.getAuthenticatedUser().getUsername())
                    .setError(ape.getMessage())
                    .createResponse(UserModel.RequiredAction.UPDATE_PASSWORD);
            context.challenge(challenge);
        }
    }
}

