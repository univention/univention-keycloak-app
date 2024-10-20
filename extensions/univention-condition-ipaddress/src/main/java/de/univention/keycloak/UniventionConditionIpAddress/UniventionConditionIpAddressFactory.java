package de.univention.keycloak;

import org.keycloak.Config;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.Arrays;
import java.util.List;

import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticatorFactory;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticator;
import static org.keycloak.provider.ProviderConfigProperty.BOOLEAN_TYPE;
import static org.keycloak.provider.ProviderConfigProperty.MULTIVALUED_STRING_TYPE;
import static org.keycloak.models.Constants.CFG_DELIMITER;


public class UniventionConditionIpAddressFactory implements ConditionalAuthenticatorFactory {

    public static final String PROVIDER_ID = "univention-condition-ipaddress";

    public static final String CONF_IP_SUBNET = "ip-subnets";

    private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = new AuthenticationExecutionModel.Requirement[]{
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.DISABLED
    };

    @Override
    public void init(Config.Scope config) {
        // no-op
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // no-op
    }

    @Override
    public void close() {
        // no-op
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Univention Condition - IP subnet";
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getHelpText() {
        return "Flow is executed only if the client ip address is in specified ip ranges / subnets";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        final ProviderConfigProperty ipSubnets = new ProviderConfigProperty();
        ipSubnets.setType(MULTIVALUED_STRING_TYPE);
        ipSubnets.setName(CONF_IP_SUBNET);
        ipSubnets.setDefaultValue("0.0.0.0/0" + CFG_DELIMITER + "::/0");
        ipSubnets.setLabel("IP subnets to be enabled to use the authenticator");
        ipSubnets.setHelpText("A list of IP subnets in CIDR notation.");
        return Arrays.asList(ipSubnets);
    }

    @Override
    public ConditionalAuthenticator getSingleton() {
        return UniventionConditionIpAddress.SINGLETON;
    }
}
