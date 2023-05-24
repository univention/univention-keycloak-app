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

You can use the existing :term:`SAML IDP` certificates from
:program:`SimpleSAMLPHP` in the client configuration.

.. note::

   Univention doesn't recommend to import the old keys from
   :program:`SimpleSAMLPHP`, but use the freshly generated keys from
   :program:`Keycloak`.

To import the signing key and the certificate into :program:`Keycloak`, use the
following steps.

#. Copy the private key and the certificate from the UCS *Primary Directory
   Node*. The default locations are:

   * Private key: :file:`/etc/simplesamlphp/ucs-sso.ucs.test-idp-certificate.key`

   * Certificate: :file:`/etc/simplesamlphp/ucs-sso.ucs.test-idp-certificate.crt`

#. Import the copied private key and the certificate to :program:`Keycloak` as
   described in :cite:t:`keycloak-adding-an-existing-keypair-and-certificate`.

#. Make sure to *enable* and *activate* the private key and set the priority to
   a value greater than ``100``.

#. *Disable* and *deactivate* the standard key ``rsa-generated``.

#. Verify that the :term:`SAML IDP` uses the private for signatures. Validate
   the :term:`SAML IDP` metadata in
   :samp:`https://{$KEYCLOAK_FQDN}/realms/ucs/protocol/saml/descriptor` and look
   for the imported key at ``<509Certifcate>``.

.. caution::

   If you import the private key and the certificate from
   :program:`SimpleSAMLPHP` with the previously described steps, don't update
   the :term:`SAML IDP` certificate in the services settings as described in the
   following examples.

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
