package de.univention.keycloak;

import org.keycloak.component.ComponentModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.mappers.AbstractLDAPStorageMapper;
import org.keycloak.storage.ldap.mappers.AbstractLDAPStorageMapperFactory;

import java.util.ArrayList;
import java.util.List;

public class UniventionUserAccountControlStorageMapperFactory extends AbstractLDAPStorageMapperFactory {

    public static final String PROVIDER_ID = "univention-ldap-mapper";

    protected static final List<ProviderConfigProperty> configProperties = new ArrayList<>();

    @Override
    public String getHelpText() {
        return "Mapper specific to Univention. It's able to integrate the Univention user account state into Keycloak account state (account enabled, password is expired etc). It's using shadowExpire, shadowMax and shadowLastChange LDAP attributes for that. " +
                "For example if shadowExpire is set, this user is disabled. If shadowMax is set, using shadowLastChange we calculate if password is expired in which case the Keycloak user is required to update password. Mapper is also able to handle exception code from LDAP user authentication.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    protected AbstractLDAPStorageMapper createMapper(ComponentModel mapperModel, LDAPStorageProvider federationProvider) {
        return new UniventionUserAccountControlStorageMapper(mapperModel, federationProvider);
    }
}
