package de.univention.authenticator.config;

import org.keycloak.models.AuthenticatorConfigModel;

public class Config {
    /*

"objectGUID" = SourceUserPrimaryID_KeycloakKey (no need for a variable)

remSourceID_key ->  SourceIdentityProviderID_KeycloakAndUDMKey

remIdGUID_key -> SourceUserPrimaryID_UDMKey
remSourceID_key -> SourceIdentityProviderID_KeycloakAndUDMKey

     */

    /**
     * Maps a value from a Keycloak user attribute to a value of a UDM user attribute with the same name.
     * Used to save the ID of the upstream Identity Provider (ADFS, upstream Keycloak)
       at the same attribute name as the keycloak value.
     */
    public String sourceIdentityProviderID_KeycloakAndUDMKey; // formally remSourceID_key

    /**
     * Keycloak user attribute name at which the users Source Directory primary ID is saved.
     * Mapped from an arbitrary value in the authentication token to the hard-coded keycloak user value "objectGUID"
       by a preceding keycloak mapper.
     */
    public String sourceUserPrimaryID_KeycloakKey = "objectGUID";

    /**
     * UDM user attribute name at which the users Source directory primary ID is saved.
     */
    public String sourceUserPrimaryID_UDMKey; //formally remIdGUID_key

    /**
     * LDAP dn of the primary group that the UDM user should be assigned to.
     * May be empty to assign the user to the default primary group configured in UDM.
     */
    public String udmUserPrimaryGroupDn; // defaultGroupDn

    public String udmBaseUrl;
    public String udmUsername;
    public String udmPassword;

    public Config(AuthenticatorConfigModel configModel) {
        this.sourceIdentityProviderID_KeycloakAndUDMKey = sourceIdentityProviderID_KeycloakAndUDMKey;
        this.sourceUserPrimaryID_KeycloakKey = sourceUserPrimaryID_KeycloakKey;
        this.sourceUserPrimaryID_UDMKey = sourceUserPrimaryID_UDMKey;
        this.udmUserPrimaryGroupDn = udmUserPrimaryGroupDn;
        this.udmBaseUrl = udmBaseUrl;
        this.udmUsername = udmUsername;
        this.udmPassword = udmPassword;
    }
}