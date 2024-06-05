.. SPDX-FileCopyrightText: 2023-2024 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _migration-example-saml:

Services using SAML
===================

The following examples demonstrate the migration of services that use |SAML|
for authentication and :program:`SimpleSAMLphp` as |IDP| to :program:`Keycloak`
as |IDP|.

.. _portal-migration:

UCS Portal
----------

You can configure the UCS Portal to use |SAML| for authentication. For a
detailed description of how to configure the UCS Portal to use
:program:`Keycloak` as a :term:`SAML IDP`, refer to
:external+uv-keycloak-app:ref:`login-portal` in the :cite:t:`ucs-keycloak-doc`.

.. _migration-nextcloud:

Nextcloud
---------

This section is about migrating the :program:`Nextcloud` app to use
:program:`Keycloak` as :term:`SAML IDP` for the |SAML| authentication. It
assumes that your environment meets the following requirements:

* The configuration of the app :program:`Nextcloud` is complete and done.

* The |SAML| sign-in for :program:`Nextcloud` works with
  :program:`SimpleSAMLphp` as :term:`SAML IDP`.

* The UCS domain has the latest version of the app :program:`Keycloak`
  installed.

To setup :program:`Nextcloud` for |SAML| with :program:`Keycloak`, use the
following steps:

#. To create a SAML client for :program:`Nextcloud`, run the following commands
   on the UCS system that has the :program:`Keycloak` app installed. Replace
   :samp:`{NEXTCLOUD_FQDN}` with the |FQDN| of the UCS system that has
   :program:`Nextcloud` installed.

   .. code-block:: console
      :caption: Create SAML client for *Nextcloud* in *Keycloak*
      :name: migration-nextcloud-create-saml-client

      $ NEXTCLOUD_FQDN="REPLACE with NEXTCLOUD_FQDN"
      $ univention-keycloak saml/sp create \
        --metadata-url="https://$NEXTCLOUD_FQDN/nextcloud/apps/user_saml/saml/metadata" \
        --role-mapping-single-value

#. To obtain the :program:`Keycloak` base URL and certificate, run
   the following commands on the UCS system that has :program:`Keycloak`
   installed:

   .. code-block:: console
      :caption: Retrieve public certificate of *Keycloak*
      :name: migration-nextcloud-get-keycloak-certificate

      $ univention-keycloak saml/idp/cert get \
         --as-pem \
         --output "/tmp/keycloak.cert"

   The output of the command in
   :numref:`migration-nextcloud-get-keycloak-certificate` saves the certificate
   in the file :file:`/tmp/keycloak.cert`. Copy this file to the UCS system that
   has the :program:`Nextcloud` app installed.

   .. code-block:: console
      :caption: Retrieve public certificate and *Keycloak* base URL
      :name: migration-nextcloud-get-keycloak-base-url

      $ univention-keycloak get-keycloak-base-url

   The command in :numref:`migration-nextcloud-get-keycloak-base-url` outputs
   the base URL of your :program:`Keycloak` server. Replace :samp:`{SSO_URL}` in
   the following instruction with this value.

#. To change the |IDP| settings for :program:`Nextcloud`, run the following
   commands on the UCS system that has it installed. Copy the certificate file
   :file:`/tmp/keycloak.cert` from
   :numref:`migration-nextcloud-get-keycloak-certificate` to the UCS system with
   :program:`Nextcloud` installed and replace :samp:`{SSO_URL}`.

   .. code-block:: console
      :caption: Configure the :program:`Nextcloud` app to use :program:`Keycloak` as |IDP|
      :name: migration-nextcloud-saml-settings

      $ SSO_URL="REPLACE WITH SSO_URL"
      $ univention-app shell nextcloud sudo -u www-data /var/www/html/occ saml:config:set \
        --idp-x509cert="$(cat /tmp/keycloak.cert)" \
        --general-uid_mapping="uid" \
        --idp-singleLogoutService.url="$SSO_URL/realms/ucs/protocol/saml" \
        --idp-singleSignOnService.url="$SSO_URL/realms/ucs/protocol/saml" \
        --idp-entityId="$SSO_URL/realms/ucs" 1

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
  :program:`SimpleSAMLphp`.

* You have the administrator credentials for your *Google Workspace Admin Console* for
  the |SAML| service configuration.

* The UCS domain has the latest version of the app :program:`Keycloak`
  installed.

To setup :program:`Google Workspace Connector` for |SAML| with
:program:`Keycloak` use the following steps:

