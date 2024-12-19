.. SPDX-FileCopyrightText: 2023-2024 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _update-to-ucs-5.2:

***********************************
Prepare for the update to |UCS| 5.2
***********************************

This section explains the necessary steps to prepare your domain for the
update to |UCS| 5.2. Before you have executed these steps the update to UCS
5.2 will be blocked. Follow these steps only after

* you migrated all services that use single sign-on for authentication to
  use :program:`Keycloak` as |IDP|,

* or you are absolutely sure no service uses :program:`SimpleSAMLphp`
  or :program:`OpenID Connect Provider` as |IDP| for single sign-on
  authentication.

To prepare your domain for the update to |UCS| 5.2, run the following command on
your UCS :ref:`Primary Directory Node <domain-ldap-primary-directory-node>`:

.. code-block:: console
   :caption: Run Keycloak migration script
   :name: keycloak-migration

   $ univention-keycloak-migration-status --delete --create-sso-uri-setting

What this does is to

* delete old and obsolete UDM objects used by :program:`SimpleSAMLphp` and
  :program:`OpenID Connect Provider`

* and to create an UCR policy for the setting :envvar:`ucs/server/sso/uri`
  used in UCS 5.2 to define the default |IDP| for services.

.. warning::

   After removing these UDM objects, single sign-on with
   :program:`SimpleSAMLphp` or :program:`OpenID Connect Provider`
   will no longer work.
