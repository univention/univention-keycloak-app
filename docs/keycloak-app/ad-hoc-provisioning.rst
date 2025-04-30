.. SPDX-FileCopyrightText: 2022 - 2025 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _ad-hoc-provisioning:

**************************
Use external user accounts
**************************

.. versionadded:: 26.1.4-ucs2

   Ad hoc provisioning is a capability for Keycloak in the context of Nubus
   in the deployments for the UCS appliance and Nubus for Kubernetes,
   that allows Keycloak to use user accounts from external IAM systems.

For ad hoc provisioning, Keycloak relies on federation.
In the context of Keycloak,
federation is the ability to integrate
and authenticate users from external identity providers and IAM systems
as if they're native user accounts within Keycloak.
It allows Keycloak to leverage existing identity systems,
such as Microsoft Azure Active Directory,
or any other OpenID Connect or SAML compliant identity provider.
Federation offers the following benefits:

Single sign-on
   Users can sign in using their credentials from an external IAM system,
   and reducing the need to manage multiple credentials.

Decentralized identity management
   Functional administrators don't have to handle user authentication directly
   by offloading it to trusted external IAM systems.
   In the context of Nubus in the UCS appliance and the Kubernetes deployments,
   it allows to plugin Nubus into existing environments with existing identity providers.

.. warning::

   The ad hoc provisioning is a built-in :program:`Keycloak` feature
   that isn't integrated into the Nubus identity management or user lifecycle.
   You need to individually add a more sophisticated integration.

The :program:`Keycloak` app provides *ad hoc provisioning*
to enable identity brokering and add user accounts to |UCS|
as so-called *shadow accounts*.
It supports the
:ref:`design decision about not having user accounts in Keycloak <app-design-decisions>`.

The :program:`Keycloak` app installs the :program:`univention-authenticator` |SPI| plugin.
The plugin creates the local shadow copy of the user account in the OpenLDAP directory services
through the REST API of |UDM|.
*Ad hoc provisioning* is for administrators and operators
who want to keep track of all users in |UCS|.

This page describes how to configure Keycloak
to use user accounts from external IAM systems.
The instructions below apply to one external IAM system
and focus on Microsoft Active Directory as example.
However, the instructions apply in principle to similar IAM systems
that Keycloak supports for federation setups.
The setup consists of the following steps in the given order:

#. :ref:`ad-hoc-provisioning-import-external-ca`

#. :ref:`ad-hoc-provisioning-custom-auth-flow`

#. :ref:`ad-hoc-provisioning-adfs-configuration`

#. :ref:`ad-hoc-provisioning-create-idp`

#. :ref:`ad-hoc-provisioning-mappers`

.. seealso::

   For more information about identity brokering and first login flow, see
   :cite:t:`keycloak-first-login`.

   For more information on |SPI|, see :cite:t:`keycloak-spi`.

.. _ad-hoc-provisioning-import-external-ca:

Import external CA certificates
===============================

Federation involves other, for example external, server systems and requires trust.
Certificates are a way to implement trust.
To tell your Keycloak system to trust another system for the ad hoc provisioning,
you need to import the CA certificate for that system.
Keycloak needs the CA certificate
to verify the encrypted connection with the other system.

For more information and the steps for adding the CA certificate,
see :ref:`additional-ca-certificates`.

.. _ad-hoc-provisioning-admin-console-sign-in:

Sign in to *Keycloak Admin Console*
===================================

You perform the steps described in this section and the followings sections
in the *Keycloak Admin Console*.
The URL depends on the deployment of your Nubus installation.

.. tab:: Nubus for UCS appliance

   Nubus for UCS appliance is an environment with Nubus on Univention Corporate Server (UCS).
   For ad hoc provisioning with Keycloak,
   you use the :program:`Keycloak` app from the App Center.

   Administrators in the UCS appliance installation follow the steps described in :ref:`keycloak-admin-console`.

.. tab:: Nubus for Kubernetes

   Nubus for Kubernetes is an environment Nubus installed in a Kubernetes cluster.
   It includes :program:`Keycloak` as identity provider.

   Operators in the Nubus for Kubernetes installation follow the steps described in
   :external+uv-nubus-kubernetes-operation:ref:`conf-ad-hoc-provisioning`.

.. _ad-hoc-provisioning-custom-auth-flow:

Create custom authentication flow
=================================

Authentication flows are workflows
that a user performs when interacting with certain aspects of the environment.
A custom authentication flow is a sequence of steps
that define how Keycloak authenticates users.
Unlike predefined authentication flows,
custom flows include specific authenticators, requirements, and conditions.

:program:`univention-authenticator` is such a specific authenticator.
And to use it during the sign-in procedure,
you need to create a custom authentication flow,
as described in the following steps:

#. :ref:`ad-hoc-provisioning-admin-console-sign-in`.

