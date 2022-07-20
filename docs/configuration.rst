.. _app-configuration:

*************
Configuration
*************

The :program:`Keycloak` app offers various configuration options. Some settings
don't allow changes after installation. Therefore, you must set them carefully
**before** installation. You find those settings marked with *Only before
installation* in :ref:`app-settings`. You can change all other settings at any
time after the installation.

To change settings after installation, sign in to the UCS management system with
a username with administration rights and go to :menuselection:`App Center -->
Keycloak --> Manage Installation --> App Settings`. On the appearing *Configure
Keycloak* page, you can change the settings and apply them to the app with a
click on :guilabel:`Apply Changes`.

The App Center then *reinitializes* the Docker container for the Keycloak app.
*Reinitilize* means the App Center throws away the running Keycloak Docker
container and creates a fresh Keycloak Docker container with the just changed
settings.

.. _login-portal:

Use Keycloak for login to UCS Portal
====================================

The :program:`Keycloak` app can take over the role of the :term:`SAML IDP` for the
UCS Portal. And the portal can use Keycloak for user authentication.

.. warning::

   The LDAP server will not recognize SAML tickets that the *simpleSAMLphp*
   based identity provider issued after you restart it. Users will experience
   invalidation of their existing sessions.

   For more information about production use, see
   :ref:`limitation-primary-node`.


To configure the UCS portal to use Keycloak for authentication, run the
following steps on the system where you installed Keycloak:

#. Set the UCR variable :envvar:`umc/saml/idp-server` to the URL
   :samp:`https://ucs-sso-ng.{$domainname}/realms/ucs/protocol/saml/descriptor`,
   for example
   ``https://ucs-sso-ng.example.org/realms/ucs/protocol/saml/descriptor``. This
   step tells the portal to use Keycloak as IDP.

   .. tab:: UMC

      Sign in to the UCS management system and then go to :menuselection:`System
      --> Univention Configuration Registry` and search for the variable
      :envvar:`umc/saml/idp-server` and set the value as described before.

   .. tab:: Console

      Open a shell on the UCS system as superuser ``root`` where you installed
      Keycloak and run the following command:

      .. code-block:: console

         $ ucr set \
         > umc/saml/idp-server=\
         > "https://ucs-sso-ng.$(hostname -d)/realms/ucs/protocol/saml/descriptor"

#. Modify the portal to use SAML for login:

   .. tab:: UMC

      In the UCS management system go to :menuselection:`Domain --> Portal -->
      login-saml`. On the tab *General* in the section *Advanced* activate the
      :guilabel:`Activated` checkbox.

   .. tab:: Console

      Open a shell on the UCS system as superuser ``root`` where you installed
      Keycloak and run the following command:

      .. code-block:: console

         $ udm portals/entry modify \
         > --dn "cn=login-saml,cn=entry,cn=portals,cn=univention,$(ucr get ldap/base)" \
         > --set activated=TRUE

#. To activate the changes, restart the LDAP server ``slapd`` within a maintenance
   window.

   .. tab:: UMC

      In the UCS management system go to :menuselection:`System --> System
      Services`. Search for ``slapd`` and click to select the service. Then
      click :guilabel:`Restart`.

   .. tab:: Console

      Open a shell on the UCS system as superuser ``root`` where you installed
      Keycloak and run the following command:

      .. code-block:: console

         $ service slapd restart

.. note::

   If you don't restart the LDAP server, you will see the following message in
   :file:`/var/log/syslog`:

   :samp:`slapd[…]: SASL [conn=…] Failure: SAML assertion issuer
   https://ucs-sso-ng.{$domainname}/realms/ucs is unknown`

.. _oidc-op:

Keycloak as OpenID Connect provider
===================================

The :program:`Keycloak` app can serve as an OpenID Connect provider
(:term:`OIDC Provider`). The following steps explain how to configure an |OIDC|
relying party (:term:`OIDC RP`) to use Keycloak for authentication:

#. :ref:`keycloak-admin-console`.

#. Navigate to :menuselection:`UCS realm --> Clients --> Create`.

