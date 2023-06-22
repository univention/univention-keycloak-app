.. SPDX-FileCopyrightText: 2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _examples:

*********************************************
Exemplary migration of SAML and OIDC services
*********************************************

This section provides detailed examples how to migrate SAML and OIDC services
to :program:`Keycloak`.

Migration of services using SAML for authentication
===================================================

This section gives a general idea about the migration of services that use
:program:`SimpleSAMLPHP` as :term:`SAML Provider` for the authentication to
:program:`Keycloak` as :term:`SAML Provider`.

The general approach for the migration includes the following:

* Installing the latest version of the :program:`Keycloak` app in the UCS
  domain.

* Getting an overview of all the services that use :program:`SimpleSAMLPHP`
  and their settings.

* Check and create attribute mappers for LDAP. This will make selected LDAP
  attributes part of the |SAML| assertion (XML response) and available to
  :program:`Keycloak`.

* Create an :term:`SAML SP` and necessary attribute mappers in
  :program:`Keycloak` for every service that uses :program:`SimpleSAMLPHP` as
  :term:`SAML Provider`.

* Change the |SAML| settings in the services to use :program:`Keycloak` as
  :term:`SAML Provider` and validate the setup.

To setup a service for |SAML| with :program:`Keycloak` use the following steps:

1. Create appropriate LDAP attribute mapper(s) depending on your needs. To get
   full list of LDAP attributes used, run:

   .. code-block:: console
      :caption: List IDP LDAP attributes
      :name: list-idp-ldap-attributes

      $ udm saml/idpconfig list | grep LdapGetAttributes | awk -F'[: ]' '{print $5;}'

   Then for each attribute you need, do call:

   .. code-block:: console
      :caption: Create LDAP attribute mapping
      :name: create-ldap-attribute-mapping

      $ univention-keycloak user-attribute-ldap-mapper create $LDAP_ATTRIBUTE_NAME