#. Create a :term:`SAML SP`, the client, for :program:`Google Workspace
   Connector` in :program:`Keycloak`. Run the following commands on the UCS
   system that has :program:`Keycloak` installed:

   .. code-block:: console
      :caption: Create SAML client for *Google Workspace Connector* in *Keycloak*
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

      $ univention-keycloak saml/idp/cert get --as-pem --output /tmp/keycloak.cert

   The output of the command in
   :numref:`migration-google-connector-keycloak-certificate` is the certificate.
   Copy the file :file:`/tmp/keycloak.cert` to your local client computer.

   .. code-block:: console
      :caption: Retrieve public certificate and *Keycloak* base URL
      :name: migration-google-connector-keycloak-base-url

      $ univention-keycloak get-keycloak-base-url

   The command in :numref:`migration-google-connector-keycloak-base-url` outputs
   the base URL of your :program:`Keycloak` server. Replace :samp:`{SSO_URL}` in
   the following instruction with this value.

#. Change the *Third-party SSO profile for your organisation* settings in the
   *Google Workspace Admin console* of your google domain.

   #. Open the URL https://admin.google.com and login with your administrator
      account.

   #. Go to :menuselection:`Security --> Authentication --> SSO with third-party IdP`.

   #. Open *Third-party SSO profile for your organisation*.

   #. Change *Sign-in page URL* to ``SSO_URL/realms/ucs/protocol/saml``.

   #. Change *Sign-out page URL* to ``SSO_URL/realms/ucs/protocol/openid-connect/logout``.

   #. To upload the :program:`Keycloak` certificate in :file:`/tmp/kc.cert`
      from :numref:`migration-google-connector-keycloak-certificate`, click
      :guilabel:`REPLACE CERTIFICATE`.

   #. To activate the settings, click :guilabel:`Save`.

#. Change the link in the UCS portal entry *Google Workspace login* for the
   |IDP| initiated single sign-on. On your UCS *Primary Directory Node* run the
   following commands:

   .. code-block:: console
      :caption: Change portal entry for *Google Workspace login* to |IDP| initiated single sign-on
      :name: migration-google-connector-portal-entry

      $ google_domain="REPLACE WITH NAME_OF_YOUR_GOOGLE_DOMAIN"
      $ SSO_URL="REPLACE WITH SSO_URL"
      $ udm portals/entry modify \
        --dn "cn=SP,cn=entry,cn=portals,cn=univention,$(ucr get ldap/base)" \
        --set link='"en_US" "'"$SSO_URL"'/realms/ucs/protocol/saml/clients/google.com?RelayState=https://www.google.com/a/'"$google_domain"'/ServiceLogin"'

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
  :program:`SimpleSAMLphp`.

* You have a client computer with *Microsoft Windows* installed on it and a
  working internet connection on the client computer to configure |SAML| in
  *Azure Active Directory*.

* You have the *Administrator* credentials for your *Azure Active Directory*
  domain for the |SAML| service configuration.

* The UCS domain has the latest version of the app :program:`Keycloak`
  installed.

To setup :program:`Microsoft 365 Connector` for |SAML| with :program:`Keycloak`,
use the following steps:

#. Create a :term:`SAML SP`, the client, for :program:`Microsoft 365 Connector`
   in :program:`Keycloak`. Run the following commands on the UCS system that has
   :program:`Keycloak` installed:

   .. code-block:: console
      :caption: Create SAML client for *Microsoft 365 Connector* in *Keycloak*
      :name: migration-365-connector-create-saml-sp

      # get the saml client metadata xml from microsoft
      $ curl https://nexus.microsoftonline-p.com/federationmetadata/saml20/federationmetadata.xml > /tmp/ms.xml

      # create the client in keycloak
      $ univention-keycloak saml/sp create \
        --metadata-file /tmp/ms.xml \
        --metadata-url urn:federation:MicrosoftOnline \
        --idp-initiated-sso-url-name MicrosoftOnline \
        --single-logout-service-url-redirect https://login.microsoftonline.com/login.srf
        --name-id-format persistent

      # create a SAML nameid mapper
      $ univention-keycloak saml-client-nameid-mapper create \
        urn:federation:MicrosoftOnline \
        entryUUID \
        --mapper-nameid-format "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent" \
        --user-attribute entryUUID \
        --base64

      # allow Keycloak being included from Microsoft 365 (for logout)
      # if already set to a value, this needs to be adapted accordingly
      ucr set keycloak/csp/frame-ancestors=https://login.microsoftonline.com
      service apache2 reload

#. For the configuration of the |SAML| settings of your *Azure Active Directory*
   domain you need the public certificate and the base URL of the
   :program:`Keycloak` server. Run the following commands on the UCS system that
   has :program:`Keycloak` installed:

   .. code-block:: console
      :caption: Retrieve public certificate and *Keycloak* base URL
      :name: migration-365-connector-keycloak-certificate

      $ univention-keycloak saml/idp/cert get --output /tmp/keycloak.cert
      $ cat /tmp/keycloak.cert

   The output of the command in
   :numref:`migration-365-connector-keycloak-certificate` is the certificate.
   Replace :samp:`{KEYCLOAK_CERTIFICATE}` in the following steps with this
   value.

   .. code-block:: console
      :caption: Retrieve public certificate and *Keycloak* base URL
      :name: migration-365-connector-keycloak-base-url

      $ univention-keycloak get-keycloak-base-url

   The command in :numref:`migration-365-connector-keycloak-base-url` outputs
   the base URL of your :program:`Keycloak` server. Replace :samp:`{SSO_URL}` in
   the following steps with this value.

#. To configure the |SAML| settings for the *Azure Active Directory* domain,
   copy the following code block to your *Microsoft Windows* client computer.
   Replace the values for :samp:`{SSO_URL}`, :samp:`{KEYCLOAK_CERTIFICATE}`,
   the *Azure Active Directory* domain name and credentials and run the
   script in the *Microsoft Windows* :program:`PowerShell`.

   .. code-block:: powershell
      :caption: Change *Azure Active Directory* domain authentication to use *Keycloak*
      :name: migration-365-connector-windows-change

      # CHANGE this according to your setup/environemt
      $sso_url = "replace with SSO_URL"
      $signing_cert = "replace with KEYCLOAK_CERTIFICATE"
      $domain = "YOUR AZURE DOMAIN NAME"
      $username = "YOUR AZURE DOMAIN ADMIN"
      $password = "PASSWORD OF YOUR AZURE DOMAIN ADMIN"
      $realm = "REALM OF CHOICE (usually ucs)"
      # CHANGE end

      $issuer_uri = "$sso_url/realms/$realm"
      $logon_uri = "$sso_url/realms/$realm/protocol/saml"
      $passive_logon_uri = "$sso_url/realms/$realm/protocol/saml"
      $logoff_uri = "$sso_url/realms/$realm/protocol/saml"
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
   |IDP| initiated single sign-on, run the following commands on your UCS
   *Primary Directory Node*:

   .. code-block:: console
      :caption: Change portal entry for *Microsoft 365 Login* to |IDP| initiated single sign-on
      :name: migration-365-connector-portal-entry

      $ SSO_URL="REPLACE WITH SSO_URL"
      $ realm="ucs"
      $ udm portals/entry modify \
        --dn "cn=office365,cn=entry,cn=portals,cn=univention,$(ucr get ldap/base)" \
        --set link='"en_US" "'"$SSO_URL"'/realms/"'"$realm"'"/protocol/saml/clients/MicrosoftOnline"'

To validate the setup, visit https://www.microsoft365.com/ and sign in with one
of the UCS user accounts enabled for *Microsoft 365*. Also, verify the UCS
portal entry *Microsoft 365 Login* for the |IDP| initiated single sign-on.

.. warning::

   The automatic redirect after the single sign-out doesn't work with
   :program:`Keycloak`.

.. _migration-365-connector-multiple-connection:

Migration of additional Azure AD connections
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

It is possible to configure additional Azure AD connections for single-sign on using the Microsoft 365 Connector wizard.
If multiple AD connections were configured according to :ref:`uv-manual:domain-saml-extended-configuration`, each connection
needs to be migrated individually to :program:`Keycloak`.
Since Azure AD explicitly needs different entity IDs for each connection, this entails the creation of a new |IDP| and therefore a new realm for each connection.

To create a new logical |IDP| in :program:`Keycloak`, run the following commands on your UCS :program:`Keycloak` host.

.. code-block:: console
   :caption: Create a new logical |IDP| in Keycloak
   :name: create-proxy-realm

   $ AD_CONNECTION="REPLACE WITH MICROSOFT 365 AD CONNECTION NAME"
   $ univention-keycloak proxy-realms create "$AD_CONNECTION"


Use the following call to get the certificate of your newly created |IDP|

.. code-block:: console
   :caption: Get the certificate of the newly created Realm
   :name: get-certificate-of-realm

   $ univention-keycloak saml/idp/cert get \
      --realm-id="$AD_CONNECTION" \
      --output "/tmp/keycloak-"$AD_CONNECTION".cert"
    cat /tmp/keycloak-"$AD_CONNECTION".cert

Using this certificate as :samp:`$signing_cert` and the :samp:`AD_CONNECTION` as :samp:`$realm`, follow the steps from
:ref:`migration-365-connector-windows-change` onward to update the |SAML| settings for the *Azure Active Directory* domain.

Repeat these steps for each additional configured Azure AD connection.

.. seealso::

   `Microsoft 365 Connector <https://www.univention.com/products/univention-app-center/app-catalog/microsoft365/>`_
      in Univention App Catalog

