.. SPDX-FileCopyrightText: 2022-2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _app-use-cases:

*********
Use cases
*********

.. highlight:: console

This section describes some uses cases for the app :program:`Keycloak` to give a
deeper insight of the app's capability.

.. _use-case-expired-password:

Expired password and change password on next sign-in
====================================================

In some situations, administrators create a user account with a temporary
password that requires the account owner to change their password during their
first sign-in. The procedure can be company policy or just considered a good
practice. Also, if for any other reason like a lost or compromised user
password, the account owner can contact the administrator and request a password
change.

.. seealso::

   :ref:`users-management-table-account`
      For user account expire and set password upon first login, refer to
      :cite:t:`ucs-manual`.

To enable these capabilities with :program:`Keycloak`, the app offers the
following extensions. The extensions *only* provide the capabilities in the
*UCS* realm with the :program:`Keycloak` app installed.

Univention LDAP mapper
   In :ref:`Keycloak Admin Console <keycloak-admin-console>` follow
   :menuselection:`UCS realm --> User Federation --> ldap-provider --> Mappers`

   The LDAP mapper reads necessary attributes from the LDAP directory and
   triggers a password update when needed.

Univention update password
   In :ref:`Keycloak Admin Console <keycloak-admin-console>` follow
   :menuselection:`UCS realm --> Authentication --> Required Actions`

   Univention update password provides dialogs and forms in the Keycloak login
   flow.


.. _use-case-reconfigure-sso:

Single sign-on through external public domain name
==================================================

The typical single sign-on configuration in UCS uses a shared DNS record to
provide a failover for the sign-in. The :term:`SAML IDP` is available at
:samp:`ucs-sso-ng.{$domainname}`. The administrator chose the environment's
:samp:`{$domainname}` during UCS installation.

.. seealso::

   :ref:`installation-domain-settings` during UCS installation
      for information about domain modes, settings and naming conventions in the
      :cite:t:`ucs-manual`.

Administrators often choose the UCS domain name for an intranet scenario and
adapt the service configuration to match the target domain and hostnames. The
term |FQDN| identifies the combination of hostname and domain used to uniquely
identify a service. For more information, see :cite:t:`wikipedia-fqdn`.

The use case *single sign-on through external, public domain name* addresses
administrators who want single sign-on availability from the internet.
Administrators find the steps to reconfigure the |FQDN| for the single sing-on
and the UCS portal in this section. The configuration for this scenario
recommends two UCS servers or more for serving the different |FQDN|\ s. If you
encounter problems during the steps below, see :ref:`troubleshoot-custom-fqdn`.

Validate configuration success
   Administrators can validate the success of their configuration with the
   following steps:

   #. Use your preferred web browser and open the UCS portal under the just
      configured |FQDN|.

   #. Sign in as user through single sign-on.

   #. After sign-in through single-sign on, the browser redirects you as user back
      to the UMC portal.

   #. If you encounter problems during the validation, see :ref:`troubleshoot-custom-fqdn`.

.. note::

   The following aspects faced by administrators encounter in this use case are
   beyond the scope of this document:

   * Configuration of an external DNS to point to the UCS system.

   * Configuration of network components to route the connection from the
     internet to the UCS system.

   * Obtaining a valid certificate from a CA.

.. warning::

   The :program:`Keycloak` admin interface as well as the :program:`Keycloak` REST API
   are also publicly available if the :program:`Keycloak` app was configured to be available
   externally. For security reasons, this should be restricted.
   Please see :ref:`apache-configuration` for an exemplary configuration.


External |FQDN| different from internal UCS name
------------------------------------------------

.. versionadded:: 21.0.1-ucs2

A common scenario is to have the UCS portal available at one |FQDN|, such as
``portal.internet.domain``, and single sign-on available at another
different |FQDN|, such as ``sso.internet.domain``.

Before starting with the configuration of this use case, consider the following
aspects:

Pre-conditions:
   For the scenario described below, it's important to have the following
   setup in place, before you proceed:

   #. You configured the external DNS entry for Keycloak, for example
      ``sso.internet.domain``.

   #. You configured the external DNS entry for the UCS portal, for example
      ``portal.internet.domain``.

   #. You have obtained proper SSL certificates for Keycloak and the UCS portal
      new |FQDN|.

   The following steps require a working network access from the UCS system to the
   external identity provider |FQDN|.

.. _use-case-custom-fqdn-idp:

Configuration of the identity provider
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

To configure single sign-on on each Keycloak instance in your UCS domain,
follow the steps below:

#. Configure the single sign-on |FQDN| to a custom value. Set the following UCR
   variables:

   .. code-block:: console

      $ SSO_FQDN=sso.internet.domain
      $ ucr set keycloak/server/sso/fqdn="${SSO_FQDN}"
      $ ucr set keycloak/server/sso/autoregistration=false
      $ ucr set keycloak/apache2/ssl/certificate="/path/to/${SSO_FQDN}/cert.pem"
      $ ucr set keycloak/apache2/ssl/key='/path/certificate/${SSO_FQDN}/private.key'
      # Add the new public domain of the portal to the frame-ancestor to the CSP
      $ ucr set keycloak/csp/frame-ancestors='https://*.internet.domain'

      $ univention-app configure keycloak

#. Adjust the standard Keycloak portal entry in the UCS domain after changing
   the single sign-on |FQDN|:

   .. code-block:: console

      $ udm portals/entry modify \
        --dn "cn=keycloak,cn=entry,cn=portals,cn=univention,$(ucr get ldap/base)" \
        --set link='"en_US "https://sso.internet.domain/admin/"'


.. warning::

   After changing the configuration of the identity provider with the previous
   steps, all services can't use that identity provider until proper
   configuration.

