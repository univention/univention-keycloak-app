.. SPDX-FileCopyrightText: 2022-2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _app-installation:

************
Installation
************

.. highlight:: console

You can install the :program:`Keycloak` app like any other app with Univention
App Center. The App Center only allows to install Keycloak on a UCS system with
system role *Primary Directory Node* or *Backup Directory Node*. For more information, see
:ref:`uv-manual:domain-ldap-primary-directory-node` in :cite:t:`ucs-manual`.

UCS offers two different ways for app installation:

* With the web browser in the UCS management system

* With the command-line

For general information about Univention App Center and how to use it for software
installation, see :ref:`uv-manual:software-appcenter` in :cite:t:`ucs-manual`.

.. _installation-browser:

Installation with the web browser
=================================

To install Keycloak from the UCS management system, use the following steps:

#. Use a web browser and sign in to the UCS management system.

#. Open the *App Center*.

#. Select or search for *Keycloak* and open the app with a click.

#. To install Keycloak, click :guilabel:`Install`.

#. Leave the *App settings* in their defaults or adjust them to your
   preferences. For a reference, see :ref:`app-settings`.

#. To start the installation, click :guilabel:`Start Installation`.

.. note::

   To install apps, the user account you choose for login to the UCS management
   system must have domain administration rights, for example the username
   ``Administrator``. User accounts with domain administration rights belong to
   the user group ``Domain Admins``.

   For more information, see :ref:`uv-manual:delegated-administration` in
   :cite:t:`ucs-manual`.

.. _installation-command-line:

Installation with command-line
==============================

To install the :program:`Keycloak` app from the command-line, use the following
steps:

#. Sign in to a terminal or remote shell with a username with administration
   rights, for example ``root``.

#. Choose between default and custom settings and run the appropriate
   installation command.

   .. tab:: Default settings

      For installation with default settings, run:

      .. code-block::

         $ univention-app install keycloak

   .. tab:: Custom settings

      To pass customized settings to the app during installation, run the
      following command:

      .. code-block::

         $ univention-app install --set $SETTING_KEY=$SETTING_VALUE keycloak

      .. caution::

         Some settings don't allow changes after installation. To overwrite
         their default values, set them before the installation. For a
         reference, see :ref:`app-settings`.


.. _installation-initial-configuration:

Initial Keycloak configuration
==============================

The first installation of the :program:`Keycloak` app in the UCS domain creates
an administrative user named `admin`, whose password is written to `/etc/keycloak.secret`.
With this user, the initial configuration of `Keycloak` is created.

To not overwrite custom settings, subsequent installations of the
:program:`Keycloak` app on additional UCS systems in the domain don't create the
initial configuration.

.. admonition:: Factory reset of the configuration

   For a *factory reset* of the configuration, delete the realm *UCS* in the
   *Keycloak Admin Console*.

   .. warning::

      Beware, your installation looses all custom configuration settings, custom
      services providers and other changes to the realm *UCS*.

   After you deleted the realm *UCS*, create the initial configuration with the
   following command:

   .. code-block:: console

      $ univention-keycloak --binduser=admin --bindpwdfile=/etc/keycloak.secret init

   .. warning::

       After you deleted the realm *UCS*, create the initial configuration with the
       following command:

.. _keycloak-admin-console:

Sign in to Keycloak Admin Console
=================================

After a successful installation, signed in domain administrator users see the
tile *Keycloak* on the UCS Portal, that directs them to the *Keycloak Admin
Console*.

The URL has the following scheme:
:samp:`https://ucs-sso-ng.{$domainname}/admin/`. The :samp:`{$domainname}` is your
UCS domain name.

Example:
   https://ucs-sso-ng.example.com/admin/

The username for login is the *name of the initial admin user* defined during
installation and saved in the UCR variable :envvar:`keycloak/admin/user`.

.. note::

   All users in the ``Domain Admins``, for example the domain user
   ``Administrator``, can also sign in to the Keycloak Admin Console.

.. _metadata-discovery-documents:

Fetch metadata for service provider configuration
=================================================

|OIDC| and |SAML| both offer machine readable information to the services that
want to use the authentication services in Keycloak. This information is the
metadata discovery documents.

In the Keycloak Admin Console you can find them at :menuselection:`realm
settings --> UCS --> Endpoints`. At the endpoints you see *OpenID Endpoint
Configuration* and *SAML 2.0 Identity Provider Metadata*. To view the metadata
discovery documents, click the endpoint entries.

With the following commands you can obtain the URLs to the metadata information.
Some services comfortably take the URL and configure the authentication
automatically.

.. tab:: OIDC

   To download the metadata information for |OIDC|, run the following command:

   .. code-block::

      $ wget "https://ucs-sso-ng.$(hostname -d)/keycloak/realms/ucs/.well-known/openid-configuration"

.. tab:: SAML

   To download the metadata information for |SAML|, run the following command:

   .. code-block::

      $ wget "https://ucs-sso-ng.$(hostname -d)/keycloak/realms/ucs/protocol/saml/descriptor"
