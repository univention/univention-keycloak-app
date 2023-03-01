package de.univention.keycloak;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.jboss.logging.Logger;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.requiredactions.UpdatePassword;
import org.keycloak.events.Details;
import org.keycloak.events.Errors;
import org.keycloak.events.EventBuilder;
import org.keycloak.events.EventType;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.*;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.services.managers.AuthenticationManager;
import org.keycloak.services.messages.Messages;
import org.keycloak.services.validation.Validation;
import org.keycloak.sessions.AuthenticationSessionModel;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;


class UniventionUpdatePasswordException extends IOException {

    public UniventionUpdatePasswordException() {
    }

    public UniventionUpdatePasswordException(String message) {
        super(message);
    }

    public UniventionUpdatePasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniventionUpdatePasswordException(Throwable cause) {
        super(cause);
    }
}

public class UniventionUpdatePassword extends UpdatePassword {
    public static final String ID = "UNIVENTION_UPDATE_PASSWORD";
    private static final String UPDATE_PASSWORD_FORM = "univention-login-update-password.ftl";

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

        return form.createForm(UPDATE_PASSWORD_FORM);
    }

    private void resetPassword(ResetPasswordRequest resetPasswordRequest) throws UniventionUpdatePasswordException {
        try {
            final URL url = new URL(String.format("https://%s/univention/auth", System.getenv("KEYCLOAK_PASSWORD_CHANGE_ENDPOINT")));
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json; q=1, */*");
            con.setRequestProperty("Accept-Language", resetPasswordRequest.getOptions().getLanguage());
            con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            con.setDoOutput(true);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new Jdk8Module());

            final String payloadString = objectMapper.writeValueAsString(resetPasswordRequest);
            try (OutputStream os = con.getOutputStream()) {
                final byte[] payload = payloadString.getBytes("utf-8");
                os.write(payload, 0, payload.length);
            }

            final int statusCode = con.getResponseCode();
            InputStream is = null;
            if (statusCode >= 200 && statusCode < 400) {
                is = con.getInputStream();
            } else {
                is = con.getErrorStream();
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                final ResetPasswordResponse resp = objectMapper.readValue(response.toString(), ResetPasswordResponse.class);
                if (200 != resp.getStatus()) {
                    throw new UniventionUpdatePasswordException(resp.getMessage());
                }
            }
        }
        catch (UniventionUpdatePasswordException e) {
            logger.warnf(e.getMessage());
            throw e;
        }
        catch (IOException e) {
            logger.warnf(e.getMessage());
            throw new UniventionUpdatePasswordException("Update password failed");
        }
    }

    public void processAction(RequiredActionContext context) {
        EventBuilder event = context.getEvent();
        AuthenticationSessionModel authSession = context.getAuthenticationSession();
        RealmModel realm = context.getRealm();
        UserModel user = context.getUser();
        KeycloakSession session = context.getSession();
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        event.event(EventType.UPDATE_PASSWORD);
        final String password = formData.getFirst("password");
        final String passwordNew = formData.getFirst("password-new");
        final String passwordConfirm = formData.getFirst("password-confirm");
        final Locale locale = session.getContext().resolveLocale(user);

        EventBuilder errorEvent = event.clone().event(EventType.UPDATE_PASSWORD_ERROR)
                .client(authSession.getClient())
                .user(authSession.getAuthenticatedUser());

        if (Validation.isBlank(password)) {
            LoginFormsProvider form = context.form()
                    .setAttribute("username", authSession.getAuthenticatedUser().getUsername())
                    .addError(new FormMessage(Validation.FIELD_PASSWORD, Messages.MISSING_PASSWORD));

            Response challenge = form.createForm(UPDATE_PASSWORD_FORM);
            context.challenge(challenge);
            errorEvent.error(Errors.PASSWORD_MISSING);
            return;
        } else if (Validation.isBlank(passwordNew)) {
            LoginFormsProvider form = context.form()
                    .setAttribute("username", authSession.getAuthenticatedUser().getUsername())
                    .addError(new FormMessage("password-new", "missingPasswordNewMessage"));

            Response challenge = form.createForm(UPDATE_PASSWORD_FORM);
            context.challenge(challenge);
            errorEvent.error(Errors.PASSWORD_MISSING);
            return;
        } else if (!passwordNew.equals(passwordConfirm)) {
            LoginFormsProvider form = context.form()
                    .setAttribute("username", authSession.getAuthenticatedUser().getUsername())
                    .addError(new FormMessage(Validation.FIELD_PASSWORD_CONFIRM, Messages.NOTMATCH_PASSWORD));

            Response challenge = form.createForm(UPDATE_PASSWORD_FORM);
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
            final ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest(
                    new ResetPasswordRequestParams.Builder()
                            .username(user.getUsername())
                            .password(password)
                            .newPassword(passwordNew)
                            .language(locale.getLanguage())
                            .build()
            );

            resetPassword(resetPasswordRequest);
            context.success();
        } catch (ModelException me) {
            errorEvent.detail(Details.REASON, me.getMessage()).error(Errors.PASSWORD_REJECTED);
            Response challenge = context.form()
                    .setAttribute("username", authSession.getAuthenticatedUser().getUsername())
                    .setError(me.getMessage(), me.getParameters())
                    .createResponse(UserModel.RequiredAction.UPDATE_PASSWORD);
            context.challenge(challenge);
        } catch (UniventionUpdatePasswordException ue) {
            errorEvent.detail(Details.REASON, ue.getMessage()).error(Errors.PASSWORD_REJECTED);
            LoginFormsProvider form = context.form()
                    .setAttribute("username", authSession.getAuthenticatedUser().getUsername())
                    .setError(ue.getMessage());
            Response challenge = form.createForm(UPDATE_PASSWORD_FORM);
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

