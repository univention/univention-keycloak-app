.. _app-troubleshooting:

***************
Troubleshooting
***************

.. highlight:: console

When you encounter problems with the operation of the :program:`Keycloak` app,
this chapter provides information where you can look closer into and to get an
impression about what is going wrong.

.. _app-log-files:

Log files
=========

The :program:`Keycloak` app produces different logging information in different
places.

:file:`/var/log/univention/appcenter.log`
   Contains log information around activities in the App Center.

   The App Center writes Keycloak relevant information to this file, when you
   run app lifecycle tasks like install, update and uninstall or when you change
   the app settings.

:file:`/var/log/univention/join.log`
   Contains log information from join processes. When the App Center installs
   Keycloak, the app also joins the domain.

Keycloak Docker container
   The app uses the vanilla `Keycloak Docker image <quay-keycloak_>`_. The App
   Center runs the container. You can view log information from the Keycloak
   Docker container with the following command:

   .. code-block::

      $ univention-app logs keycloak

Keycloak Admin Console
   Offers to view event logs in *Events* in the *Manage* section. Administrators
   can see *Login Events* and *Admin Events*. For more information, see
   :cite:t:`keycloak-docs-events`.

.. _app-debugging:

Debugging
=========

To increase the log level for more log information for the :program:`Keycloak`
app, see :envvar:`keycloak/log/level`.

This log level only affects the log information that Keycloak itself generates
and writes to the Docker logs. The App Center sets the Docker container's
``KEYCLOAK_LOGLEVEL`` environment variable to the value of
:envvar:`keycloak/log/level`.
