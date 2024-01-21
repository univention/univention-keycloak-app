.. SPDX-FileCopyrightText: 2023-2024 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _update-to-ucs-5.2:

***********************************
Prepare for the update to |UCS| 5.2
***********************************

This section explains the necessary steps to prepare your domain for the
update to |UCS| 5.2. Follow these steps only after

* you migrated all services that use single sign-on for authentication to
  use :program:`Keycloak` as |IDP|,

* or you are absolutely sure no service uses :program:`SimpleSAMLphp`
  or :program:`OpenID Connect Provider` as |IDP| for single sign-on authentication.

|UCS| before version 5.2 stored all the :program:`SimpleSAMLphp` and
:program:`OpenID Connect Provider` client configurations as |UDM| modules
``saml/serviceprovider`` and ``oidc/rpservice``. The update to 5.2 blocks
until all these objects are removed.

.. warning::

   After removing these client objects, single sign-on with
   :program:`SimpleSAMLphp` or :program:`OpenID Connect Provider` does no longer work.

To prepare your domain for the update to |UCS| 5.2, run the following command on
your UCS :ref:`Primary Directory Node <domain-ldap-primary-directory-node>` to
backup all |IDP| client object settings and subsequently remove them:

.. code-block:: console
   :caption: Remove single sign-on client objects from UDM
   :name: remove-sso-clients

   $ univention-keycloak-migration-status --delete
