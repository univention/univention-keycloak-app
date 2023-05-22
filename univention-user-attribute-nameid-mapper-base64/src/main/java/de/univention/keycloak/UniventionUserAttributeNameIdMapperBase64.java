package de.univention.keycloak;

import org.keycloak.models.AuthenticatedClientSessionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.ProtocolMapperUtils;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.protocol.saml.mappers.AbstractSAMLProtocolMapper;
import org.keycloak.protocol.saml.mappers.SAMLNameIdMapper;
import org.keycloak.protocol.saml.mappers.NameIdMapperHelper;

import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UniventionUserAttributeNameIdMapperBase64 extends AbstractSAMLProtocolMapper implements SAMLNameIdMapper {

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();
    private static final Logger logger = Logger.getLogger(UniventionUserAttributeNameIdMapperBase64.class);

    static {
        NameIdMapperHelper.setConfigProperties(configProperties);
        ProviderConfigProperty property;
        property = new ProviderConfigProperty();
        property.setName(ProtocolMapperUtils.USER_ATTRIBUTE);
        property.setLabel(ProtocolMapperUtils.USER_MODEL_ATTRIBUTE_LABEL);
        property.setHelpText(ProtocolMapperUtils.USER_MODEL_ATTRIBUTE_HELP_TEXT);
        configProperties.add(property);
    }

    public static final String PROVIDER_ID = "univention-saml-user-attribute-nameid-mapper-base64";


    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "User Attribute Mapper For NameID (base64)";
    }

    @Override
    public String getDisplayCategory() {
        return "NameID Mapper (base64)";
    }

    @Override
    public String getHelpText() {
        return "Map user attribute to SAML NameID value (base64).";
    }

    @Override
    public String mapperNameId(String nameIdFormat, ProtocolMapperModel mappingModel, KeycloakSession session,
            UserSessionModel userSession, AuthenticatedClientSessionModel clientSession) {
        String nameId = userSession.getUser().getFirstAttribute(mappingModel.getConfig().get(ProtocolMapperUtils.USER_ATTRIBUTE));
        String encodedNameId = Base64.getEncoder().encodeToString(nameId.getBytes());
        logger.debug("base64 encoded nameId: " + encodedNameId);
        return encodedNameId;
    }

}
