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
Keyloak --> Manage Installation --> App Settings`. On the appearing *Configure
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

.. _oidc-sp:

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

.. note::

   If the administrator chooses ``Confidential`` as *Access Type* on the client configuration page,
   Keycloak offers an additional *Credentials* tab with the credentials.

.. _2fa-authentication:

Two-factor authentication for Keycloak
======================================

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
