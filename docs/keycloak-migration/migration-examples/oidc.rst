.. SPDX-FileCopyrightText: 2023-2024 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _migration-example-oidc:

Services using OIDC
===================

The following examples demonstrate the migration of services that use |OIDC| for
authentication and :program:`OpenID Connect Provider` as |IDP| to
:program:`Keycloak` as |IDP|.

.. _migration-ownCloud:

ownCloud
--------

This section is about the migration of the :program:`ownCloud` app to use
:program:`Keycloak` as :term:`OIDC Provider` for authentication. It assumes that your
environment meets the following requirements:

* The configuration of the app :program:`ownCloud` is complete and done.

* The |OIDC| sign-in for :program:`ownCloud` works with :program:`OpenID Connect Provider` as :term:`OIDC Provider`.

* The UCS domain has the latest version of the app :program:`Keycloak`
  installed.

To setup :program:`ownCloud` for |OIDC| with :program:`Keycloak` use the
following steps:

#. To obtain the necessary information such as ``clientsecret`` and
   ``redirectURI``, run the following command on the UCS *Primary Directory
   Node*. You need the values to create the :term:`OIDC RP`, the client, in the
   next step.

   .. code-block:: console
      :caption: Get current settings for the *ownCloud* OIDC client
      :name: migration-owncloud-obtain-oidc-information

      $ udm oidc/rpservice list --filter name=owncloud
        DN: cn=owncloud,cn=oidc,cn=univention,dc=...
          applicationtype: web
          clientid: owncloud
          clientsecret: -> copy this value
          insecure: None
          name: owncloud
          redirectURI: -> copy this value
          trusted: yes

   Look for the values of ``clientsecret`` and ``redirectURI`` and copy them,
   for example, into a temporary text file.

#. To create the :term:`OIDC RP`, the client, for :program:`ownCloud` in
   :program:`Keycloak`, run the following commands on the UCS system that has
   :program:`Keycloak` installed. Replace :samp:`{clientsecret}` and
   :samp:`{redirectURI}` with the values for these settings in
   :numref:`migration-owncloud-obtain-oidc-information` from the previous step.

   .. code-block:: console
      :caption: Create OIDC client for *ownCloud* in *Keycloak*
      :name: migration-owncloud-create-oidc-rp

      $ CLIENT_SECRET="REPLACE WITH clientsecret"
      $ REDIRECT_URI="REPLACE WITH redirectURI"
      $ univention-keycloak oidc/rp create \
         --client-secret "$CLIENT_SECRET" \
         --app-url "$REDIRECT_URI" owncloud

#. To obtain the base URL of your :program:`Keycloak` server, run the following
   command on the UCS system that has it installed:

   .. code-block:: console
      :caption: Obtain *Keycloak* base URL
      :name: migration-owncloud-keycloak-base-url

      $ univention-keycloak get-keycloak-base-url

   Replace :samp:`{SSO_URL}` in the following step with this value.

#. Change the |IDP| setting in :program:`ownCloud`. Run the following command on
   the UCS system that has :program:`ownCloud` installed:

   .. code-block:: console
      :caption: Change IDP settings in *ownCloud*
      :name: migration-owncloud-idp-settings

      $ SSO_URL="REPLACE WITH SSO_URL"
      $ univention-app configure owncloud \
        --set OWNCLOUD_OPENID_PROVIDER_URL="$SSO_URL/realms/ucs"

To validate the setup, visit the sign-in page of your :program:`ownCloud`
app and initiate a single sign-on. :program:`ownCloud` redirects you to
:program:`Keycloak` for authentication. You can use :program:`ownCloud` after
authentication.

.. seealso::

   `ownCloud <https://www.univention.com/products/univention-app-center/app-catalog/owncloud/>`_
      in Univention App Catalog
