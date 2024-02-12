.. SPDX-FileCopyrightText: 2023-2024 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _migration-procedure:

*******************
Migration procedure
*******************

:program:`Keycloak` replaces :program:`SimpleSAMLphp` and the app
:program:`OpenID Connect Provider` as :term:`SAML IDP` and :term:`OIDC provider`
in a future release of |UCS|. This section provides a general overview of the
migration steps and the required considerations to make before migrating. This
migration guide focuses on exclusively on UCS 5.0.

Before the migration can take place, please keep in mind:

* You can migrate services step by step.

* The migration is a manual process.

* Create a backup of the current single sign-on configuration of your services
  **before** the migration, so that you can rollback in case a problem occurs.

* :program:`SimpleSAMLphp` and :program:`OpenID Connect Provider` still work
  even if you installed :program:`Keycloak`.

* After you migrated a service, existing user sessions become invalid. Users have
  to sign in to the migrated service again.

The migration of one or multiple services always includes at least the
following steps:

* The installation and configuration of the :program:`Keycloak` app. For a
  detailed description, see :ref:`uv-keycloak-app:app-installation` in the
  :cite:t:`ucs-keycloak-doc`.

* The creation of :term:`OIDC RP` or :term:`SAML SP`, referred to as *clients*
  in this document, in :program:`Keycloak` for each service. For more
  information about how to create those clients, refer to :ref:`migration-oidc`
  and :ref:`migration-saml`.

* The update of the single sign-on configuration of the services to use
  :program:`Keycloak` as |IDP|. Have a look at the examples in :ref:`migration-examples`.

* The verification that single sign-on works with :program:`Keycloak` as |IDP|

.. toctree::
   :caption: Contents
   :maxdepth: 3

   prerequisites
   oidc
   saml
   forward
