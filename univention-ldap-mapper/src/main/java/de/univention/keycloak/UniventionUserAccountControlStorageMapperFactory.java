package de.univention.keycloak;

import org.keycloak.component.ComponentModel;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.mappers.AbstractLDAPStorageMapper;
import org.keycloak.storage.ldap.mappers.AbstractLDAPStorageMapperFactory;

public class UniventionUserAccountControlStorageMapperFactory extends AbstractLDAPStorageMapperFactory {

    public static final String PROVIDER_ID = "univention-ldap-mapper";
//    protected static final List<ProviderConfigProperty> configProperties;
//
//    static {
//        configProperties = getConfigProps(null);
//    }
//
//    private static List<ProviderConfigProperty> getConfigProps(ComponentModel parent) {
//        return ProviderConfigurationBuilder.create()
////                .property().name(MSADUserAccountControlStorageMapper.LDAP_PASSWORD_POLICY_HINTS_ENABLED)
////                .label("Password Policy Hints Enabled")
////                .helpText("Applicable just for writable MSAD. If on, then updating password of MSAD user will use LDAP_SERVER_POLICY_HINTS_OID " +
////                        "extension, which means that advanced MSAD password policies like 'password history' or 'minimal password age' will be applied. This extension works just for MSAD 2008 R2 or newer.")
////                .type(ProviderConfigProperty.BOOLEAN_TYPE)
////                .defaultValue("false")
////                .add()
//                .build();
//
//    }

    @Override
    public String getHelpText() {
        return "Mapper specific to Univention. It's able to integrate the Univention user account state into Keycloak account state (account enabled, password is expired etc). It's using shadowMax and shadowLastChange LDAP attributes for that. " +
                "For example if shadowMax is set, using shadowLastChange we calculate if password is expired in which case the Keycloak user is required to update password. Mapper is also able to handle exception code from LDAP user authentication.";
    }

//    @Override
//    public List<ProviderConfigProperty> getConfigProperties() {
//        return configProperties;
//    }
//
//    @Override
//    public List<ProviderConfigProperty> getConfigProperties(RealmModel realm, ComponentModel parent) {
//        return getConfigProps(parent);
//    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    protected AbstractLDAPStorageMapper createMapper(ComponentModel mapperModel, LDAPStorageProvider federationProvider) {
        return new UniventionUserAccountControlStorageMapper(mapperModel, federationProvider);
    }
}
