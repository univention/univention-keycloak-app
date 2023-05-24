.. SPDX-FileCopyrightText: 2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _migration-procedure:

*******************
Migration procedure
*******************

This section explains the steps to migrate the UCS domain and connected services
from :program:`SimpleSAMLPHP` for SAML and :program:`Kopano Connect` for OIDC to
:program:`Keycloak`. This includes the installation and configuration of
:program:`Keycloak` and the configuration of SAML and OIDC services.

Installation of Keycloak
========================

Optional steps (TODO better naming)
===================================

The following sections describe optional steps for the migration procedure.

Import SimpleSAMLPHP signing certificate pair to Keycloak
---------------------------------------------------------

It is possible to import the signing key and certificate from
:program:`SimpleSAMLPHP` into :program:`Keycloak`. This step allows you
to re-use the IDP certificate in the client configuration.

We do not recommend to import the old keys but instead to use the newly
generated keys from :program:`Keycloak`.

To import the key and certificate into :program:`Keycloak`:

* Get the old key and certificate from the *Primary Directory Node*, in most
  cases this will be
  :file:`/etc/simplesamlphp/ucs-sso.ucs.test-idp-certificate.crt` for the
  certificate and
  :file:`/etc/simplesamlphp/ucs-sso.ucs.test-idp-certificate.key` for the
  private key.
* Import the existing key and certificate into :program:`Keycloak` as described
  in cite:t:`keycloak-adding-an-existing-keypair-and-certificate`.
* Make sure to *enable* and *activate* the key and set a priority greater than
  100.
* Disable and deactivate the standard key *rsa-generated*.
* Make sure the imported key is used for *signing* by checking the IDP
  metadata in ``https://$KEYCLOAK_FQDN/realms/ucs/protocol/saml/descriptor``,
  you should see the imported key in `<509Certificate>`.

.. note::

   If you import the old keys, do not update the IDP certificate in
   the services (clients) settings as described in the examples below.

Single sing-on between Keycloak and SimpleSAMLPHP (optional)
------------------------------------------------------------

TODO

Configure SAML/OIDC clients in Keycloak for all services
========================================================

TODO

Configure services to use Keycloak
==================================

TODO

Validate services and troubleshooting
=====================================

TODO, maybe link to Keycloak doc for troubleshooting

Remove SimpleSAMLPHP (TODO)
======================================

TODO to be discussed, how do make clear that the migration has taken place,
remove :program:`univention-saml`?