#. Navigate to :menuselection:`UCS realm --> Authentication`.

#. Select ``First Broker Login`` in the list and click :guilabel:`Copy`.

#. Give a name to the authentication flow and click :guilabel:`OK`.

#. In the *Review Profile (review profile config)* click :guilabel:`Actions` and
   select ``Config``.

#. Select ``Off`` in the list, click :guilabel:`Save` and navigate back to
   the authentication flow.

#. In the authentication flow, click :guilabel:`Add execution` to open the *Create Authenticator Execution* page.

#. Select ``Univention Authenticator`` in the list and click :guilabel:`Save`.

#. On the *Flows* tab in the *Authentication* section, change the *Univention
   Authenticator* in the displayed table to ``Required``.

#. To finish the configuration, click :guilabel:`Actions` in the *Univention
   Authenticator* and select ``Config``.

#. Fill in the following configuration options for the *Univention
   Authenticator*:

   :Alias: Name of the configuration.

   :UDM REST API endpoint: The API endpoint of UDM where UCS stores the shadow copy of the user.

   :Username: Username of a user account with write permissions to UDM.

   :Password: Password of that user account with write permissions to UDM.

#. Click :guilabel:`Save`.

.. seealso::

   Authentication flows
      in :cite:t:`keycloak-auth-flow`
      for more information about authentication flows.

.. _ad-hoc-provisioning-adfs-configuration:

Configure Active Directory Federation Services for ad hoc provisioning
======================================================================

Keycloak needs a federation with the external IAM system.
*Active Directory Federation Service* adds the needed federation capability
to Active Directory using SAML and OpenID Connect.

To configure the Active Directory Federation Services to properly work with ad
hoc federation you need to configure it with the following steps:

#. Sign in as ``Administrator`` in *Active Directory Federation Services*.

#. Open *Relying Party Trust* and click :guilabel:`Add Relying Party Trust`.

#. Select ``Claim aware`` and click :guilabel:`Start`.

#. On the *Select Data Source* page, select ``Import data about the relying
   party published online or on a local network``.

#. In the *Federation metadata address* field insert the metadata URL:
   :samp:`https://ucs-sso-ng.$(ucr get domainname)/auth/realms/ucs/broker/{SAML
   IDP name}/endpoint/descriptor`.

#. Specify a *Display Name*. Click :guilabel:`Next`.

#. Select your wanted *Access Control Policy*. Click :guilabel:`Next`.

#. Review your final configuration and click :guilabel:`Next`.

#. Click :guilabel:`Close`.

#. Add the claims to the ticket.

   ``objectGUID``
      #. Click :guilabel:`Add rule` and select ``Send LDAP Attributes as Claims``.

      #. Add a claim for ``objectGUID`` to the ticket:

         :Claim Rule name: Name of the claim
         :Attribute Store: ``Active Directory``
         :LDAP attribute: ``objectGUID``
         :Outgoing Claim Type: ``objectGUID``

   ``sAMAccountName``
      #. Click :guilabel:`Add rule` and select ``Send LDAP Attributes as Claims``.

      #. Add a claim for ``sAMAccountName`` to the ticket:

         :Claim Rule name: Name of the claim
         :Attribute Store: ``Active Directory``
         :LDAP attribute: ``SAM-Account-Name``
         :Outgoing Claim Type: ``sAMAccountName``

   Email address
      #. Click :guilabel:`Add rule` and select ``Send LDAP Attributes as Claims``.

      #. Add a claim for the email address to the ticket:

         :Claim Rule name: Name of the claim
         :Attribute Store: ``Active Directory``
         :LDAP attribute: ``E-mail Addresses``
         :Outgoing Claim Type: ``E-mail Address``

   Given name
      #. Click :guilabel:`Add rule` and select ``Send LDAP Attributes as Claims``.

      #. Add a claim for the given name to the ticket:

         :Claim Rule name: Name of the claim
         :Attribute Store: ``Active Directory``
         :LDAP attribute: ``Given-Name``
         :Outgoing Claim Type: ``Given Name``

   Surname
      #. Click :guilabel:`Add rule` and select ``Send LDAP Attributes as Claims``.

      #. Add a claim for the surname to the ticket:

         :Claim Rule name: Name of the Claim
         :Attribute Store: ``Active Directory``
         :LDAP attribute: ``Surname``
         :Outgoing Claim Type: ``Surname``

#. Click :guilabel:`OK` to apply and save the rules.

.. _ad-hoc-provisioning-create-idp:

Create an identity provider for Microsoft Active Directory
==========================================================

After you created the :ref:`custom authentication flow <ad-hoc-provisioning-custom-auth-flow>`,
Keycloak can use ad hoc provisioning on any configured federated login.
In this section, you learn how to set up a federated login
using a `Microsoft Active Directory Federation Services <ms-adfs_>`_.

