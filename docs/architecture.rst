.. _app-architecture:

************
Architecture
************

The :program:`Keycloak` app architecture consists of the following elements:

* The operating environment |UCS| with the App Center and the Docker engine
  running Keycloak.

* The Keycloak software.

* The OpenLDAP LDAP directory in UCS as identity store for Keycloak

* A SQL database as data persistence layer with read-write access for Keycloak.

This architecture view doesn't go into detail of the Keycloak software itself,
because it's beyond the scope of this documentation.

.. _app-architecture-overview:

Overview
========

:numref:`figure-architecture` shows the architecture with the most important
elements. 

.. _figure-architecture:

.. figure:: /images/architecture.*
   :alt: Keycloak app architecture

   Keycloak app architecture

   View focuses on the elements Keycloak, |SAML| and |OIDC| as its most
   important interfaces for single sign-on, and the LDAP directory.

The following list describes the elements in more detail.

.. glossary::

   Keycloak
      *Keycloak* is the Keycloak software as distributed by the Keycloak project as
      container image for Docker. The :program:`Keycloak` app uses the software
      as-is without any changes to the software code.

   LDAP
      *LDAP* is the LDAP directory provided by |UCS| with the OpenLDAP software.
      In UCS it is the storage for all identity and infrastructure data of the
      UCS domain. For more information, see :ref:`uv-manual:domain-ldap` in
      :cite:t:`ucs-manual`.

   SAML IDP
      *SAML IDP* stands for *SAML Identity Provider* and is the |SAML| interface in
      Keycloak that offers user authentication as a service through SAML.

   SAML SP
      *SAML SP* stands for *SAML Service Provider* and is the SAML interface in
      Keycloak that outsources its user authentication function to an *IDP*.

   OIDC IDP
      *OIDC IDP* is short for *OpenID Connect Identity Provider*. In Keycloak this
      |OIDC| interface offers user authentication as a service.

   OIDC RP
      *OIDC RP* is short for *OpenID Connect Relying Party*. In Keycloak this OIDC
      interface outsources its user authentication function to an *IDP*.

.. _app-design-decisions:

Design decisions
================

One goal of the :program:`Keycloak` app is to provide a ready to run Keycloak
setup for |UCS|. To reach that goal, the Univention team made the following
decisions.

.. user federation

The :program:`Keycloak` app configures a user federation in the realm *UCS* in
Keycloak. In general, a user federation synchronizes users from LDAP and Active
Directory servers to Keycloak. In the Keycloak app, the user federation
**doesn't** synchronize user accounts from LDAP to Keycloak. A realm manages a
set of users, credentials, roles, and groups in Keycloak.

The user federation in the realm *UCS* uses the LDAP |DN|
:samp:`uid=sys-idp-user,cn=users,{$ldap_base}` to bind to the LDAP directory in
UCS.

The app registers :samp:`ucs-sso-ng.{$domainname}` to the DNS that serves as
host for API entry points of Keycloak and administrative web interface.
