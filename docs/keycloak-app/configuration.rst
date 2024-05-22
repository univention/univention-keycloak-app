.. SPDX-FileCopyrightText: 2022-2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

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
         umc/saml/idp-server=\
         "https://ucs-sso-ng.$(hostname -d)/realms/ucs/protocol/saml/descriptor"

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
         --dn "cn=login-saml,cn=entry,cn=portals,cn=univention,$(ucr get ldap/base)" \
         --set activated=TRUE

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

By default the :program:`Keycloak` app creates a :term:`SAML SP` (client) for
every UCS Portal server. You can see the list of existing :term:`SAML SP`
clients with the following command:

.. code-block:: console

   $ univention-keycloak saml/sp get --json
   [
       "https://ucs1.example.com/univention/saml/metadata",
       "https://ucs2.example.com/univention/saml/metadata",
       ...
   ]

If the :term:`SAML SP` for a  particular UCS Portal server doesn't exist,
you can create it in :program:`Keycloak` with the command:

.. code-block:: console

   $ FQDN="the fqdn of the UCS Portal server"
   $ univention-keycloak saml/sp create \
     --metadata-url="https://$FQDN/univention/saml/metadata" \
     --umc-uid-mapper


.. _ldap-attribute-mapper:

Import of user attributes from UCS to Keycloak
============================================================

:program:`Keycloak` uses the LDAP directory of the UCS domain as
backend for the user accounts. During the authentication process
certain user attributes are imported into :program:`Keycloak`. These
attributes can be used later on in so called *Attribute Mappers* to pass
additional information trough the |SAML| assertion or |OIDC| token to
services (e.g. *displayName*).

By default the :program:`Keycloak` app is configured to import the following
user attributes:

.. list-table::
   :header-rows: 1
   :widths: 5 5

   * - LDAP attribute
     - Keycloak attribute

   * - ``uid``
     - ``username``

   * - ``uid``
     - ``uid``

   * - ``entryUUID``
     - ``entryUUID``

   * - ``lastname``
     - ``lastName``

   * - ``mailPrimaryAddress``
     - ``email``

   * - ``givenName``
     - ``firstName``

   * - ``createTimestamp``
     - ``createTimestamp``

   * - ``modifyTimestamp``
     - ``modifyTimestamp``

It is possible to configure the import of additional LDAP user attributes to
:program:`Keycloak`, for example

.. code-block:: console

   $ univention-keycloak user-attribute-ldap-mapper create description

to import the LDAP user attribute ``description`` to the :program:`Keycloak`
attribute ``description``.

With the following command you get a list of all the currently configured
:program:`Keycloak` user attributes.

.. code-block:: console

   $ univention-keycloak user-attribute-ldap-mapper get --user-attributes

.. _oidc-op:

Keycloak as OpenID Connect provider
===================================

The :program:`Keycloak` app can serve as an OpenID Connect provider
(:term:`OIDC Provider`). The following steps explain how to configure an |OIDC|
relying party (:term:`OIDC RP`) to use Keycloak for authentication:

#. :ref:`Keycloak Admin Console <keycloak-admin-console>`.

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
   $ univention-keycloak oidc/rp create \
   --app-url="https://$(hostname -f)/${MYAPP_URL}/" "${MYAPP_CLIENT_ID}"

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
   $ univention-keycloak saml/sp create \
   --metadata-url "https://$(hostname -f)/$METADATA-URL-OF-THE-APP"

The option group ``saml/sp`` offers additional options like
``--client-signature-required``.

.. note::

   If the administrator chooses ``Confidential`` as *Access Type* on the client
   configuration page, Keycloak offers an additional *Credentials* tab with the
   credentials.

.. _backup-and-restore:

Backup and restore
==================

Administrators can create a backup of the :program:`Keycloak` app data. The data
comprises information for example about the realm, clients, groups, and roles.
To create a backup, run the *export* action as in the following steps:

