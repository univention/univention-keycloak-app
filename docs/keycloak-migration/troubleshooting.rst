.. SPDX-FileCopyrightText: 2023-2024 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _troubleshooting:

***************************************
Services validation and troubleshooting
***************************************

Every procedure during the migration has the potential to fail at some point.
This section provides hints for troubleshooting such situations.

.. _troubleshooting-logfiles:

Log files
=========

As first step, review the log files of :program:`Keycloak` and the connected
services:

* Review the log files of :program:`Keycloak` and look for errors. On the UCS
  system that has :program:`Keycloak` installed, run the following command:

  .. code-block:: console

     $ univention-app logs keycloak

* For an extensive troubleshooting guide of the :program:`Keycloak` app, refer
  to :ref:`uv-keycloak-app:app-troubleshooting` in the :cite:t:`ucs-keycloak-doc`.

* Review the log files of the following services:

  * For UMC: :file:`/var/log/univention/management-console-web-server.log`

  * For :program:`Nextcloud`:
    :file:`/var/lib/univention-appcenter/apps/nextcloud/data/nextcloud-data/nextcloud.log`

  * For :program:`ownCloud`:
    :file:`/var/lib/univention-appcenter/apps/owncloud/data/files/owncloud.log`

  * For other services: consult the manual of the service


.. _troubleshooting-sso:

Single sign-on settings
=======================

Also verify the configuration of :program:`Keycloak` and the single sign-on
settings of the services:

* For |OIDC| services make sure that the service has the correct
  :samp:`clientsecret` and :samp:`clientid`. The values for these settings must
  match in :program:`Keycloak` and the services.

* For |SAML| services verify that the service uses the current, public
  certificate of the :program:`Keycloak` server.

* For |SAML| verify that the :samp:`clientid` in the :program:`Keycloak`
  configuration of your :term:`SAML SP` is correct. This is also the issuer
  for the |SAML| authentication request. The value is service specific, but
  needs to match the expectations of the service.

Additionally, verify the following items:

* Ensure that all involved systems have the same and synchronized time.

* Use *Developer Tools* of your browser to see which requests fail to narrow
  down the cause of the problem.

