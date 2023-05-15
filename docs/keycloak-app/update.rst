.. SPDX-FileCopyrightText: 2021-2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _app-update:

******
Update
******

You can update the :program:`Keycloak` app like any other app with Univention
App Center or the command line tool :program:`univention-app`.

This chapter covers additional points to consider before and during the update
process.

.. _app-update-configuration-auto-migration:

Configuration migration during Keycloak app updates
===================================================

Some updates of the :program:`Keycloak` app require a migration of the domain
wide Keycloak configuration, because every Keycloak service in the UCS domain
uses the same, shared configuration.

By default, the :program:`Keycloak` app migrates the configuration during the
app update process. Some changes may conflict with older versions of the
:program:`Keycloak` app. Therefore, make sure that all :program:`Keycloak` app
instances on all UCS systems in the domain have the same version.

.. tip::

   You can deactivate the automatic configuration migration during the app
   update process. Set the value of the UCR variable
   :envvar:`keycloak/auto-migration` to ``false``.

   You can manually migrate the configuration of the :program:`Keycloak` app on
   all UCS system, **after** you updated the app on all UCS systems to the same
   version. Use the following command:

   .. code-block:: console

      $ univention-keycloak upgrade-config