#. Create |SAML| client in :program:`Keycloak` using one of the following
   methods:

   1. create |SAML| client from saml/serviceprovider object
      (map attributes to univention-keycloak options)

   2. use metadata either from known URL or :samp:`serviceProviderMetadata`
      obtained using `udm saml/serviceprovider list`.

   3. In case of custom settings are used, do check
      /etc/simplesamlphp/metadata.d/*.php files.

#. You can also use the :ref:`Keycloak Admin Console <keycloak-admin-console>`
   to create :term:`SAML RP` clients manually or to adjust clients created
   with :samp:`univention-keycloak saml/rp create`. See also
   :ref:`uv-keycloak-app:saml-op` for more information on how to manage
   :term:`SAML RP` clients with :program:`Keycloak`.

#. change the |SAML| settings in the services to use :program:`Keycloak` as
  :term:`SAML Provider` and validate the setup.

   .. code-block:: console
      :caption: Get base URL of the *Keycloak* server

      $ sso_url="$(univention-keycloak get-keycloak-base-url)"

#. It is necessary to update the IDP public certificate in your |SAML|
   settings. To obtain :program:`Keycloak` server certificate:

   .. code-block:: console
      :caption: Retrieve public certificate and *Keycloak* base URL
      :name: migration-of-services-using-SAML-keycloak-certificate

      $ univention-keycloak saml/idp/cert get \
         --as-pem \
         --output "/tmp/keycloak.cert"

#. SAML 2.0 Identity Provider Metadata for keycloak is:
   https://${sso_url}/realms/ucs/protocol/saml/descriptor

To get a better picture using |SAML| with :program:`Keycloak`, have a look at
the examples in the next sections.

.. _portal-migration:

UCS Portal
----------

The UCS Portal can be configured to use |SAML| for authentication.
Please see :ref:`uv-keycloak-app:login-portal` for detailed
description how to setup the UCS Portal to use :program:`Keycloak`
as :term:`SAML IDP`.

.. _migration-nextcloud:

Nextcloud
---------

This section is about migrating the :program:`Nextcloud` app to use
:program:`Keycloak` as :term:`SAML IDP` for the |SAML| authentication. It
assumes that your environment meets the following requirements:

* The configuration of the app :program:`Nextcloud` is complete and done.

* The |SAML| sign-in for :program:`Nextcloud` works with
  :program:`SimpleSAMLPHP` as :term:`SAML IDP`.

* The UCS domain has the latest version of the app :program:`Keycloak`
  installed.

To setup :program:`Nextcloud` for |SAML| with :program:`Keycloak` use the
following steps:

#. To create a :term:`SAML SP` for :program:`Nextcloud`, run the following
   command on the :program:`Keycloak`. Replace :samp:`$NEXTCLOUD_FQDN` with
   the |FQDN| of the UCS system that has :program:`Nextcloud` installed.

   .. code-block:: console
      :caption: Create SAML SP for *Nextcloud* in *Keycloak*
      :name: migration-nextcloud-create-saml-client

      $ export nc_fqdn="REPLACE with NEXTCLOUD_FQDN"
      $ univention-keycloak saml/sp create \
        --metadata-url="https://$nc_fqdn/nextcloud/apps/user_saml/saml/metadata" \
        --role-mapping-single-value

#. To obtain the :program:`Keycloak` base URL and certificate, run
   the following commands on the UCS system that has :program:`Keycloak`
   installed:

   .. code-block:: console
      :caption: Retrieve public certificate and *Keycloak* base URL
      :name: migration-nextcloud-get-idp-settings

      $ univention-keycloak saml/idp/cert get \
         --as-pem \
         --output "/tmp/keycloak.cert"

      $ univention-keycloak get-keycloak-base-url

   The output of the first command in
   :numref:`migration-nextcloud-get-idp-settings` saves the certificate in the
   file :file:`/tmp/keycloak.crt`. Copy this file to the UCS system that has the
   :program:`Nextcloud` app installed.

   The second command in :numref:`migration-nextcloud-get-idp-settings` outputs
   the base URL of your :program:`Keycloak` server. Replace :samp:`{SSO_URL}`
   in the following instruction with this value.

#. To change the |IDP| settings for :program:`Nextcloud`, run the following
   command on the UCS system that has it installed. Copy the certificate file
   :file:`/tmp/keycloak.cert` from the previous step to the program:`Nextcloud`
   server and replace :samp:`SSO_URL`.

   .. code-block:: console
      :caption: Configure the :program:`Nextcloud` app to use :program:`Keycloak` as |IDP|
      :name: migration-nextcloud-saml-settings

      $ export sso="REPLACE WITH SSO_URL"
      $ univention-app shell nextcloud sudo -u www-data /var/www/html/occ saml:config:set \
        --idp-x509cert="$(cat /tmp/keycloak.cert)" \
        --general-uid_mapping="uid" \
        --idp-singleLogoutService.url="$sso/realms/ucs/protocol/saml" \
        --idp-singleSignOnService.url="$sso/realms/ucs/protocol/saml" \
        --idp-entityId="$sso/realms/ucs" 1

To validate the setup, visit the sign-in page of your :program:`Nextcloud`
app and initiate a single sign-on. :program:`Nextcloud` redirects you to
:program:`Keycloak` for authentication. You can use :program:`Nextcloud` after
authentication.

.. seealso::

   `Nextcloud <https://www.univention.com/products/univention-app-center/app-catalog/nextcloud/>`_
      in Univention App Catalog

.. _migration-google-connector:

Google Workspace Connector
--------------------------

This section provides a step-by-step guide for migrating the :program:`Google
Connector` app to use :program:`Keycloak` as :term:`SAML IDP`. The migration
assumes that your environment meets the following requirements:

* The configuration of the app :program:`Google Workspace Connector` is complete
  and done.

* The |SAML| login for your *Google Workspace* works with
  :program:`SimpleSAMLPHP`.

* You have the administrator credentials for your *Google Workspace Admin Console* for
  the |SAML| service configuration.

* The UCS domain has the latest version of the app :program:`Keycloak`
  installed.

To setup :program:`Google Workspace Connector` for |SAML| with
:program:`Keycloak` use the following steps:

#. Create a :term:`SAML SP` for :program:`Google Workspace Connector` in
   :program:`Keycloak`. Run the following commands on the UCS system that has
   :program:`Keycloak` installed:

   .. code-block:: console
      :caption: Create SAML SP for *Google Workspace Connector* in *Keycloak*
      :name: migration-google-connector-create-saml-sp

      $ google_domain="REPLACE_WITH_NAME_OF_YOUR_GOOGLE_DOMAIN"
      $ univention-keycloak saml/sp create \
          --client-id google.com \
          --assertion-consumer-url-post "https://www.google.com/a/$google_domain/acs" \
          --single-logout-service-url-post "https://www.google.com/a/$google_domain/acs" \
          --idp-initiated-sso-url-name google.com \
          --name-id-format email \
          --frontchannel-logout-off
      $ univention-keycloak user-attribute-ldap-mapper \
        create univentionGoogleAppsPrimaryEmail
      $ univention-keycloak saml-client-nameid-mapper create \
        google.com \
        univentionGoogleAppsPrimaryEmail \
        --user-attribute univentionGoogleAppsPrimaryEmail \
        --mapper-nameid-format urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress

#. For the configuration of the |SAML| settings of your *Google Workspace* you
   need the public certificate and the base URL of the :program:`Keycloak`
   server. Run the following commands on the UCS system that has
   :program:`Keycloak` installed:

   .. code-block:: console
      :caption: Retrieve public certificate and *Keycloak* base URL
      :name: migration-google-connector-keycloak-certificate

      $ univention-keycloak saml/idp/cert get --as-pem --output /tmp/kc.cert
      $ univention-keycloak get-keycloak-base-url

   The output of the first command in
   :numref:`migration-google-connector-keycloak-certificate` is the certificate.
   Copy the file :file:`/tmp/kc.cert` to your local client computer.

   The second command in
   :numref:`migration-google-connector-keycloak-certificate` outputs the base
   URL of your :program:`Keycloak` server. Replace :samp:`{SSO_URL}` in the
   following instruction with this value.

#. Change the *Third-party SSO profile for your organisation* settings in the
   *Google Workspace Admin console* of your google domain.

   #. Open the URL https://admin.google.com and login with your administrator
      account.

   #. Go to :menuselection:`Security --> Authentication --> SSO with third-party IdP`.

   #. Open *Third-party SSO profile for your organisation*.

   #. Change *Sign-in page URL* to ``SSO_URL/realms/ucs/protocol/saml``.

   #. Change *Sign-out page URL* to ``SSO_URL/realms/ucs/protocol/openid-connect/logout``.

   #. To upload the :program:`Keycloak` certificate from :file:`/tmp/kc.cert`
      click :guilabel:`REPLACE CERTIFICATE`.

   #. *Save* your settings.

#. Change the link in the UCS portal entry *Google Workspace login* for the
   |IDP| initiated single sign-on. On your UCS *Primary Directory Node* run the
   following command:

   .. code-block:: console
      :caption: Change portal entry for *Google Workspace login* to |IDP| initiated single sign-on
      :name: migration-google-connector-portal-entry

      $ google_domain="REPLACE WITH NAME_OF_YOUR_GOOGLE_DOMAIN"
      $ sso_url="REPLACE WITH SSO_URL"
      $ udm portals/entry modify \
        --dn "cn=SP,cn=entry,cn=portals,cn=univention,$(ucr get ldap/base)" \
        --set link='"en_US" "'$sso_url'/realms/ucs/protocol/saml/clients/google.com?RelayState=https://www.google.com/a/'$google_domain'/ServiceLogin"'

To validate the setup, visit https://google.com and sign in with one of the
UCS user accounts enabled for *Google Workspace*. Also, verify the UCS portal
entry *Google Workspace login* for the |IDP| initiated single sign-on.

.. warning::

   The automatic redirect after the single sign-out doesn't work with
   :program:`Keycloak`.

.. seealso::

   `Google Workspace Connector <https://www.univention.com/products/univention-app-center/app-catalog/google-apps/>`_
      in Univention App Catalog

.. _migration-365-connector:

Microsoft 365 Connector
-----------------------

The example illustrates how to migrate the app :program:`Microsoft 365
Connector` to use :program:`Keycloak` as :term:`SAML IDP`. It assumes
that your environment meets the following requirements:

* The configuration of the app :program:`Microsoft 365 Connector` is complete
  and done.

* The |SAML| login for your *Azure Active Directory* domain works with
  :program:`SimpleSAMLPHP`.

* You have a client computer with *Microsoft Windows* installed on it and a
  working internet connection on the client computer to configure |SAML| in
  *Azure Active Directory*.

* You have the *Administrator* credentials for your *Azure Active Directory*
  domain for the |SAML| service configuration.

* The UCS domain has the latest version of the app :program:`Keycloak`
  installed.

To setup :program:`Microsoft 365 Connector` for |SAML| with :program:`Keycloak`
use the following steps:

#. Create a :term:`SAML SP` for :program:`Microsoft 365 Connector` in
   :program:`Keycloak`. Run the following commands on the UCS system that has
   :program:`Keycloak` installed:

   .. code-block:: console
      :caption: Create SAML SP for *Microsoft 365 Connector* in *Keycloak*
      :name: migration-365-connector-create-saml-sp

      # get the saml client metadata xml from microsoft
      $ curl https://nexus.microsoftonline-p.com/federationmetadata/saml20/federationmetadata.xml > /tmp/ms.xml

      # create the client in keycloak
      $ univention-keycloak saml/sp create \
        --metadata-file /tmp/ms.xml \
        --metadata-url urn:federation:MicrosoftOnline \
        --idp-initiated-sso-url-name MicrosoftOnline \
        --name-id-format persistent

      # create a SAML nameid mapper
      $ univention-keycloak saml-client-nameid-mapper create \
        urn:federation:MicrosoftOnline \
        entryUUID \
        --mapper-nameid-format "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent" \
        --user-attribute entryUUID \
        --base64

#. For the configuration of the |SAML| settings of your *Azure Active Directory*
   domain you need the public certificate and the base URL of the
   :program:`Keycloak` server. Run the following commands on the UCS system that
   has :program:`Keycloak` installed:

   .. code-block:: console
      :caption: Retrieve public certificate and *Keycloak* base URL
      :name: migration-365-connector-keycloak-certificate

      $ univention-keycloak saml/idp/cert get --output /tmp/kc.cert
      $ cat /tmp/kc.cert

      $ univention-keycloak get-keycloak-base-url

   The output of the first command in
   :numref:`migration-365-connector-keycloak-certificate` is the certificate.
   Replace :samp:`{$KEYCLOAK_CERTIFICATE}` in the following steps
   with this value.

   The second command in :numref:`migration-365-connector-keycloak-certificate`
   outputs the base URL of your :program:`Keycloak` server. Replace
   :samp:`$SSO_URL` in the following steps with this value.

#. To configure the |SAML| settings for the *Azure Active Directory* domain
   copy the following code block to your *Microsoft Windows* client computer.
   Replace the values for :samp:`$SSO_URL`, :samp:`$KEYCLOAK_CERTIFICATE`,
   the *Azure Active Directory* domain name and credentials and run the
   script in the *Microsoft Windows* :program:`PowerShell`.

   .. code-block:: powershell
      :caption: Change *Azure Active Directory* domain authentication to use *Keycloak*
      :name: migration-365-connector-windows-change

      # CHANGE this according to your setup/environemt
      $sso_fqdn = "$SSO_URL"
      $signing_cert = "$KEYCLOAK_CERTIFICATE"
      $domain = "YOUR AZURE DOMAIN NAME"
      $username = "YOUR AZURE DOMAIN ADMIN"
      $password = "PASSWORD OF YOUR AZURE DOMAIN ADMIN"
      # CHANGE end

      $issuer_uri = "$sso_fqdn/realms/ucs"
      $logon_uri = "$sso_fqdn/realms/ucs/protocol/saml"
      $passive_logon_uri = "$sso_fqdn/realms/ucs/protocol/saml"
      $logoff_uri = "$sso_fqdn/realms/ucs/protocol/saml"
      $pass = ConvertTo-SecureString -String "$password" -AsPlainText -Force
      $credential = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $username, $pass
      $o365cred = Get-Credential $credential

      Install-Module MSOnline
      Import-Module MSOnline
      Connect-MsolService -Credential $o365cred

      Set-MsolDomainAuthentication -DomainName "$domain" -Authentication Managed
      Set-MsolDomainAuthentication `
          -DomainName "$domain" `
          -FederationBrandName "UCS" `
          -Authentication Federated `
          -ActiveLogOnUri "$logon_uri" `
          -PassiveLogOnUri "$passive_logon_uri" `
          -SigningCertificate "$signing_cert" `
          -IssuerUri "$issuer_uri" `
          -LogOffUri "$logoff_uri" `
          -PreferredAuthenticationProtocol SAMLP

      Get-MsolDomain
      Pause

#. To change the link in the UCS portal entry *Microsoft 365 Login* for the
   |IDP| initiated single sign-on, run the following command on your UCS
   *Primary Directory Node*:

   .. code-block:: console
      :caption: Change portal entry for *Microsoft 365 Login* to |IDP| initiated single sign-on
      :name: migration-365-connector-portal-entry

      sso_url="$SSO_URL"
      udm portals/entry modify \
        --dn "cn=office365,cn=entry,cn=portals,cn=univention,$ldap_base" \
        --set link='"en_US" "'$sso_url'/realms/ucs/protocol/saml/clients/MicrosoftOnline"'

To validate the setup, visit https://www.microsoft365.com/ and sign in with one
of the UCS user accounts enabled for *Microsoft 365*. Also, verify the UCS
portal entry *Microsoft 365 Login* for the |IDP| initiated single sign-on.

.. warning::

   The automatic redirect after the single sign-out doesn't work with
   :program:`Keycloak`.

.. seealso::

   `Microsoft 365 Connector <https://www.univention.com/products/univention-app-center/app-catalog/microsoft365/>`_
      in Univention App Catalog

Migration of services using OIDC for authentication
===================================================

Generic OIDC service ... TODO

.. _migration-owncloud:

ownCloud
--------

This section is about the migration of the :program:`ownCloud` app to use
:program:`Keycloak` as :term:`OIDC Provider` for authentication. It assumes that your
environment meets the following requirements:

* The configuration of the app :program:`ownCloud` is complete and done.

* The |OIDC| sign-in for :program:`ownCloud` works with :program:`Kopano
  Connect` as :term:`OIDC Provider`.

* The UCS domain has the latest version of the app :program:`Keycloak`
  installed.

To setup :program:`ownCloud` for |OIDC| with :program:`Keycloak` use the
following steps:

#. To obtain the necessary information such as ``clientsecret`` and
   ``redirectURI``, run the following command on the UCS *Primary Directory
   Node*. You need the values to create the :term:`OIDC RP` in the next step.

   .. code-block:: console
      :caption: Get current settings for the *ownCloud* OIDC RP
      :name: migration-owncloud-obtain-oidc-information

      $ udm oidc/rpservice list --filter name=owncloud
        DN: cn=owncloud,cn=oidc,cn=univention,dc=...
          applicationtype: web
          clientid: owncloud
          clientsecret: -> copy this value
          insecure: None
          name: owncloud
          redirectURI: -> copy this value
          trusted: yes

   Look for the values of ``clientsecret`` and ``redirectURI`` and copy them,
   for example into a temporary text file.

#. To create the :term:`OIDC RP` for :program:`ownCloud` in :program:`Keycloak`,
   run the following command on the UCS system that has :program:`Keycloak`
   installed. Replace :samp:`{clientsecret}` and :samp:`{redirectURI}` with the
   values for these settings from the previous step.

   .. code-block:: console
      :caption: Create OIDC RP for *ownCloud* in *Keycloak*
      :name: migration-owncloud-create-oidc-rp

      $ export CLIENT_SECRET="REPLACE WITH clientsecret"
      $ export REDIRECT_URI="REPLACE WITH redirectURI"
      $ univention-keycloak oidc/rp create \
         --client-secret "$CLIENT_SECRET" \
         --app-url "$REDIRECT_URI"
         owncloud

#. To obtain the base URL of your :program:`Keycloak` server, run the following
   command on the UCS system that has it installed:

   .. code-block:: console
      :caption: Obtain *Keycloak* base URL
      :name: migration-owncloud-keycloak-base-url

      $ univention-keycloak get-keycloak-base-url

   Replace :samp:`{SSO_URL}` in the following step with this value.

#. Change the |IDP| setting in :program:`ownCloud`. Run the following command on
   the UCS system that has :program:`ownCloud` installed:

   .. code-block:: console
      :caption: Change IDP settings in *ownCloud*
      :name: migration-owncloud-idp-settings

      $ export SSO="REPLACE WITH SSO_URL"
      $ univention-app configure owncloud \
        --set OWNCLOUD_OPENID_PROVIDER_URL="$SSO/realms/ucs"

To validate the setup, visit the sign-in page of your :program:`ownCloud`
app and initiate a single sign-on. :program:`ownCloud` redirects you to
:program:`Keycloak` for authentication. You can use :program:`ownCloud` after
authentication.

.. seealso::

   `ownCloud <https://www.univention.com/products/univention-app-center/app-catalog/owncloud/>`_
      in Univention App Catalog
