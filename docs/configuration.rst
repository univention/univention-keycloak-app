.. _app-configuration:

*************
Configuration
*************

The :program:`Keycloak` app offers various configuration options. Some settings
don't allow changes after installation. Therefore, you must set them carefully
**before** installation. You find those settings marked with *Only before
installation* in :ref:`app-settings`. You can change all other settings at any
time after the installation.

To change settings later after installation, sign in to the UCS management
system and go to :menuselection:`App Center --> Keyloak --> Manage Installation
--> App Settings`. On the appearing *Configure Keycloak* page, you can change
the settings and apply them to the app with a click on :guilabel:`Apply
Changes`.

The App Center then *reinitializes* the Docker container for the Keycloak app.
*Reinitilize* means the App Center throws away the running Keycloak Docker
container and creates a fresh Keycloak Docker container with the just changed
settings.

.. _app-settings:

Settings
========

The following references shows the available settings within the
:program:`Keycloak` app.

Keycloak for sure has a lot more possibilities for configuration and
customization. For more information, consult :cite:t:`keycloak-docs`.


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
