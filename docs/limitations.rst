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

Installation on Primary Directory Node
======================================

The App Center installs the :program:`Keycloak` app only on a Primary Directory
Node in your UCS environment, see :ref:`app-installation`. The app is therefore
not suitable for production use in UCS domains that have Backup Directory Nodes.

Use the :program:`Keycloak` app only in a UCS environment without Backup
Directory Nodes, because otherwise:

* Users may encounter sign in problems at the UCS management system on other UCS
  systems.

* Other apps may not be able to authenticate users through SAML without manual
  interaction.

The installation might not break anything in production. But, experiments with
reconfiguration of, for example, UMC and other services so that they use
Keycloak, may have undesired results. In particular, when you change the UCR
variable :envvar:`umc/saml/idp-server` to point to your Keycloak installation.
The LDAP server will not recognize SAML tickets that the *simpleSAMLphp* based
identity provider issued after you restart it. Users will experience
invalidation of their existing sessions.

.. TODO : Discuss with SME:

   * What kind of sign in problems may occur? Can we specify them better?
   * What kind of manual interaction by whom is meant here?

   See https://git.knut.univention.de/univention/ucs/-/issues/1081 and
   https://git.knut.univention.de/univention/ucs/-/issues/994.

.. _limitation-no-user-activation:

No user activation for SAML
===========================

In the *Users* UMC module, the user account's *SAML settings* at
:menuselection:`Account --> SAML settings` don't require anymore that
administrators activate identity providers for user accounts. Therefore, any
user account can use |SAML| for single sign-on. The behavior is the same as for
the |OIDC| capability before through the :program:`Kopano Connect` app.

