.. SPDX-FileCopyrightText: 2022 - 2025 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only


.. _ad-hoc-provisioning:

**************************
Use external user accounts
**************************

Ad hoc provisioning is a capability for Keycloak in the context of Nubus
in the deployments for the UCS appliance and Nubus for Kubernetes,
that allows Keycloak to use user accounts from external IAM systems.

.. warning::

   The ad hoc provisioning is a built-in :program:`Keycloak` feature
   that isn't integrated into the UCS identity management or user lifecycle.
   You need to individually add a more sophisticated integration.

.. versionadded:: 26.1.4-ucs2

   The ad hoc provisioning is a built-in :program:`Keycloak` feature
   that isn't integrated into the UCS identity management or user lifecycle.
   You need to individually add more sophisticated integration.

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

.. _ad-hoc-provisioning-custom-auth-flow:

Create custom authentication flow
=================================

First, you as administrator need to create a custom authentication flow to use
the :program:`univention-authenticator` |SPI|:

#. :ref:`keycloak-admin-console`.

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

.. _ad-hoc-provisioning-create-idp:

Create an identity provider for Microsoft Active Directory
==========================================================

After you created the :ref:`custom authentication flow
<ad-hoc-provisioning-custom-auth-flow>`, Keycloak can use ad hoc provisioning on any
configured federated login. In this section, you learn how to set up a federated
login using a `Microsoft Active Directory Federation Services <ms-adfs_>`_.

To create an identity provider for Active Directory that uses the ad hoc
federation follow the next steps:

#. :ref:`keycloak-admin-console`.

#. Navigate to :menuselection:`UCS realm --> Identity Providers`.

#. Click :guilabel:`Add provider...` and select ``SAML v2.0``.

#. Fill in the fields *Alias* and *Display Name*. You **can't** change the field
   *Alias* later.

#. Fill in the field *Service Provider Entity ID* with the *EntityID* from the
   *Relying Party* on the Active Directory Federation Services.

#. Fill in the field *SAML entity descriptor* with the URL of the SAML metadata from the
   *Relying Party* on the Active Directory Federation Services.

#. Select your authentication flow with the *Univention Authenticator* on the
   *First Login Flow*.

#. Set the *Single Sign-On Service URL* to the single sign-on URL from the
   *Relying Party*.

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
with Univention Corporate Server:

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

.. _ad-hoc-provisioning-adfs-configuration:

Configure Active Directory Federation services for ad hoc provisioning
======================================================================

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

#. Apply and save the rules.