To create an identity provider for Active Directory
that uses the ad hoc provisioning follow the next steps:

#. :ref:`ad-hoc-provisioning-admin-console-sign-in`.

#. Navigate to :menuselection:`UCS realm --> Identity Providers`.

#. Click :guilabel:`Add provider...` and select ``SAML v2.0``.

#. Fill in the fields *Alias* and *Display Name*. You **can't** change the field
   *Alias* later.

#. Fill in the field *Service Provider Entity ID* with the *EntityID* from the
   *Relying Party* on the Active Directory Federation Services.
   The *Service Provider Entity ID* can have any value.
   You use it to describe the SAML service provider.
   It usually looks similar to the entity descriptor.

#. Fill in the field *SAML entity descriptor* with the URL of the SAML metadata from the
   *Relying Party* on the Active Directory Federation Services.

   In Microsoft Active Directory Federation Service,
   you find it at :menuselection:`AD FS --> Service --> Endpoints --> Metadata`.

   Example:
      ``https://ad.example.com/FederationMetadata/2007-06/FederationMetadata.xml``

#. Select your authentication flow with the *Univention Authenticator* on the
   *First Login Flow*.

#. Set the *Single Sign-On Service URL*
   to the single sign-on URL from the *Relying Party*.
   Keycloak should automatically detect it from the metadata.
   In case the automatic detection didn't work,
   the service URL looks like
   :numref:`ad-hoc-provisioning-create-idp-sso-service-listing`
   in the SAML metadata.

   .. code-block:: xml
      :caption: Example for SAML metadata with the Single sign-on service URL
      :name: ad-hoc-provisioning-create-idp-sso-service-listing

      <SingleSignOnService
          Binding="urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect"
          Location="https://ad.example.com/adfs/ls/"/>


#. In *Principal Type* select ``Unspecified`` in the fields *NameID Policy
   Format*, *Attribute [Name]*.

   In *Principal Attribute* select ``sAMAccountName``.

#. Enable the following properties:

   * ``Allow Create``

   * ``HTTP-POST Binding Response``

   * ``HTTP-POST Binding for AuthnRequest``

   * ``Want AuthnRequests Signed``

#. For the field *Signature Algorithm* select ``RSA_SHA256``.

   For the field *SAML Signature Key Name* select ``CERT_SUBJECT``.

#. Enable *Validate Signature* and add the certificate to *Validating x509
   Certificates*.

#. Click :guilabel:`Save`.

.. _ad-hoc-provisioning-mappers:

Mappers for the identity provider
=================================

The identity provider needs the following mapper configuration to work properly
with Nubus in the UCS appliance and the Kubernetes deployments:

#. :ref:`ad-hoc-provisioning-admin-console-sign-in`.

#. To create a mapper in the identity provider configuration navigate to
   :menuselection:`UCS realm --> Identity Provider --> Your Identity Provider
   --> Mappers`.

#. Click :guilabel:`Create`

#. Configure the mapper for the email address with the following properties:

   :Name: Name of the mapper
   :Sync Mode Override: ``import``
   :Type of mapper: ``Attribute Importer``
   :Attribute Name: ``http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress``
   :User Attribute Name: ``email``


#. Configure the mapper for the first name with the following properties:

   :Name: Name of the mapper
   :Sync Mode Override: ``import``
   :Type of mapper: ``Attribute Importer``
   :Attribute Name: ``http://schemas.xmlsoap.org/ws/2005/05/identity/claims/givenname``
   :User Attribute Name: ``firstName``

#. Configure the mapper for the last name with the following properties:

   :Name: Name of the mapper
   :Sync Mode Override: ``import``
   :Type of mapper: ``Attribute Importer``
   :Attribute Name: ``http://schemas.xmlsoap.org/ws/2005/05/identity/claims/surname``
   :User Attribute Name: ``lastName``

#. Configure the mapper for ``univentionObjectIdentifier`` with the following properties:

   :Name: Name of the mapper
   :Sync Mode Override: ``import``
   :Type of mapper: ``Attribute Importer``
   :User attribute: ``objectGuid``
   :User attribute Name: ``univentionObjectIdentifier``

#. Configure the mapper for ``univentionSourceIAM`` with the following properties:

   :Name: Name of the mapper
   :Sync Mode Override: ``import``
   :Type of mapper: ``Hardcoded attribute``
   :User attribute: ``univentionSourceIAM``
   :User attribute value: Value from the *Alias* field of the identity provider, as configured in Keycloak.

#. Configure the mapper for ``external-${ALIAS}-${ATTRIBUTE.sAMAccountName}``
   with the following properties:

   :Name: Name of the mapper
   :Sync Mode Override: ``import``
   :Type of mapper: ``Username Template Importer``
   :User attribute: ``external-${ALIAS}-${ATTRIBUTE.sAMAccountName}``
   :Target: ``LOCAL``