#. Specify the ``client-id`` for the client application (:term:`OIDC RP`). Use
   the same ``client-id`` in the configuration of the client application.

#. Select ``openid-connect`` in the *Client Protocol* drop-down list.

#. Enter the *root URL*, the endpoint URL of the client application (:term:`OIDC
   RP`).

#. Click :guilabel:`Save`.

#. Finally, the administrator can review the URL settings and customize them, if
   necessary.

For more information, see :cite:t:`keycloak-clients`.

.. versionadded:: 19.0.1-ucs1

   :program:`univention-keycloak` added.
   For more information about the usage, see the ``--help`` option.

As an alternative the app :program:`Keycloak` offers a command line tool. For
usage, see the following example:

.. code-block:: console

   $ univention-keycloak oidc/op/cert get \
     --as-pem \
     --output "$SOMEFILENAME"
   $ univention-keycloak oidc/rp create "$CLIENT-ID" \
     --app-url "https://$(hostname -f)/$MYAPPURL/"

The option group ``oidc/rp`` offers additional options like ``--client-secret``.

.. note::

   If the administrator chooses ``Confidential`` as *Access Type* on the client
   configuration page, Keycloak offers an additional *Credentials* tab with the
   credentials.

.. _2fa-authentication:

.. _saml-idp:

Keycloak as SAML Identity Provider
==================================

.. versionadded:: 19.0.1-ucs1

   :program:`univention-keycloak` added.
   For more information about the usage, see the ``--help`` option.

The :program:`Keycloak` app can serve as an :term:`SAML IDP`.

For apps that want to act as a :term:`SAML SP`, you need to add a ``client``
configuration in Keycloak through the :ref:`Keycloak Admin Console
<keycloak-admin-console>`. For more information about how to create a SAML
client configuration, see :cite:t:`keycloak-saml-client`.

As an alternative the app :program:`Keycloak` offers a command line tool. For
usage, see the following example:

.. code-block:: console

   $ univention-keycloak saml/idp/cert get \
     --as-pem --output "$SOMEFILENAME"
   $ univention-keycloak saml/sp create "$APPNAME" \
     --metadata-url "https://$(hostname -f)/$METADATA-URL-OF-THE-APP"

The option group ``saml/sp`` offers additional options like
``--client-signature-required``.

.. note::

   If the administrator chooses ``Confidential`` as *Access Type* on the client
   configuration page, Keycloak offers an additional *Credentials* tab with the
   credentials.

Two-factor authentication for Keycloak
======================================

.. versionadded:: 19.0.1-ucs1

   * Added support for |2FA| to :program:`univention-keycloak`.
     For more information about the usage, see the ``--help`` option.

The app :program:`Keycloak` offers a |2FA| option. |2FA| is an authentication
method that grants users access to a service after they sign in with a password
and a |OTP| randomly generated by a third-party |OTP| password generator like
*FreeOTP* or *Google Authenticator*.

|2FA| increases the protection for user data, because users need to provide two
pieces: knowledge (password) and something in the users' possession (the |OTP|).
It also increase the security of the system by avoiding account locking on known
accounts because of malicious attacks. For more information, see `Wikipedia:
Multi-factor authentication <w-2fa_>`_.

After you activate |2FA| for a group of users, Keycloak asks those users for
their |OTP| on each login. To simplify the configuration process, you can use a
command-line tool to enable |2FA|.

To activate or deactivate |2FA| for a user group, follow the instructions in the
next sections.

.. _2fa-enable-groups:

Activate two-factor authentication for domain administrators
------------------------------------------------------------

#. Open a shell on the UCS system as superuser ``root`` where you installed
   Keycloak and run the following command:

   .. code-block:: console

      $ univention-keycloak 2fa enable --group-2fa "Domain Admins"

#. The next time a user belonging to the ``Domain Admins`` group tries to sign
   in, Keycloak forces them to configure the |2FA| following the instructions given
   during the login.

.. _2fa-disable-groups:

Deactivate two-factor authentication for domain administrators
--------------------------------------------------------------

#. :ref:`keycloak-admin-console`.

#. Navigate to :menuselection:`UCS realm --> Groups`.

#. Select ``Domain Admins`` in the list and click :guilabel:`Edit`.

#. Navigate to *Role Mappings* on the tabs.

#. Remove ``2FA role`` from *Assigned roles*.


.. _ad-hoc-federation:

Keycloak ad hoc federation
==========================
.. versionadded:: 19.0.1-ucs2
   
:program:`Keycloak` |SPI| extension for ad-hoc federation added.
Keycloak offers identity brokering to delegate authentication to one or more
identity providers for OpenID Connect or SAML 2.0.

.. seealso::

   For more information about identity brokering and first login flow, see
   :cite:t:`keycloak-first-login`.

The app :program:`Keycloak` provides *ad hoc federation* to enable identity
brokering and add user accounts to |UCS| as so-called *shadow accounts*. It
supports the :ref:`design decision about not having user accounts in Keycloak
<app-design-decisions>`.

The app :program:`Keycloak` installs the :program:`univention-authenticator`
|SPI| plugin. The plugin creates the local shadow copy of the user account in
the OpenLDAP directory services through the REST API of |UDM|. *Ad hoc
federation* is useful when administrators want to keep track of all users in
|UCS|.

.. seealso::

   For more information on |SPI|, see :cite:t:`keycloak-spi`.

.. _ad-hoc-federation-custom-auth-flow:

Create custom authentication flow
---------------------------------

First, you as administrator need to create a custom authentication flow to use
*univention-authenticator* |SPI|:

#. :ref:`keycloak-admin-console`.

#. Navigate to :menuselection:`UCS realm --> Authentication`.

#. Select ``First Broker Login`` in the list and click :guilabel:`Copy`.

#. Give a name to the authentication flow and click :guilabel:`OK`.

#. In the *Review Profile (review profile config)* click :guilabel:`Actions` and
   select ``Config``.

#. Select ``Off`` in the list, click :guilabel:`Save` and navigate back to
   the authentication flow.

#. Click :guilabel:`Add execution` to get to the *Create Authenticator Execution* page.

#. Select ``Univention Authenticator`` in the list and click :guilabel:`Save`.

#. On the *Flows* tab in the *Authentication* section, change the *Univention
   Authenticator* in the displayed table to ``Required``.

#. To finish the configuration, click :guilabel:`Actions` in the *Univention
   Authenticator* and select ``Config``.

#. Fill in the following configuration options for the *Univention
   Authenticator*:

   :Alias: Name of the configuration.

   :UDM REST API endpoint: The API endpoint of UDM where UCS stores the shadow copy of the user.

   :Username: Username of a user account that can write to UDM.

   :Password: Password of the user account that can write to UDM.

#. Click :guilabel:`Save`.

.. _ad-hoc-federation-create-IdP:

Create an identity provider for Microsoft Active Directory
----------------------------------------------------------

After you created the :ref:`custom authentication flow
<ad-hoc-federation-custom-auth-flow>`, Keycloak can use ad hoc federation on any
configured federated login. In this section, you learn how to set up a federated
login using a `Microsoft Active Directory Federation Services <ms-adfs_>`_.

To create an identity provider for Active Directory that uses the ad hoc
federation follow the next steps:

#. :ref:`keycloak-admin-console`.

#. Navigate to :menuselection:`UCS realm --> Identity Providers`.

#. Click :guilabel:`Add provider...` and select ``SAML v2.0``.

#. Fill in the fields *Alias* and *Display Name*. You **can't** change the field
   *Alias* later.

#. Select your authentication flow with the *Univention Authenticator* on the
   *First Login Flow*.

#. Fill in the field *Service Provider Entity ID* with the *EntityID* from the
   *Relying Party* on the Active Directory Federation Services.

#. Set the *Single Sign-On Service URL* to the single sign-on URL from the
   *Relying Party*.

#. In *Principal Type* select ``Unspecified`` in the fields *NameID Policy
   Format*, *Attribute [Name]*.

   In *Principal Attribute* select ``sAMAccountName``.

#. Enable the following properties: 

   * ``Allow Create``

   * ``HTTP-POST Binding Response``

   * ``HTTP-POST Binding for AuthnRequest``

   * ``Want AuthnRequests Signed``

#. For the field *Signature Algorithm* select ``RSA_SHA256``

   For the field *SAML Signature Key Name* select ``CERT_SUBJECT``.

#. Enable *Validate Signature* and add the certificate to *Validating x509
   Certificates*.

#. Click :guilabel:`Save`

.. _ad-hoc-federation-mappers:

Mappers for the identity provider
---------------------------------

The identity provider needs the following mapper configuration to work properly
with Univention Corporate Server:

#. To create a mapper in the identity provider configuration navigate to
   :menuselection:`UCS realm --> Identity Provider --> Your Identity Provider
   --> Mappers`.

#. Click :guilabel:`Create`

#. Configure the mapper for the email address with the following properties:

   :Name: Name of the mapper
   :Sync Mode Override: ``import``
   :Type of mapper: ``Attribute Importer``
   :Attribute Name: ``http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress``
   :User Attribute Name: ``email``


#. Configure the mapper for the first name with the following properties:

   :Name: Name of the mapper
   :Sync Mode Override: ``import``
   :Type of mapper: ``Attribute Importer``
   :Attribute Name: ``http://schemas.xmlsoap.org/ws/2005/05/identity/claims/givenname``
   :User Attribute Name: ``firstName``

#. Configure the mapper for the last name with the following properties:

   :Name: Name of the mapper
   :Sync Mode Override: ``import``
   :Type of mapper: ``Attribute Importer``
   :Attribute Name: ``http://schemas.xmlsoap.org/ws/2005/05/identity/claims/surname``
   :User Attribute Name: ``lastName``

#. Configure the mapper for ``univentionObjectIdentifier`` with the following properties:

   :Name: Name of the mapper
   :Sync Mode Override: ``import``
   :Type of mapper: ``Attribute Importer``
   :User attribute: ``objectGuid``
   :User attribute Name: ``univentionObjectIdentifier``

#. Configure the mapper for ``univentionSourceIAM`` with the following properties:

   :Name: Name of the mapper
   :Sync Mode Override: ``import``
   :Type of mapper: ``Hardcoded attribute``
   :User attribute: ``univentionSourceIAM``
   :User attribute value: Identifier of the identity provider.

#. Configure the mapper for ``external-${ALIAS}-${ATTRIBUTE.sAMAccountName}``
   with the following properties:

   :Name: Name of the mapper
   :Sync Mode Override: ``import``
   :Type of mapper: ``Username Template Importer``
   :User attribute: ``external-${ALIAS}-${ATTRIBUTE.sAMAccountName}``
   :Target: ``LOCAL``

.. _ad-hoc-federation-ADFS-configuration:

Configure Active Directory Federation services for ad hoc federation
--------------------------------------------------------------------

To configure the Active Directory Federation Services to properly work with ad
hoc federation you need to configure it with the following steps:

#. Sign in as *Administrator* in Active Directory Federation Services.

#. Open *Relying Party Trust* and click :guilabel:`Add Relying Party Trust`.

#. Select ``Claim aware`` and click :guilabel:`Start`.

#. On the *Select Data Source* page, select ``Import data about the relying
   party published online or on a local network``.

#. In the field *Federation metadata address* insert the metadata URL:
   :samp:`https://ucs-sso-ng.$(ucr get domainname)/auth/realms/ucs/broker/{SAML
   IDP name}/endpoint/descriptor`.

#. Specify a *Display Name*. Click :guilabel:`Next`.

#. Select your wanted *Access Control Policy*. Click :guilabel:`Next`.

#. Review your final configuration and click :guilabel:`Next`.

#. Click :guilabel:`Close`.

#. Add the claims to the ticket.

   ``objectGUID``
      #. Click :guilabel:`Add rule` and select ``Send LDAP Attributes as Claims``.

      #. Add a claim for ``objectGUID`` to the ticket:

         :Claim Rule name: Name of the Claim
         :Attribute Store: ``Active Directory``
         :LDAP attribute: ``objectGUID``
         :Outgoing Claim Type: ``objectGUID``

   ``sAMAccountName``
      #. Click :guilabel:`Add rule` and select ``Send LDAP Attributes as Claims``.

      #. Add a claim for ``sAMAccountName`` to the ticket:

         :Claim Rule name: Name of the Claim
         :Attribute Store: ``Active Directory``
         :LDAP attribute: ``SAM-Account-Name``
         :Outgoing Claim Type: ``sAMAccountName``

   Email address
      #. Click :guilabel:`Add rule` and select ``Send LDAP Attributes as Claims``.

      #. Add a claim for the email address to the ticket:

         :Claim Rule name: Name of the Claim
         :Attribute Store: ``Active Directory``
         :LDAP attribute: ``E-mail Addresses``
         :Outgoing Claim Type: ``E-mail Address``

   Given name
      #. Click :guilabel:`Add rule` and select ``Send LDAP Attributes as Claims``.

      #. Add a claim for the given name to the ticket:

         :Claim Rule name: Name of the Claim
         :Attribute Store: ``Active Directory``
         :LDAP attribute: ``Given-Name``
         :Outgoing Claim Type: ``Given Name``

   Surname
      #. Click :guilabel:`Add rule` and select ``Send LDAP Attributes as Claims``.

      #. Add a claim for the surname to the ticket:

         :Claim Rule name: Name of the Claim
         :Attribute Store: ``Active Directory``
         :LDAP attribute: ``Surname``
         :Outgoing Claim Type: ``Surname``

#. Apply and save the rules.

.. _app-settings:

Settings
========

The following references show the available settings within the
:program:`Keycloak` app. Univention recommends to keep the default values.

Keycloak has a lot more possibilities for configuration and customization. For
more information, consult :cite:t:`keycloak-docs`.


.. envvar:: keycloak/admin/user

   Defines the name of the first user with administration rights in Keycloak.
   The file :file:`/etc/keycloak.secret` stores this user's password on the
   system you installed the app.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - Yes
        - ``admin``
        - Only before installation


.. envvar:: keycloak/log/level

   Configures the verbosity of log messages in Keycloak.

   Possible values
      ``ALL``, ``DEBUG``, ``ERROR``, ``FATAL``, ``INFO``, ``OFF``, ``TRACE``,
      ``WARN``.

   For a detailed description of the log level values, see
   :cite:t:`keycloak-docs-root-logging`.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - Yes
        - ``INFO``
        - Installation and app configuration


.. envvar:: keycloak/java/opts

   Defines the options that the Keycloak app appends to the *java* command.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - Yes
        - ``-server -Xms1024m -Xmx1024m``
        - Installation and app configuration


.. envvar:: keycloak/theme

   Defines the theme that Keycloak uses for the login interface. A CSS file with
   the same name must exist in the directory
   :file:`/usr/share/univention-web/themes/`. The setting value only uses the
   basename of the file without the extension ``css``.

   Possible values
      ``dark`` and ``light``

      If you provide custom CSS files with other names, they add to the possible
      values.

   Possible values
      ``true`` and ``false``.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - Same value as UCR variable :envvar:`uv-manual:ucs/web/theme`.
        - Installation and app configuration


.. envvar:: keycloak/server/sso/fqdn

   Defines the FQDN to the identity provider in your environment's UCS domain.
   Defaults to :samp:`ucs-sso-ng.{$domainname}`.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - None
        - Installation and app configuration


.. envvar:: keycloak/server/sso/autoregistration

   If set to ``true`` (default), the UCS system with the Keycloak app installed
   registers its IP address at the hostname of the identity provider defined in
   :envvar:`keycloak/server/sso/fqdn`.

   Possible values:
      ``true`` or ``false``

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - Yes
        - ``true``
        - Installation and app configuration
