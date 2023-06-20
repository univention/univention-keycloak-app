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

Before you apply the outlined steps, decide on your |FQDN| setup for your
:program:`Keycloak` app. For more details, see :ref:`use-case-reconfigure-sso`.

.. _sso-keycloak-simplesamlphp:

Single sign-on between :program:`Keycloak` and :program:`SimpleSAMLPHP`
-----------------------------------------------------------------------

This section is about single sign-on between services that already use
:program:`Keycloak` and other services, that *still* use
:program:`SimpleSAMLPHP`. Keep in mind, that the outlined setup is an
intermediate solution for your environment, until all clients completed the
migration to :program:`Keycloak`.

.. important::

   UCS 5.2 doesn't support this setup.

1. Download the certificate from the :program:`Keycloak` app and save it to the
   local file :file:`/etc/ssl/certs/ucs-sso-ng.keycloak-signing.pem`:

   .. code-block:: console
      :caption: Download :program:`Keycloak` certificate

      $ univention-keycloak saml/idp/cert get \
      --as-pem \
      --output /etc/ssl/certs/ucs-sso-ng.keycloak-signing.pem

#. Create a client for :program:`SimpleSAMLPHP` in :program:`Keycloak`:

   .. code-block:: console
      :caption: Create client for :program:`SimpleSAMLPHP`

      $ univention-keycloak saml/sp create \
      --umc-uid-mapper \
      --metadata-url \
      "https://${ucs_sso_fqdn}/simplesamlphp/module.php/saml/sp/metadata.php/default-sp" \
      --redirect-urls \
      "https://${ucs_sso_fqdn}/simplesamlphp/module.php/saml/sp/saml2-acs.php/default-sp"

#. Change the default provider from ``univention-ldap`` to ``default-sp``:

   .. code-block:: console
      :caption: Change default provider

      $ ucr set saml/idp/authsource=default-sp

#. Create a remote |IDP| for :program:`Keycloak` in :program:`SimpleSAMLPHP`:

   .. code-block:: console
      :caption: Create remote |IDP| for :program:`Keycloak` in :program:`SimpleSAMLPHP`

      $ kc_provider=$(univention-keycloak get-keycloak-base-url)
      $ cat <<EOF > /etc/simplesamlphp/metadata/saml20-idp-remote.php
        <?php
        \$metadata['https://${kc_provider}/realms/ucs'] = [
          'SingleSignOnService'  => 'https://${kc_provider}/realms/ucs/protocol/saml',
          'SingleLogoutService'  => 'https://${kc_provider}/realms/ucs/protocol/saml',
          'certificate'          => 'ucs-sso-ng.keycloak-signing.pem',
          'authproc' => array(
            50 => array(
              'class' => 'core:AttributeCopy',
              'urn:oid:0.9.2342.19200300.100.1.1' => 'uid',
            ),
          ),
        ];
        EOF


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

TODO: We decided that installing the Keycloak app is enough to allow the update
to UCS 5.2. So no extra steps are needed after the migration. This section
can be removed.
