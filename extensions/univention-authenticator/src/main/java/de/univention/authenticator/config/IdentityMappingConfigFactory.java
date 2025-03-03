package de.univention.authenticator.config;

import de.univention.authenticator.UniventionAuthenticator;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.jboss.logging.Logger;

public class IdentityMappingConfigFactory {
    /**
     * Creates a Config instance from an AuthenticatorConfigModel.
     *
     * @param context The Keycloak authenticator flow context
     * @return A new Config instance.
     */
    private static final Logger logger = Logger.getLogger(UniventionAuthenticator.class);

    public static IdentityMappingConfig createConfig(AuthenticationFlowContext context)  {
        return new IdentityMappingConfig(context);
    }
}
