.. SPDX-FileCopyrightText: 2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _examples:

*********************************************
Exemplary migration of SAML and OIDC services
*********************************************

This section provides detailed examples how to migrate SAML and OIDC services
to :program:`Keycloak`.

Migration of services using SAML for authentication
===================================================

Generic SAML service ... TODO

.. _migration-nextcloud:

Nextcloud
---------

This section is about migrating the :program:`Nextcloud` app to use
:program:`Keycloak` as :term:`SAML IDP` for the |SAML| authentication. It
assumes that your environment meets the following requirements:

* The configuration of the app :program:`Nextcloud` is complete and done.

* The |SAML| sign-in for :program:`Nextcloud` works with
  :program:`SimpleSAMLPHP` as :term:`SAML IDP`.

* The UCS domain has the latest version of the app :program:`Keycloak`
  installed.

To setup :program:`Nextcloud` for |SAML| with :program:`Keycloak` use the
following steps:

#. To create a :term:`SAML SP` for :program:`Nextcloud`, run the following
   command on the :program:`Keycloak`. Replace :samp:`$NEXTCLOUD_FQDN` with
   the |FQDN| of the UCS system that has :program:`Nextcloud` installed.

   .. code-block:: console
      :caption: Create SAML SP for *Nextcloud* in *Keycloak*
      :name: migration-nextcloud-create-saml-client

      $ export nc_fqdn="REPLACE with NEXTCLOUD_FQDN"
      $ univention-keycloak saml/sp create \
        --metadata-url="https://$nc_fqdn/nextcloud/apps/user_saml/saml/metadata" \
        --role-mapping-single-value

#. To obtain the :program:`Keycloak` base URL and certificate, run
   the following commands on the UCS system that has :program:`Keycloak`
   installed:

   .. code-block:: console
      :caption: Retrieve public certificate and *Keycloak* base URL
      :name: migration-nextcloud-get-idp-settings

      $ univention-keycloak saml/idp/cert get \
         --as-pem \
         --output "/tmp/keycloak.cert"

      $ univention-keycloak get-keycloak-base-url

   The output of the first command in
   :numref:`migration-nextcloud-get-idp-settings` saves the certificate in the
   file :file:`/tmp/keycloak.crt`. Copy this file to the UCS system that has the
   :program:`Nextcloud` app installed.

   The second command in :numref:`migration-nextcloud-get-idp-settings` outputs
   the base URL of your :program:`Keycloak` server. Replace :samp:`{$SSO_URL}`
   in the following instruction with this value.

#. To change the |IDP| settings for :program:`Nextcloud`, run the following
   command on the UCS system that has it installed. Copy the certificate file
   :file:`/tmp/keycloak.cert` from the previous step to the program:`Nextcloud`
   server and replace :samp:`SSO_URL`.

   .. code-block:: console
      :caption: Configure the :program:`Nextcloud` app to use :program:`Keycloak` as |IDP|
      :name: migration-nextcloud-saml-settings

      $ export sso="REPLACE WITH SSO_URL"
      $ univention-app shell nextcloud sudo -u www-data /var/www/html/occ saml:config:set \
        --idp-x509cert="$(cat /tmp/keycloak.cert)" \
        --general-uid_mapping="uid" \
        --idp-singleLogoutService.url="$sso/realms/ucs/protocol/saml" \
        --idp-singleSignOnService.url="$sso/realms/ucs/protocol/saml" \
        --idp-entityId="$SSO_URL/realms/ucs" 1

To validate the setup, visit the sign-in page of your :program:`Nextcloud`
app and initiate a single sign-on. :program:`Nextcloud` redirects you to
:program:`Keycloak` for authentication. You can use :program:`Nextcloud` after
authentication.

.. seealso::

   `Nextcloud <https://www.univention.com/products/univention-app-center/app-catalog/nextcloud/>`_
      in Univention App Catalog

Google Connector
----------------

TODO

Office365 Connector
-------------------

TODO

Migration of services using OIDC for authentication
===================================================

Generic OIDC service ... TODO

Owncloud
--------

TODO