.. _use-case-custom-fqdn-ucs-systems:

Configuration of UMC as service provider
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

To re-configure single sign-on for UMC on all UCS systems in the domain,
run the following commands:

.. code-block:: console

   $ ucr set umc/saml/idp-server="https://${SSO_FQDN}/realms/ucs/protocol/saml/descriptor"
   $ service slapd restart

For UCS systems joining the domain, configure a UCR policy and assign it the UCS
systems before you install them. The UCR policy must set
:envvar:`umc/saml/idp-server` to your custom |FQDN|.

.. _use-case-custom-fqdn-umc:

Configuration of UCS Portal to use external fully qualified domain name
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

As an example use case to expose the UCS portal to the internet, follow the
steps below. Apply the steps only to the UCS system that exposes the UCS portal
to the internet.

#. Store the certificate files for the UCS portal in the following locations on
   your UCS system:

   * Certificate: :samp:`/etc/univention/ssl/{$PORTAL_FQDN}/cert.pem`

   * Private key for the certificate: :samp:`/etc/univention/ssl/{$PORTAL_FQDN}/private.key`

#. Configure the UCR variables to use the custom |FQDN| and the certificates:

   .. code-block:: console

      $ SSO_FQDN=sso.internet.domain
      $ PORTAL_FQDN=portal.internet.domain
      $ ucr set umc/saml/sp-server="${PORTAL_FQDN}"
      $ ucr set umc/saml/idp-server="https://${SSO_FQDN}/realms/ucs/protocol/saml/descriptor"

#. Run the join script to update the web server configuration:

   .. code-block:: console

      $ univention-run-join-scripts \
      --force \
      --run-scripts 92univention-management-console-web-server.inst

.. _use-case-same-fqdn-as-host:

External |FQDN| identical to internal UCS name
----------------------------------------------

.. versionadded:: 21.1.0-ucs1

In this scenario the |FQDN| of the UCS system and the external name for
accessing the UCS Portal are identical. Furthermore, the name for the single
sign-on endpoint uses the same |FQDN|. To achieve this use a
different URL path for the single sign-on endpoint, for example:

:Internal name: ``portal.example.test``
:External name: ``portal.example.test``
:Single sign-on URL: ``portal.example.test/auth``

Pre-conditions:
   For this scenario, it's important to have the following setup in place,
   before you proceed:

   #. You configured the external DNS entry for Keycloak, for example
      ``portal.example.test``.

   #. You have obtained proper SSL certificates for this name, for example with
      the :program:`Let's Encrypt` app from the App Center.

.. warning::

   In this scenario the new :program:`Keycloak` URL path must not be ``/``
   to not override the global configuration of the web server.

.. _use-case-same-fqdn-as-host-idp:

Configuration of the identity provider
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

To configure this scenario run the following steps on each :program:`Keycloak`
instance in your UCS domain.

.. code-block:: console

   $ FQDN="portal.example.test"
   $ PATH="/auth"
   $ ucr set keycloak/server/sso/fqdn="$FQDN"
   $ ucr set keycloak/server/sso/path="$PATH"
   $ ucr set keycloak/server/sso/virtualhost=false
   $ ucr set keycloak/server/sso/autoregistration=false

   $ univention-app configure keycloak

.. warning::

   After changing the configuration of the identity provider with the previous
   steps, all services can't use that identity provider until proper
   configuration.

.. _use-case-same-fqdn-as-host-umc:

Configuration of UMC as service provider
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

To re-configure single sign-on for UMC on all UCS systems in the domain,
run the following commands:

.. code-block:: console

   $ FQDN="portal.example.test"
   $ PATH="/auth"
   $ ucr set \
     umc/saml/idp-server="https://${SSO_FQDN}${PATH}/realms/ucs/protocol/saml/descriptor"
   $ service slapd restart

For UCS systems joining the domain, configure a UCR policy and assign it the UCS
systems before you install them. The UCR policy must set
:envvar:`umc/saml/idp-server` to your custom :term:`SAML IDP` URL.

.. _use-case-lets-encrypt:

Official (:program:`Let's Encrypt`) certificates for single sing-on
-------------------------------------------------------------------

If the single sing-on endpoint is exposed to the internet, usually an
official certificate for the server is required. This can be achieved
with the :program:`Let's Encrypt` app (but it is not required to use this
app to create the official certificate).

.. note::

   The examples below assume the :program:`Let's Encrypt` was
   used to create the certificate. The actual filenames of the
   certificate and key can differ depending on which mechanism
   was used to create the certificate.

Dedicated |FQDN| for single sing-on endpoint
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

In this scenario the single sing-on endpoint has its own (virtual host)
web server configuration. The certificate files can be configured with
following UCR variables.

.. code-block:: console

   $ cert_file="/etc/univention/letsencrypt/signed_chain.crt"
   $ key_file="/etc/univention/letsencrypt/domain.key"
   $ ucr set keycloak/apache2/ssl/certificate="$cert_file"
   $ ucr set keycloak/apache2/ssl/key="$key_file"
   $ systemctl reload apache2.service

Single sing-on |FQDN| identical to UCS Portal |FQDN| (or internal name)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Here the :program:`Keycloak` app uses the global web server configuration and
therefor the standard UCR variables for the :program:`Apache` certificate
files can be used.

.. code-block:: console

   $ cert_file="/etc/univention/letsencrypt/signed_chain.crt"
   $ key_file="/etc/univention/letsencrypt/domain.key"
   $ ucr set apache2/ssl/certificate="$cert_file"
   $ ucr set apache2/ssl/key="$key_file"
   $ systemctl reload apache2.service
