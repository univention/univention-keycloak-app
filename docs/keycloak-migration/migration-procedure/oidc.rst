.. SPDX-FileCopyrightText: 2023-2024 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _migration-oidc:

Migration of services using OIDC for authentication
===================================================

.. note::

   If you don't have :program:`OpenID Connect Provider` installed in
   your domain, there is no need for the migration described in this chapter.
   In fact the following |UDM| commands will fail as the module ``oidc/rpservice``
   is only available if :program:`OpenID Connect Provider` is installed.

This section gives a general idea about the migration of services that use
:program:`OpenID Connect Provider` as :term:`OIDC Provider` for the
authentication to :program:`Keycloak` as :term:`OIDC Provider`.

The general approach for the migration includes the following:

* Install the latest version of the :program:`Keycloak` app in the UCS domain.

* Get an overview of all the services that use :program:`OpenID Connect
  Provider` and their settings.

* Create an :term:`OIDC RP`, the client, in :program:`Keycloak` for every
  service that uses :program:`OpenID Connect Provider` as :term:`OIDC Provider`.

* Change the |OIDC| settings in the services to use :program:`Keycloak` as
  :term:`OIDC Provider` and validate the setup.

To setup a service for |OIDC| with :program:`Keycloak` use the following steps:

#. The |UDM| module ``oidc/rpservice`` configures services that use
   :program:`OpenID Connect Provider`. To get a list of all the services and
   settings, run the following command on the UCS *Primary Directory Node*:

   .. code-block:: console
      :caption: List all services that use *OpenID Connect Provider* for |OIDC|

      $ udm oidc/rpservice list

#. Each service has a ``clientid``, a ``clientsecret``, and a ``redirectURI``.
   You need the values of these settings to create identical clients for the
   service in :program:`Keycloak`. On the UCS system where you have installed
   :program:`Keycloak`, create an OIDC client with the following command:

   .. code-block:: console
      :caption: Create an *OIDC client* for the service in *Keycloak*

      $ univention-keycloak oidc/rp create \
        --client-secret clientsecret \
        --app-url redirectURI \
        clientid

   .. note::

      In case you made custom settings of your :program:`OpenID Connect
      Provider` installation, review the following files on your UCS system,
      that has the app installed:

      * :file:`/etc/kopano/konnectd.cfg`
      * :file:`/etc/kopano/konnectd-identifier-registration.yaml`
      * :file:`/etc/kopano/konnectd-identifier-scopes.yaml`

#. You can also use the :ref:`Keycloak Admin Console <keycloak-admin-console>`
   to create OIDC clients manually or to adjust clients created with
   :samp:`univention-keycloak oidc/rp create`. See also
   :ref:`uv-keycloak-app:oidc-op` for more information on how to manage OIDC
   client clients with :program:`Keycloak`.

#. After you created the OIDC client for your service, you need to change
   the |IDP| settings that point to the :term:`OIDC Provider` in the |OIDC|
   configuration of the service. Because the services are
   highly individual in the way they configure |OIDC|, this documentation can't
   provide a general description. At least, you need the base URL of your
   :program:`Keycloak` server. Run the following command on the UCS system that
   has :program:`Keycloak` installed:

   .. code-block:: console
      :caption: Get base URL of the *Keycloak* server

      $ univention-keycloak get-keycloak-base-url

#. Some services may need to configure the :term:`OIDC Provider` URL. Its value
   is :samp:`{SSO_URL}/realms/ucs`. Replace :samp:`{SSO_URL}` with the output
   from the previous command.

   Other services may have individual settings for the authorization endpoint,
   the token endpoint, and so on. To get these URLs, run the following commands
   on the :program:`Keycloak` server.

   .. code-block:: console
      :caption: Get different endpoint URLs

      $ SSO_URL="$(univention-keycloak get-keycloak-base-url)"
      $ univention-install jq
      $ curl "$SSO_URL/realms/ucs/.well-known/openid-configuration" | jq
      {
        "issuer": "https://ucs-sso-ng.example.com/realms/ucs",
        "authorization_endpoint": "https://ucs-sso-ng.example.com/realms/ucs/protocol/openid-connect/auth",
        "token_endpoint": "https://ucs-sso-ng.example.com/realms/ucs/protocol/openid-connect/token",
        "introspection_endpoint": "https://ucs-sso-ng.example.com/realms/ucs/protocol/openid-connect/token/introspect",
        "userinfo_endpoint": "https://ucs-sso-ng.example.com/realms/ucs/protocol/openid-connect/userinfo",
        "end_session_endpoint": "https://ucs-sso-ng.example.com/realms/ucs/protocol/openid-connect/logout",
        ...
      }

   You don't need to change the settings for the client name and secret, because
   you have created an OIDC client with identical values in
   :program:`Keycloak`.

To get a better picture using |OIDC| with :program:`Keycloak`, have a look at
the examples given in section :ref:`migration-example-oidc`.
