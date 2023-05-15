.. SPDX-FileCopyrightText: 2021-2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _app-limitations:

****************************
Requirements and limitations
****************************

To ensure a smooth operation of the :program:`Keycloak` app on |UCS|,
administrators need to know the following requirements and limitations:

.. _limitation-user-federation:

User federation and synchronization
===================================

The app configures a user federation in the realm *UCS*. **Don't** remove the
user federation or Keycloak won't be able to resolve users anymore.

The configured user federation in the realm *UCS* doesn't synchronize the user
accounts from the UCS LDAP to Keycloak. For more information, see
:ref:`app-design-decisions`.

.. _limitation-primary-node:

Installation on UCS
===================

The App Center installs the app :program:`Keycloak` on a UCS 5.0-x Primary
Directory Node or Backup Directory Node in your UCS environment, see
:ref:`app-installation`. The app is suitable for production use in UCS domains.
Administrators need to keep in mind, other apps may be unable to authenticate
users through SAML without manual reconfiguration.

Administrators need to take care with experiments that involve the
reconfiguration, for example, of UMC, and other services to use
Keycloak. The experiments may have undesired results. In particular, when you
change the UCR variable :envvar:`umc/saml/idp-server` to point to your Keycloak
installation and restart the LDAP server, the LDAP server doesn't accept
SAML tickets any longer that the *simpleSAMLphp* based identity provider issued.
So users find their existing sessions invalidated.

.. _limitation-no-user-activation:

No user activation for SAML
===========================

In the *Users* UMC module, the user account's *SAML settings* at
:menuselection:`Account --> SAML settings` don't require anymore that
administrators activate identity providers for user accounts. Therefore, any
user account can use |SAML| for single sign-on. The behavior is the same as for
the |OIDC| capability before through the :program:`Kopano Connect` app.

.. _limitation-password-restriction:

Password restriction
====================

Keycloak offers a password policies feature, see
:cite:t:`keycloak-password-policies`. Because of the user federation with UCS,
see :ref:`app-design-decisions`, Keycloak **doesn't** manage the users
credentials.

UCS takes care of password policy definition and enforcement. For more
information, see :ref:`uv-manual:domain-ldap` in :cite:t:`ucs-manual`.

.. _limitation-application-client:

Application clients
===================

:program:`Keycloak` offers the possibility to create |SAML| or |OIDC| clients
using the command line tool :program:`univention-keycloak`. Administrators can
adjust the generic client configuration, if they need a specific configuration.
In this case you can use the :ref:`Keycloak Admin Console
<keycloak-admin-console>`.
