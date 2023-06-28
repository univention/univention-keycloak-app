.. SPDX-FileCopyrightText: 2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _migration-procedure-forward-ssp-to-keycloak:

Forward from legacy :program:`SimpleSAMLphp` to :program:`Keycloak`
===================================================================

This section is about single sign-on between services that already use
:program:`Keycloak` and other services that still use
:program:`SimpleSAMLphp`.
This configuration can be a temporary solution for environments which
have a lot of services to migrate, and where single sign-on between all
services needs to be available during the time of the migration.
Keep in mind, that the outlined setup is only an
short-term solution for your environment until all clients completed the
migration to :program:`Keycloak`.

.. important::

   This setup is only important if single sign-on between migrated and not
   migrated services is needed during the time of the migration.
   Future releases will not support this setup.

1. Download the certificate from the :program:`Keycloak` app and save it to the
   local file :file:`/etc/ssl/certs/ucs-sso-ng.keycloak-signing.pem`:

   .. code-block:: console
      :caption: Download :program:`Keycloak` certificate

      $ univention-keycloak saml/idp/cert get \
      --as-pem \
      --output /etc/ssl/certs/ucs-sso-ng.keycloak-signing.pem

#. Create a client for :program:`SimpleSAMLphp` in :program:`Keycloak`:

   .. code-block:: console
      :caption: Create client for :program:`SimpleSAMLphp`

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

#. Create a remote |IDP| for :program:`Keycloak` in :program:`SimpleSAMLphp`:

   .. code-block:: console
      :caption: Create remote |IDP| for :program:`Keycloak` in :program:`SimpleSAMLphp`

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
