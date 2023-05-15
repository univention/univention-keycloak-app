.. SPDX-FileCopyrightText: 2022-2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

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

.. _troubleshoot-custom-fqdn:

Configuration of single sign-on through external public domain
==============================================================

Administrators may encounter some problems when reconfiguring of the
Univention Management Console and Keycloak for a custom |FQDN|. This section
describes the most common problems that may occur.

.. _troubleshoot-custom-fqdn-join-script-failure-3:

Univention Management Console join script failure
-------------------------------------------------

During the run of the |UMC| join script as described in
:ref:`use-case-custom-fqdn-ucs-systems`, the join script may fail with the error
code ``3``.

During the script run, the join script downloads the |SAML| metadata from the
:term:`SAML IDP` specified in :envvar:`umc/saml/idp-server`. The download was
unsuccessful. Check manually, for example with your web browser, if you can
reach the metadata at
:samp:`https://{$SSO_FQDN}/realms/ucs/protocol/saml/descriptor`. After you can
load the metadata manually, run the following commands:

.. code-block:: console

   # Set the SAML metadata url
   $ ucr set umc/saml/idp-server="https://${SSO_FQDN}/realms/ucs/protocol/saml/descriptor"

   # Execute the join script again
   $ univention-run-join-scripts --force --run-scripts 92univention-management-console-web-server.inst


.. _troubleshoot-custom-fqdn-sso-session-refresh:

Single sign-on session not refreshed
------------------------------------

After a sign-in to the UCS portal through single sign-on, the portal passively
refreshes the user session every five minutes. If the configuration of the
Keycloak virtual host in the Apache web server is incorrect, the passive refresh
doesn't work for the UCS portal or other services.

To allow external connections to Keycloak, you need to add the sources as space
separated list to the UCR variable :envvar:`keycloak/csp/frame-ancestors`.

.. tip::

   Recommendation
      To test this behavior, use a private or incognito session in your web browser.