package de.univention.keycloak;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import inet.ipaddr.IPAddress;
import inet.ipaddr.AddressStringException;
import inet.ipaddr.IPAddressString;

import static java.text.MessageFormat.format;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticatorFactory;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticator;
import static de.univention.keycloak.UniventionConditionIpAddressFactory.CONF_IP_SUBNET;
import static org.keycloak.models.Constants.CFG_DELIMITER_PATTERN;
import static org.keycloak.models.Constants.CFG_DELIMITER;


public class UniventionConditionIpAddress implements ConditionalAuthenticator {

    public static final UniventionConditionIpAddress SINGLETON = new UniventionConditionIpAddress();

    private static final Logger log = Logger.getLogger(UniventionConditionIpAddress.class);

    @Override
    public boolean matchCondition(AuthenticationFlowContext context) {

        final Map<String, String> config = context.getAuthenticatorConfig().getConfig();
        final Stream<IPAddress> ipSubnets = getConfiguredIpSubnets(config);

        final IPAddress clientIpAddress = getClientIpAddress(context);
        if (clientIpAddress != null) {
                return ipSubnets.anyMatch(ipSubnet -> ipSubnet.contains(clientIpAddress));
        }else {
                return false;
        }
    }

    private IPAddress getClientIpAddress(AuthenticationFlowContext context) {

        final String ipAddressStringFromClientConnection = context.getConnection().getRemoteAddr();
        final Optional<IPAddress> ipAddressFromConnection = parseIpAddress(ipAddressStringFromClientConnection);
        if (ipAddressFromConnection.isPresent()) {
            return ipAddressFromConnection.get();
        }else {
            log.warn("No valid client IP Address found");
            return null;
        }
    }

    private Stream<IPAddress> getConfiguredIpSubnets(Map<String, String> config) {

        String ipSubnetsString = config.get(CONF_IP_SUBNET);
        if ( ipSubnetsString == null ) {
                // nothing configured -> allow all IP addresses
                List<String> list = Arrays.asList("0.0.0.0/0", "::/0");
                ipSubnetsString = String.join(CFG_DELIMITER, list);
        }
        final String[] ipSubnets = CFG_DELIMITER_PATTERN.split(ipSubnetsString);
        return Arrays.stream(ipSubnets)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(this::parseIpAddress)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    private Optional<IPAddress> parseIpAddress(String text) {

        IPAddressString ipAddressString = new IPAddressString(text);

        if (!ipAddressString.isValid()) {
            log.warn("Ignoring invalid IP address " + ipAddressString);
            return Optional.empty();
        }

        try {
            final IPAddress parsedIpAddress = ipAddressString.toAddress();
            return Optional.of(parsedIpAddress);
        } catch (AddressStringException e) {
            log.warn("Ignoring invalid IP address " + ipAddressString, e);
            return Optional.empty();
        }
    }
    @Override
    public void action(AuthenticationFlowContext context) {
        // Not used
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // Not used
    }

    @Override
    public void close() {
        // Not used
    }
}