.. code-block:: console

   $ univention-app shell keycloak /opt/keycloak/bin/kc.sh export \
   --dir /var/lib/univention-appcenter/apps/keycloak/data/myexport

In this example :file:`myexport` is a freely chosen directory name.

To restore the backup into the app :program:`Keycloak`, run the *import* action
as in the following step:

.. code-block:: console

   $ univention-app shell keycloak /opt/keycloak/bin/kc.sh import \
   --dir /var/lib/univention-appcenter/apps/keycloak/data/myexport

.. warning::

   :program:`Keycloak` defines the scope of exported data and may not contain
   every configuration option the program offers.

.. _cluster-setup:

Multiple installations in the domain
====================================

Administrators can install the app :program:`Keycloak` on several nodes in a UCS
domain to increase availability and provide failover using the default DNS name
``ucs-sso-ng.$(hostname -d)``. The default installations in the domain don't
require any interaction from the administrator. This will also provide session
synchronization between all :program:`Keycloak` installations on the domain.

.. note::
   If the :program:`Keycloak` app is installed on multiple systems in the domain
   and updates are available, make sure to update the app on all systems so that
   all instances of the app in the domain are on the same version.

Two-factor authentication for Keycloak
======================================

.. warning::

   The two-factor capability is a built-in :program:`Keycloak` feature that
   is not integrated into the UCS identity management or user lifecycle.
   More sophisticated integration needs to be added individually.

.. versionadded:: 19.0.1-ucs1

   * Added functionality to enable |2FA| to :program:`univention-keycloak`.
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


..
    .. _ad-hoc-federation:

    Keycloak ad hoc federation
    ==========================

    .. warning::

       The ad hoc federation is a built-in :program:`Keycloak` feature that
       is not integrated into the UCS identity management or user lifecycle.
       More sophisticated integration needs to be added individually.

    .. versionadded:: 19.0.1-ucs2

    :program:`Keycloak` |SPI| extension for ad hoc federation added.
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

    .. _ad-hoc-federation-import-external-ca:

    Import external CA certificates
    -------------------------------

    Federation involves other, for example external, server systems and requires
    trust. Certificates are a way to implement trust. To tell your Keycloak
    system to trust another system for the ad-hoc federation, you need to
    import the CA certificate for that system. Keycloak needs the CA certificate
    to verify the encrypted connection with the other system.

    Use the following steps to add the CA certificate of the other system:

    .. code-block:: console

       $ docker cp /path/to/externalCA.pem keycloak:/externalCA.pem
       $ univention-app shell keycloak \
       keytool -cacerts -import -alias ucsCA -file /externalCA.pem -storepass "changeit" -noprompt

    Repeat this procedure when any CA certificate expires. In case of any CA related
    TLS error, restart the container:

    .. code-block:: console

      $ docker restart keycloak

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


.. envvar:: keycloak/server/sso/fqdn

   Defines the FQDN to the identity provider in your environment's UCS domain.
   Defaults to :samp:`ucs-sso-ng.{$domainname}`.
   Please note that uppercase letters in this setting can lead to problems
   regarding the Keycloak admin console.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - :samp:`ucs-sso-ng.{$domainname}`
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

.. envvar:: keycloak/server/sso/virtualhost

   If set to ``true`` (default) the UCS system will create a dedicated
   apache virtual host configuration for the Keycloak server FQDN.

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

.. envvar:: keycloak/apache/config

   If set to ``true`` (default) the UCS system will create an apache
   configuration for Keycloak.

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

.. envvar:: keycloak/federation/remote/identifier

   This property stores the name of the UDM property that stores
   the unique identifier of the remote IAM objects. It is only
   used for ad hoc federation.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - ``univentionObjectIdentifier``
        - Installation and app configuration


.. envvar:: keycloak/federation/source/identifier

   This property stores the name of the UDM property that stores
   the remote source of an IAM objects. It is only used
   for ad hoc federation.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - ``univentionSourceIAM``
        - Installation and app configuration


