package de.univention.keycloak;

import org.keycloak.Config;
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
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.storage.ldap.mappers.*;
import de.univention.keycloak.UniventionUserAccountControlStorageMapper.*;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.idm.model.LDAPObject;
import org.keycloak.storage.ldap.mappers.LDAPStorageMapper;
import org.keycloak.storage.ldap.mappers.AbstractLDAPStorageMapper;
import org.keycloak.storage.ldap.idm.query.internal.LDAPQuery;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class UniventionSelfService implements RequiredActionProvider, RequiredActionFactory {

    public static final String ID = "UNIVENTION_SELF_SERVICE";

    private static final Logger logger = Logger.getLogger(UniventionSelfService.class);
    private final KeycloakSession session;

    public UniventionSelfService(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {

        // Show form
        context.challenge(createForm(context, null));
    }

    @Override
    public void processAction(RequiredActionContext context) {
        AuthenticationSessionModel authSession = context.getAuthenticationSession();
        RealmModel realm = context.getRealm();
        UserModel user = context.getUser();
        KeycloakSession session = context.getSession();
	user.removeRequiredAction(ID);
	context.success();
    }

    @Override
    public void evaluateTriggers(RequiredActionContext context) {
    
    }

    @Override
    public void close() {
    }

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return this;
    }

    @Override
    public String getDisplayText() {
        return "Univention Self Service";
    }

    @Override
    public String getId() {
        return ID;
    }

   @Override
   public void init(Config.Scope config) {

   }

   @Override
   public void postInit(KeycloakSessionFactory factory) {

   }


    protected Response createForm(RequiredActionContext context, Consumer<LoginFormsProvider> formCustomizer) {

        LoginFormsProvider form = context.form();
        form.setAttribute("username", context.getUser().getUsername());

        if (formCustomizer != null) {
            formCustomizer.accept(form);
        }

        return form.createForm("univention-self-service.ftl");
    }

}

