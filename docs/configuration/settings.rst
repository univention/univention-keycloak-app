.. _app-settings:

********
Settings
********

The Keycloak app uses the following settings for configuration:

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


.. envvar:: keycloak/url/prefix

   Defines the prefix of the URL where you find your Keycloak instance.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - Yes
        - ``keycloak``
        - Only before installation


.. envvar:: keycloak/log/level

   Configures the verbosity of log messages in Keycloak.

   Possible values
      ``ALL``, ``DEBUG``, ``ERROR``, ``FATAL``, ``INFO``, ``OFF``, ``TRACE``,
      ``WARN``.

   For a detailed description of the log level values, see `Keycloak
   documentation: Configuring logging <keycloak-docs-root-logging_>`_.

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


.. envvar:: keycloak/ucs/setup

   Configures Keycloak to use the UCS LDAP directory server as authentication
   backend. The login in the UCS management system becomes a SAML client for
   Keycloak.

   Possible values
      ``true`` and ``false``.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - Yes
        - ``true``
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


.. envvar:: keycloak/ssl/certificate/privatekey

   Defines the path of the certificate private key file that Keycloak uses to
   sign the SAML assertion. Keycloak sends the SAML assertion to the service
   provider.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - None
        - Installation and app configuration


.. envvar:: keycloak/ssl/certificate/certificate

   Defines the path of the certificate public key file that Keycloak uses to
   sign the SAML assertion. Keycloak sends the SAML assertion to the service
   provider.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - None
        - Installation and app configuration

.. TODO Discuss with SME: Doesn't make sense to me. The assertion is signed with
   public and the private key? Signatures are done with the private key and
   verified with the public key.


.. envvar:: keycloak/server/sso/fqdn

   Defines the FQDN to the identity provider in your environment's UCS domain.

   .. list-table::
      :header-rows: 1
      :widths: 2 5 5

      * - Required
        - Default value
        - Set

      * - No
        - None
        - Installation and app configuration


.. envvar:: keycloak/server/sso/certificate/generation

   If set to ``true`` (default), UCS automatically creates the certificate for
   the FQDN of the identity provider defined in
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

.. TODO Discuss with SME: What is the active component in the description? UCS
   or Keycloak?


.. envvar:: keycloak/server/sso/certificate/download

   If set to ``true`` (default), Keycloak automatically downloads the
   certificate for the FQDN of the identiy provided defined in
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

.. TODO Discuss with SME: What is the active component in the description? UCS
   or Keycloak?

.. envvar:: keycloak/server/sso/autoregistraton

   If set to ``true`` (default), the UCS system with the Keycloak app installed
   registers its IP address at the IP address of the identity provider defined in
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

.. TODO Discuss with SME: Where registers UCS its IP address? Why?