.. envvar:: keycloak/database/connection

   This is a setting for the :program:`PostgreSQL` database, the default
   database for Keycloak on the UCS system. The setting specifies the IP
   addresses from which the database can receive connections. The default value
   is ``0.0.0.0``, meaning that every IP address can connect to the database.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - None
        - Installation and app configuration


.. envvar:: kc/db/url

   Specifies the database JDBC URL (for example ``jdbc:postgresql://dbhost/keycloak``)
   to connect Keycloak. Defaults to :samp:`jdbc:postgresql://{fqdn}:5432/keycloak`.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - :samp:`jdbc:postgresql://{fqdn}:5432/keycloak`
        - Installation and app configuration


.. envvar:: kc/db/username

   Specifies the database username. Defaults to ``keycloak``.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - ``keycloak``
        - Installation and app configuration


.. envvar:: kc/db/kind

   Specifies the kind of database. Defaults to ``postgres``. You find the
   available values at :cite:t:`keycloak-db`.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - ``postgres``
        - Installation and app configuration


.. envvar:: kc/db/password

   Specifies the password to connect to the database.

   .. list-table::
     :header-rows: 1
     :widths: 2 5 5

     * - Required
       - Default value
       - Set

     * - No
       - None
       - Installation and app configuration


.. envvar:: ucs/self/registration/check_email_verification

   Controls if the login is denied for unverified, self registered user
   accounts. For more information, see
   :ref:`uv-manual:user-management-password-changes-by-users-selfregistration-account-verification`
   in the :cite:t:`ucs-manual`.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - False
        - Installation and app configuration


.. envvar:: keycloak/login/messages/en/accountNotVerifiedMsg

   English error message for a self-registered user account that isn't verified
   yet. The error message supports HTML format.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - See default value in
          :numref:`listing-default-account-not-verified-message-en` after the table.
        - Installation and app configuration

   .. code-block::
      :caption: Default value for :envvar:`keycloak/login/messages/en/accountNotVerifiedMsg`
      :name: listing-default-account-not-verified-message-en

      'Your account is not verified.<br>You must <a id="loginSelfServiceLink" href="https://${hostname}.${domainname}/univention/selfservice/#/selfservice/verifyaccount" target="_blank">verify your account</a> before you can login.<br/>'


.. envvar:: keycloak/login/messages/de/accountNotVerifiedMsg

   German error message for a self-registered user account that isn't verified
   yet. The error message supports HTML format.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - See default value in
          :numref:`listing-default-account-not-verified-message-de` after the
          table.
        - Installation and app configuration

   .. code-block::
      :caption: Default value for :envvar:`keycloak/login/messages/de/accountNotVerifiedMsg`
      :name: listing-default-account-not-verified-message-de

      'Konto nicht verifiziert.<br>Sie m\\u00FCssen Ihr <a id="loginSelfServiceLink" href="https://${hostname}.${domainname}/univention/selfservice/#/selfservice/verifyaccount" target="_blank">Konto verifizieren</a>, bevor Sie sich einloggen k\\u00F6nnen.<br/>'


.. envvar:: keycloak/csp/frame-ancestors

   Additional entries to the ``frame-ancestors`` directive of the Keycloak
   virtual host. The space separated list of sources can have multiple values
   can be used. For example, ``https://portal.external.com
   https://*.remote.de``. For more information, see *CSP: frame-ancestors* in
   :cite:t:`csp-frame-ancestors`.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - None
        - Installation and app configuration


.. envvar:: keycloak/apache2/ssl/certificate

   Sets the absolute path to the SSL certificate file for the :program:`Apache
   web server` module ``mod_ssl`` of the Keycloak virtual host. The web server
   needs the certificate in the PEM format.

   The web server uses the UCS certificate from
   :samp:`/etc/univention/ssl/ucs-sso-ng.{$domainname}/cert.pem`, if the UCR
   variable has no value.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - :samp:`/etc/univention/ssl/ucs-sso-ng.{$domainname}/cert.pem`
        - Installation and app configuration


.. envvar:: keycloak/apache2/ssl/key

   Sets the absolute path to the private RSA/DSA key of the SSL certificate file
   for the :program:`Apache web server` module ``mod_ssl`` of the Keycloak
   virtual host. The web server needs the certificate in the PEM format.

   The web server uses the UCS private key from
   :samp:`/etc/univention/ssl/ucs-sso-ng.{$domainname}/private.key`, if the UCR
   variable has no value.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - :samp:`/etc/univention/ssl/ucs-sso-ng.{$domainname}/private.key`
        - Installation and app configuration


.. envvar:: keycloak/apache2/ssl/ca

   Sets the absolute path to the certificate of the certificate authority (CA)
   for the :program:`Apache web server` module ``mod_ssl`` of the Keycloak
   virtual host. The web server needs the certificate in the PEM format.

   The web server uses the UCS CA from
   :file:`/etc/univention/ssl/ucsCA/CAcert.pem`, if the UCR variable has no
   value.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - :file:`/etc/univention/ssl/ucsCA/CAcert.pem`
        - Installation and app configuration


.. envvar:: keycloak/cookies/samesite

   This setting sets the ``SameSite`` attribute in all the cookies of Keycloak.
   Possible values are ``Lax``, ``Strict`` and the default value ``None``.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - ``None``
        - Installation and app configuration


.. envvar:: keycloak/server/sso/path

   This setting sets the path used to access Keycloak at the end of the
   Keycloak URL.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - ``/``
        - Installation and app configuration


.. envvar:: keycloak/password/change/endpoint

   This setting sets the endpoint for the password change.
   Per default, the local Univention Management Console Server is used.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - ``None``
        - Installation and app configuration


.. envvar:: keycloak/login/messages/en/accessDeniedMsg

   This setting sets the access denied message during login in English.
   This setting only has effect, if you have configured Keycloak for application
   specific access restriction as described in :ref:`application-authorization`.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - See default value in
          :numref:`listing-default-login-message-en-access-denied` after the
          table.
        - Installation and app configuration

   .. code-block::
      :caption: Default value for :envvar:`keycloak/login/messages/en/accessDeniedMsg`
      :name: listing-default-login-message-en-access-denied

      'Access forbidden.<br>You do not have the needed privileges to access this application. Please contact the administrator that you do not have access to the service {0} if you find this to be incorrect.'


.. envvar:: keycloak/login/messages/de/accessDeniedMsg

   This setting sets the access denied message during login in German.
   This setting only has effect, if you have configured Keycloak for application
   specific access restriction as described in :ref:`application-authorization`.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - See default value in
          :numref:`listing-default-login-message-de-access-denied` after the
          table.
        - Installation and app configuration

   .. code-block::
      :caption: Default value for :envvar:`keycloak/login/messages/de/accessDeniedMsg`
      :name: listing-default-login-message-de-access-denied

      'Zugriff verboten.<br>Bitte wenden Sie sich an den Administrator, dass Sie keinen Zugriff auf den Service {0} haben, wenn Sie feststellen, dass dies nicht korrekt ist.'


.. _css-settings:

Customize the appearance
========================

The :program:`Keycloak` app uses the same web theme as UCS, so that the UCR
variable :envvar:`ucs/web/theme` applies to Keycloak, as well. To adjust the web
theme, follow the steps outlined in :ref:`uv-manual:central-theming-custom` in
the :cite:t:`ucs-manual`.

Administrators can change the values of the following CSS variables to customize
the appearance of the web theme for the sign-in form provided by Keycloak. These
CSS variables are specifically relevant to Keycloak. They take their default
values from UMC and expect CSS background values.

* :envvar:`--login-background`
* :envvar:`--login-box-background`
* :envvar:`--login-logo`

Keycloak also uses
:file:`/usr/share/univention-management-console-login/css/custom.css` and loads
from the URL ``/univention/login/css/custom.css``. The CSS file gives more
control than just the theme.

.. caution::

   You may need to adjust your customizations in the CSS file :file:`custom.css`
   after updates for UCS or the Keycloak app, because CSS selectors may change
   on updates.

.. seealso::

   `background - CSS: Cascading Style Sheets | MDN <https://developer.mozilla.org/en-US/docs/Web/CSS/background>`_
      for more information about the syntax for background values.

.. _language-settings:

Adjusting texts on the Keycloak login page
------------------------------------------

The :program:`Keycloak` app lets Administrators overwrite any messages on the
:program:`Keycloak` login page.
Each text variable value in this login template can be overwritten
by using a UCR variable of the form

:samp:`keycloak/login/messages/[de/en]/key=value`

This make use of the :program:`Keycloak` message bundles that are documented
here:
https://www.keycloak.org/docs/latest/server_development/#messages

For example, the login title in the :program:`Keycloak` login dialogue can be
adjusted like this:

.. code-block::

  $ ucr set \
  keycloak/login/messages/en/loginTitleHtml=\
  'Login at Domainname'


After setting one of these variables, this command
has to be run to make the change visible in :program:`Keycloak` login page:

.. code-block:: console

  $ univention-app configure keycloak

.. warning::

   These settings are local settings. The UCR variables have to be set on each
   host running :program:`Keycloak`.

.. _additional-login-links:

Additional links on the login page
----------------------------------

.. versionadded:: 22.0.1-ucs2 Additional links below login dialog

Administrators can add links below the login dialog, for example to the user
self service for a forgotten password or legal information such as a privacy
statement.

To manage up to 12 links, use the command line tool
:program:`univention-keycloak`. To add links to the login page for both English
and German run the following commands:

.. code-block:: console
   :caption: Add links below login dialog with :program:`univention-keycloak`

   $ univention-keycloak login-links set en 1 "Link 1" "Link 1 description"
   $ univention-keycloak login-links set en 2 "Link 2" "Link 2 description"
   $ univention-keycloak login-links set de 1 "Link 1" "Beschreibung von 1"
   $ univention-keycloak login-links set de 2 "Link 2" "Beschreibung von 2"

The login page then shows the links below the login dialog as in
:numref:`additional-login-links-image-example`.

.. _additional-login-links-image-example:

.. figure:: /images/login_links.png
   :alt: Login links example
   :width: 60%

   Custom links below the login dialog

Use :program:`univention-keycloak` to modify and remove login links.

.. code-block:: console
   :caption: Modify or delete links below login dialog with :program:`univention-keycloak`

   $ univention-keycloak login-links set en 1 "Link 1 new" "Link 1 new description"
   $ univention-keycloak login-links delete en 2

To show the links that the login page has configured for a given language, use
:command:`univention-keycloak` like in the following example:

.. code-block:: console
   :caption: Show configured links below the login dialog with :program:`univention-keycloak`

   $ univention-keycloak login-links get en

.. _cookie-consent-banner:

Cookie consent banner dialog
----------------------------

The :program:`Keycloak` app allows the configuration of a cookie consent banner
dialog on the login page. The UCS portal, the UMC and the login page provided by
the :program:`Keycloak` app share the same configuration for the cookie banner.

For more information about how to configure the cookie consent banner, see
:external+uv-manual:ref:`banner`.

.. _apache-configuration:

Customize web server configuration for Keycloak
===============================================

The :program:`Keycloak` app ships a configuration for the Apache HTTP web server
in :file:`/etc/apache2/sites-available/univention-keycloak.conf`.
The Keycloak app creates the file and overwrites any changes during app updates.
Therefore, administrators shouldn't edit this file.

You as administrator can customize the web server configuration for Keycloak
by creating the file :file:`/var/lib/univention-appcenter/apps/keycloak/data/local-univention-keycloak.conf`.

For example, an administrator may want to restrict the access to the
*Keycloak Admin Console* to a specific IP subnet
and writes the appropriate configuration into :file:`local-univention-keycloak.conf`.

.. code-block:: apache

  <LocationMatch "^(/admin/|/realms/master/)">
                deny from all
                allow from 10.207.0.0/16
  </LocationMatch>

To activate the configuration, you need to validate the configuration
and then tell the web server to reload it.
Use the following commands on the command line as super user.

#. The validation of the configuration is necessary,
   because the Apache HTTP web server terminates upon errors without error message.
   The Apache HTTP web server offers a dedicated command to validate the configuration.

   .. code-block:: console

      $ apachectl configtest
      Syntax OK

#. After the validation didn't show any errors,
   you can restart the Apache HTTP web server to activate your custom changes.

   .. code-block:: console

      $ service apache2 restart

.. _kerberos-authentication:

Activating Kerberos authentication
==================================

In the default configuration, the :program:`Keycloak` app evaluates
:program:`Kerberos` tickets during the authentication process. If you have a UCS
domain with client workstations that obtain :program:`Kerberos` tickets during
the user login process, users can configure their web browsers to send this
ticket to :program:`Keycloak` for authentication to enable a passwordless login,
for example in the UCS portal.

To enable the web browser to send the :program:`Kerberos` tickets, you must
change the following settings:

.. tab:: Mozilla Firefox

   Open a tab and enter ``about:config`` in the address bar to open the
   Firefox configuration. Search for ``network.negotiate-auth.trusted-uris`` and
   add the |FQDN| of your :program:`Keycloak` server, which is
   :samp:`ucs-sso-ng.{[Domain name]}` by default.

.. tab:: Microsoft Edge

   For Microsoft Edge on Windows, you need to configure Kerberos authentication
   in the general settings of the operating system. Open the *Control Panel* and
   move to :menuselection:`Security --> Local Intranet --> Sites --> Advanced`.
   Add the |FQDN| of your :program:`Keycloak` server, :samp:`ucs-sso-ng.{[Domain
   name]}` by default, to the list of ``Websites``.

If you install the :program:`Active Directory-compatible Domain Controller` app
*after* installing :program:`Keycloak`, you need to run the following command on
the Primary Directory Node. It ensures that the Kerberos authentication also works
with the :program:`Active Directory-compatible Domain Controller`:

.. code-block:: console

  $ eval "$(ucr shell keycloak/server/sso/fqdn)"
  $ samba-tool spn add "HTTP/$keycloak_server_sso_fqdn" "krbkeycloak"

Per default, :program:`Keycloak` tries to use :program:`Kerberos`. If no
:program:`Kerberos` ticket is available, *Keycloak* falls back to username and
password authentication. You can deactivate this behavior in the :ref:`Keycloak
Admin Console <keycloak-admin-console>` with the following steps:

* Select the realm ``UCS``.

* On the sidebar, click :guilabel:`User federation` and choose
  ``ldap-provider``.

* Go to the section *Kerberos integration* and deactivate :guilabel:`Allow
  Kerberos authentication`.


.. _application-authorization:

Restrict access to applications
===============================

.. versionadded:: 21.1.2-ucs2


With the |UCS| :program:`simpleSAMLphp` integration, you can restrict access of
groups and users to specific :term:`SAML service providers <SAML SP>` through
the |UDM| SAML settings.

The configuration steps in the following sections restrict access to certain
:term:`SAML service providers <SAML SP>` and :term:`OIDC Relying parties <OIDC
RP>` through group membership in a similar way with :program:`Keycloak`.

.. attention::

   Application access restriction isn't yet integrated into the UDM UMC module
   yet.

   If you already need the application access restriction for groups at this
   time, read on and follow the steps outlined below. Note that you may need to
   perform manual migration steps after the integration is complete.

   If you don't have an immediate need, it's recommended that you wait until the
   integration is complete in a future version of the :program:`Keycloak` app.

This configuration differs from the one provided by :program:`simpleSAMLphp` in
the following ways:

* Only the group membership restricts the access to applications. It isn't
  possible to restrict the access for an individual user directly.

* You must configure group access restrictions for :term:`SAML SP` and
  :term:`OIDC RP` directly in the :ref:`Keycloak Admin Console
  <keycloak-admin-console>`, although you manage users and their group
  memberships in |UDM|.

* By default, :program:`Keycloak` allows access to all users. Only when you
  specifically configure the :term:`SAML SP` or :term:`OIDC RP` to use app
  authorization will :program:`Keycloak` evaluate the access restriction to
  applications.

.. important::

   Univention doesn't support nested groups in the group mapper between UCS and
   :program:`Keycloak`. The reason is that :program:`Keycloak` doesn't support
   groups as members of groups.


.. _authorization-create-auth-flow:

Create authentication flow
--------------------------

:program:`Keycloak` version 21.1.2-ucs2 provides an authenticator extension
called *Univention App authenticator*, which performs the authorization
validation on the user during the sign-in.

To use this authenticator, you need to create a Keycloak *authentication flow*
that includes this authenticator. Use the command :command:`univention-keycloak`
as follows. The command doesn't give any output:

.. code-block:: console
   :caption: Create a Keycloak *authentication flow*

   $ univention-keycloak legacy-authentication-flow create

.. seealso::

   For more information on authentication flows, see :cite:t:`keycloak-auth-flow`.

.. _authorization-assign-auth-flow:

Assign authentication flow
--------------------------

:program:`Keycloak` calls the :term:`SAML SP` and the :term:`OIDC RP` *Client*.
By default, neither :term:`SAML SP` nor :term:`OIDC RP` use the created
authentication flow.

To restrict application access, you must assign the :ref:`created authentication
flow <authorization-create-auth-flow>` to each :term:`Keycloak Client`.
Otherwise, the :term:`Keycloak Client` still allows access to all users. To
assign a specific flow to an existing :term:`Keycloak Client`, use the following
command in :numref:`authorization-assign-auth-flow-listing`.

.. code-block:: console
   :caption: Assign authentication flow to a :term:`Keycloak Client`
   :name: authorization-assign-auth-flow-listing

   $ univention-keycloak client-auth-flow \
     --clientid "REPLACE_WITH_YOUR_CLIENT_ID" \
     --auth-flow "browser flow with legacy app authorization"

.. note::

   You can also pass the option ``--auth-browser-flow`` when you create a
   :term:`SAML SP` or :term:`OIDC RP` as a :term:`Keycloak Client`. See section
   :ref:`saml-idp` on how to create a :term:`Keycloak Client`.


.. _authorization-group-mapper:

Map UDM groups to Keycloak
--------------------------

To restrict access to certain :term:`Keycloak Client`\ s by group membership,
you must map the necessary groups to :program:`Keycloak`. Use the
:ref:`Keycloak Admin Console <keycloak-admin-console>` to create an appropriate
*LDAP mapper*.

#. In :ref:`Keycloak Admin Console <keycloak-admin-console>` go to
   :menuselection:`UCS realm --> User Federation --> ldap-provider --> Mappers
   --> Add mapper`.

#. Choose the *Name* of the mapper freely.

#. Select the *Mapper type* ``group-ldap-mapper`` to extend the form. Fill in
   the fields as following:

   :LDAP Groups DN: Set to the value of the base LDAP DN of your domain, for
     example ``dc=example,dc=local``.

   :Group Object Classes: ``univentionGroup``

   :Ignore Missing Groups: ``On``

   :Membership LDAP Attribute: ``memberUid``

   :Membership Attribute Type: ``UID``

   :Drop non-existing groups during sync: ``On``

   .. important::

      It's strongly recommended to set an *LDAP Filter* in the group mapper so
      that :program:`Keycloak` only maps strictly necessary groups. If you don't
      specify an *LDAP filter*, :program:`Keycloak` synchronizes **all groups**
      from the LDAP directory service. Depending on the size of the groups, it
      may impact the performance of :program:`Keycloak`.

      Example
         To filter groups by their name and only allow :program:`Keycloak` to
         synchronize the mentioned groups, use
         ``(|(cn=umcAccess)(cn=nextcloudAccess))``

#. Scroll down and click :guilabel:`Save`.

To trigger the synchronization of the groups immediately, click the name of the
mapper you just created to open it and select :guilabel:`Sync LDAP groups to
Keycloak` from the *Action* drop-down.

.. _authorization-create-client-roles:

Create Keycloak client roles
----------------------------

The authenticator extension *Univention App authenticator* restricts access by
evaluating the roles of a user in :program:`Keycloak`. It specifically checks
for a client specific role named ``univentionClientAccess``. If this client
specific role exists, the authenticator extension restricts access of all users
that don't have this role.

For each :term:`Keycloak Client` that you want to check access restrictions, you
need to create the role ``univentionClientAccess``. In :ref:`Keycloak Admin
Console <keycloak-admin-console>` go to :menuselection:`UCS realm --> Clients`.
For each client of interest, run the following steps:

#. Select :menuselection:`YOUR_CLIENT --> Roles --> Create role`.

#. Enter name for the role ``univentionClientAccess``.

#. Click :guilabel:`Save`.

   .. important::

      Follow the next section :ref:`authorization-attach-role-to-groups`
      immediately, because saving the client role enforces the sign-in restriction
      for the :term:`Keycloak Client`.

.. seealso::

   For more information on roles in Keycloak, see :cite:t:`keycloak-roles`.

.. _authorization-attach-role-to-groups:

Attach the client specific role to groups
-----------------------------------------

To grant access permission to group members of a group so that they can sign in
to an app, you need to attach the :term:`Keycloak Client` role to the groups.
All group members then inherit the client role.

In :ref:`Keycloak Admin Console <keycloak-admin-console>` go to
:menuselection:`UCS realm --> Groups`. For each group of interest, run the
following steps:

#. Select :menuselection:`YOUR_GROUP --> Role mapping --> Assign role --> Filter by clients`.

#. Find and select the app you intend to control with ``univentionClientAccess``.

   .. warning::

      :program:`Keycloak` doesn't evaluate nested group memberships. Only direct
      group membership of a user give the user the necessary client role.

#. Click :guilabel:`Assign`.

From now on, only the users that inherited the :term:`Keycloak Client` specific
role ``univentionClientAccess`` have access to the respective applications.

.. _authorization-error-page:

Customize the authorization error page
--------------------------------------

:program:`Keycloak` shows an error page, if a user doesn't have access to an
application because the access restriction applies to them.

You can configure the error page through the following App settings:

:German: :envvar:`keycloak/login/messages/de/accessDeniedMsg`
:English: :envvar:`keycloak/login/messages/en/accessDeniedMsg`

You can include HTML format with links in this setting to customize the error
page.

The default message shows the ``client ID`` of the :term:`Keycloak Client` that
forbids access to the user. If you need a human readable name, you can set the
attribute *Name* of the :term:`Keycloak Client` in the :ref:`Keycloak Admin
Console <keycloak-admin-console>`. With the attribute set, Keycloak shows the
*Name* instead of the ``client ID``.

.. important::

   The app setting only applies to the local Keycloak instance. You can use
   different values on the different Keycloak installations, for example, to
   show a link to the local portal.

   For more information, refer to :ref:`language-settings`.
