.. SPDX-FileCopyrightText: 2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _migration-saml:

Migration of services using SAML for authentication
===================================================

This section gives a general idea about the migration of services that use
:program:`SimpleSAMLphp` for the authentication to :program:`Keycloak` as a SAML
client.

The general approach for the migration includes the following:

* Install the latest version of the :program:`Keycloak` app in the UCS domain.

* Get an overview of all the services that use :program:`SimpleSAMLphp` and
  their settings.

* Check and create attribute mappers for LDAP. Selected LDAP attributes
  become available to :program:`Keycloak`.

* Create a :term:`SAML SP`, the client, and necessary attribute mappers in
  :program:`Keycloak` for every service that uses :program:`SimpleSAMLphp` as
  :term:`SAML IDP`.

* Change the |SAML| settings in the services to use :program:`Keycloak` as a
  :term:`SAML IDP` and validate the setup.

The following sections explain each step of the migration in detail.

.. _migration-saml-ldap-attribute-mappers:

Create LDAP attribute mappers
-----------------------------

Some services require specific LDAP attributes, for example, the service
requires the user's email address, which the :term:`SAML IDP` can pass to the
service during authentication. Create appropriate LDAP attribute mappers based
on your needs.

The user object in :program:`Keycloak` doesn't have every attribute available in
the LDAP directory. You can find a list of attributes mapped to
:program:`Keycloak` in :ref:`uv-keycloak-app:ldap-attribute-mapper` of
:cite:t:`ucs-keycloak-doc`.

To check, if the attribute mapping is sufficient for your services, compare the
list with the attributes that :program:`SimpleSAMLphp` reads from the LDAP
directory. To get a complete list of LDAP attributes available to
:program:`SimpleSAMLphp`, run the following command:

.. code-block:: console
   :caption: List IDP LDAP attributes
   :name: list-idp-ldap-attributes

   $ udm saml/idpconfig list | sed -n 's/LdapGetAttributes: //p'

If you find attributes, that don't map to Keycloak, you can create an LDAP
mapper object through the tool :command:`univention-keycloak` or directly in the
:ref:`Keycloak Admin Console <keycloak-admin-console>`. To create a mapping on
the command line, run the following command:

.. code-block:: console
   :caption: Create LDAP attribute mapping
   :name: create-ldap-attribute-mapping

   $ univention-keycloak user-attribute-ldap-mapper create $LDAP_ATTRIBUTE_NAME

.. _migration-saml-create-sp:

Create a |SAML| client in :program:`Keycloak`
----------------------------------------------

Each :term:`SAML SP`, or client, that you have configured for
:program:`SimpleSAMLphp`, requires a corresponding :term:`SAML SP` created in
:program:`Keycloak` with the appropriate settings. Depending on the specific
settings of your :term:`SAML SP`, there are several ways to create a |SAML|
client:

.. tab:: Command line

   To inspect the :program:`SimpleSAMLphp` configuration on the command line,
   run:

   .. code-block:: console
      :caption: List :program:`SimpleSAMLphp` Provider configuration
      :name: list-saml-client-config

      $ udm saml/serviceprovider list

.. tab:: |UMC| module *SAML Identity Provider*

   Through the |UMC| module *SAML Identity Provider*. For a description of each
   LDAP attribute in this object, see
   :ref:`domain-saml-additional-serviceprovider` in :cite:t:`ucs-manual`.

Use one of the following approaches that best suits your needs:

1. If your application provides a metadata XML file for the |SAML| client settings,
   you can create a client in :program:`Keycloak` with the command line tool
   :program:`univention-keycloak` by using either the URL to that XML file or
   the file itself.

   .. code-block:: console
      :caption: Create basic Keycloak client
      :name: create-basic-keycloak-client

      # with URL
      $ univention-keycloak saml/sp create --metadata-url="$URL"

      # with local XML file
      $ univention-keycloak saml/sp create \
        --metadata-url="REPLACE WITH SAML CLIENT ISSUER NAME OR URL" \
        --metadata-file "/path/to/xml"

2. You can transfer each of the |SAML| client settings from the ``saml/serviceprovider`` object
   to the :program:`Keycloak` client.
   The tool :program:`univention-keycloak` has flags and options for each of these settings.

   .. list-table:: Mapping between :program:`SimpleSAMPLphp` settings and :command:`univention-keycloak saml/sp create` options
      :header-rows: 1
      :widths: 19 10 17
      :name: migration-mapping-ssp-keycloak-options

      * - UMC SAML Identity Provider module / UDM attribute name
        - Keycloak GUI
        - ``univention-keycloak saml/sp create`` option

      * - Service provider activation status: ``isActivated``
        - Enabled
        - ``--not-enabled``

      * - Service provider identifier: ``Identifier``
        - Client ID
        - ``--client-id``

      * - Respond to this service provider URL after login: ``AssertionConsumerService``
        - Valid redirect URI. This value is also automatically parsed from a
          metadata XML file.
        - ``--valid-redirect-uris``

      * - Format of *NameID* attribute: ``NameIDFormat``
        - Name ID format
        - ``--name-id-format``

      * - Description of this service provider: ``serviceproviderdescription``
        - Description
        - ``--description``

      * - URL to the service provider's privacy policy: ``privacypolicyURL``
        - Policy URI
        - ``--policy-url``

      * - Respond to this service provider URL after login: ``AssertionConsumerService``
        - Assertion Consumer Service POST Binding URL
        - ``--assertion-consumer-url-post``

      * - Single logout URL for this service provider: ``singleLogoutService``
        - Logout Service POST Binding URL
        - ``--single-logout-service-url-post``

      * - Allow transition of LDAP attributes to the service provider: ``simplesamlAttributes``
        - n/a
        - Not available in Keycloak, only implicitly configured if
          specific attributes are configured to be transitioned to the
          service provider.

      * - Name of the attribute that is used as NameID: ``simplesamlNameIDAttribute``
        - n/a
        - Not available during the creation of the :term:`SAML SP` client.
          Instead another mappings object has to be created for
          :program:`Keycloak`, see :ref:`migration-saml-response`.

      * - List of LDAP attributes to transmit: ``LDAPattributes``
        - n/a
        - Not available during the creation of the :term:`SAML SP` client.
          Instead another mappings object has to be created for
          :program:`Keycloak`, see :ref:`migration-saml-response`.

      * - Value for attribute format field: ``attributesNameFormat``
        - n/a
        - Not available during the creation of the :term:`SAML SP` client.
          Instead another mappings object has to be created for
          :program:`Keycloak`, see :ref:`migration-saml-response`.

3. You can also use the :ref:`Keycloak Admin Console <keycloak-admin-console>`
   to create SAML clients manually or to adjust clients created with
   :samp:`univention-keycloak saml/sp create`. You may want to consult the
   :program:`Keycloak` documentation at
   :cite:t:`keycloak-admin-guide-assembly-managing-clients` about how to create
   clients in Keycloak.

4. Some |SAML| clients may use custom configurations (e.g. with the attribute
   ``rawsimplesamlSPconfig`` of the ``saml/serviceprovider`` object). If you have a
   service configured in that way, please check the
   :file:`/etc/simplesamlphp/metadata.d/*.php` files to get the appropriate settings
   for the client.

.. _migration-saml-response:

Configure user attributes for SAML response
-------------------------------------------

The following listing demonstrates how to create attribute mappings in
:program:`Keycloak` for the ``simplesamlNameIDAttribute`` and
``LDAPattributes`` settings of the ``saml/serviceprovider`` object.
These mappings allow you to configure the unique identifier for the user and
additional user attributes for the |SAML| response.

NameID attribute (``simplesamlNameIDAttribute``)
   To map the attribute to :program:`Keycloak` that uniquely identifies a
   user, create a so-called ``SAML client nameid mapper`` and attach it to the
   :term:`SAML SP`. The table :numref:`migration-saml-response-ssp-mapping`
   shows which :program:`SimpleSAMLphp` settings correspond to which options of
   the command ``univention-keycloak saml-client-nameid-mapper create``.

   .. list-table:: Mapping between :program:`SimpleSAMPLphp` settings and :command:`univention-keycloak saml-client-nameid-mapper create` options
      :header-rows: 1
      :widths: 19 10 17
      :name: migration-saml-response-ssp-mapping

      * - UMC SAML Identity Provider module / UDM attribute name
        - Keycloak GUI
        - ``univention-keycloak saml-client-nameid-mapper create`` option

      * - Name of the attribute used as *NameID*: ``simplesamlNameIDAttribute``
        - User attribute
        - ``--user-attribute``

      * - Format of *NameID* attribute: ``NameIDFormat``
        - Mapper *NameID* format
        - ``--mapper-nameid-format``

   For example, a command might look like one of the following:

   .. code-block:: console
      :caption: Create ``client nameid mapper``
      :name: create-client-nameid-mapper

      $ univention-keycloak saml-client-nameid-mapper create CLIENTID MAPPERNAME \
        --user-attribute uid \
        --mapper-nameid-format urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified

Additional user attributes (``LDAPattributes``)
   Some :term:`SAML SP` require additional attributes. The name of the
   attribute on the user object requires a mapping to the name of the attribute
   in the |SAML| assertion. The table
   :numref:`migration-saml-client-user-attribute-mapper` shows which
   :program:`SimpleSAMLphp` settings correspond to which ``univention-keycloak
   saml-client-user-attribute-mapper create`` options.

   .. list-table:: Mapping between :program:`SimpleSAMLphp` settings and :command:`univention-keycloak saml-client-user-attribute-mapper create` options
      :header-rows: 1
      :widths: 19 10 17
      :name: migration-saml-client-user-attribute-mapper

      * - UMC SAML Identity Provider module / UDM attribute name
        - Keycloak GUI
        - ``univention-keycloak saml-client-nameid-mapper create`` option

      * - Value for attribute format field: ``attributesNameFormat``
        - Name ID
        - ``--attribute-nameformat``

      * - List of LDAP attributes to transmit: ``LDAPattributes``
        - User attribute, SAML attribute name
        - ``--user-attribute`` ``--attribute-name``

   You need to create such a mapper for each attribute listed in the
   :term:`SAML SP` configuration. In :program:`Keycloak`, you find flags
   to specify the name of the attribute to send:

   :``--user-attribute``: is the attribute name present on the :program:`Keycloak` user object.

   :``--attribute-name``: is the |SAML| attribute utilizing the ``urn:oid`` namespace.

   For example, a command might look like one of the following:

   .. code-block:: console
      :caption: Create SAML client user attribute mapper
      :name: create-saml-client-user-attribute-mapper

      $ univention-keycloak saml-client-user-attribute-mapper create CLIENTID MAPPERNAME \
        --user-attribute uid \
        --attribute-name urn:oid:0.9.2342.19200300.100.1.1

.. note::

   To get a list of client IDs for all existing :term:`SAML SP` clients
   :program:`Keycloak`, run:

   .. code-block:: console
      :caption: Get a list of client IDs for all existing SAML SP clients

      $ univention-keycloak saml/sp get

.. _migration-saml-services-use-keycloak:

Configure SAML services to use Keycloak
---------------------------------------

After creating :term:`SAML SP` clients and mappers in :program:`Keycloak`, the
next step is to update the service settings to use :program:`Keycloak` as
|IDP|.

#. Change the |SAML| settings in the services to use :program:`Keycloak` as
   :term:`SAML IDP` and validate the setup. The service needs some information
   about :program:`Keycloak` as the |IDP|. This includes the *Identity Provider
   Metadata URL*, and the public certificate.

   The SAML Identity Provider Metadata endpoint for Keycloak is
   :samp:`https://${sso_url}/realms/ucs/protocol/saml/descriptor`. You can
   obtain :samp:`{sso_url}` from the output of the following command:

   .. code-block:: console
      :caption: Lookup Keycloak base URL
      :name: lookup-keycloak-base-url

      $ sso_url="$(univention-keycloak get-keycloak-base-url)"

#. It's necessary to update the |IDP| public certificate in your |SAML|
   settings. You can obtain the :program:`Keycloak` server certificate in PEM
   format with the following command:

   .. code-block:: console
      :caption: Retrieve public certificate and *Keycloak* base URL
      :name: migration-of-services-using-SAML-keycloak-certificate

      $ univention-keycloak saml/idp/cert get \
         --as-pem \
         --output "/tmp/keycloak.cert"

How and where you need to configure this information in the service itself is
very specific to the service, and this document can't describe it in general
terms. Consult the documentation of the service itself. To get a better idea of
using |SAML| with :program:`Keycloak`, take a look at the
:ref:`migration-example-saml` in the next section.

.. _migration-ucr-variable-differences:

UCR variable differences
------------------------

This section describes the differences between UCR variables when using
:program:`SimpleSAMLphp` (SAML) and :program:`Keycloak`.

Added variables
~~~~~~~~~~~~~~~

:program:`Keycloak` introduces the following UCR variables:

* :envvar:`keycloak/apache/config`
* :envvar:`keycloak/server/sso/path`

Renamed variables
~~~~~~~~~~~~~~~~~

:program:`Keycloak` uses the following variables known to be used for SAML
before:

.. list-table:: UCR variable differences
  :header-rows: 1
  :widths: 6 6
  :name: migration-ucr-variable-differences-table

  * - SAML
    - Keycloak

  * - :envvar:`ucs/server/sso/fqdn`
    - :envvar:`keycloak/server/sso/fqdn`

  * - :envvar:`ucs/server/sso/autoregistraton`
    - :envvar:`keycloak/server/sso/autoregistration`

  * - :envvar:`ucs/server/sso/virtualhost`
    - :envvar:`keycloak/server/sso/virtualhost`

  * - :envvar:`ucs/server/sso/password/change/server`
    - :envvar:`keycloak/password/change/endpoint`

  * - :envvar:`saml/apache2/ssl/ca`
    - :envvar:`keycloak/apache2/ssl/ca`

  * - :envvar:`saml/apache2/ssl/key`
    - :envvar:`keycloak/apache2/ssl/key`

  * - :envvar:`saml/apache2/ssl/certificate`
    - :envvar:`keycloak/apache2/ssl/certificate`

  * - :envvar:`saml/apache2/content-security-policy/.*`
    - :envvar:`keycloak/csp/frame-ancestors`

  * - :envvar:`saml/idp/selfservice/check_email_verification`
    - :envvar:`ucs/self/registration/check_email_verification`

  * - :envvar:`saml/idp/log/level`
    - :envvar:`keycloak/log/level`

  * - :envvar:`saml/idp/selfservice/account-verification/error-title` and ``saml/idp/selfservice/account-verification/error-title/.*``
    - :envvar:`keycloak/login/messages/en/accountNotVerifiedMsg` and :envvar:`keycloak/login/messages/de/accountNotVerifiedMsg`

  * - :envvar:`saml/idp/selfservice/account-verification/error-descr` and ``saml/idp/selfservice/account-verification/error-descr/.*``
    - :envvar:`keycloak/login/messages/en/accessDeniedMsg` and :envvar:`keycloak/login/messages/de/accessDeniedMsg`

No longer supported variables
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

:program:`Keycloak` doesn't support the following UCR variables anymore:

* :envvar:`saml/idp/authsource`
* :envvar:`saml/idp/ldap/debug`
* :envvar:`saml/idp/ldap/get_attribute`
* :envvar:`saml/idp/ldap/user`
* :envvar:`saml/idp/log/debug/enabled`
* :envvar:`saml/idp/negotiate/filter-subnets`
* :envvar:`saml/idp/session-duration`
* :envvar:`saml/idp/show-error-reporting`
* :envvar:`saml/idp/show-errors`
* :envvar:`saml/idp/technicalcontactemail`
* :envvar:`saml/idp/technicalcontactname`
* :envvar:`saml/idp/timezone`
* :envvar:`ucs/server/sso/certificate/download`
* :envvar:`ucs/server/sso/certificate/generation`

:program:`Keycloak` *enables* the following UCR variables with the LDAP
federation:

* :envvar:`saml/idp/ldap/enable_tls`
* :envvar:`saml/idp/negotiate` starting with :program:`Keycloak` app version
  ``22.0.3-ucs1``.

:program:`Keycloak` *sets* the following UCR variables with the LDAP
federation:

* :envvar:`saml/idp/ldap/user`
* :envvar:`saml/idp/ldap/search_attributes`

Not used anymore
~~~~~~~~~~~~~~~~

:program:`Keycloak` doesn't use the following UCR variables anymore and
automatically sets a respective configuration:

* :envvar:`saml/apache2/ssl/certificatechain`
* :envvar:`saml/idp/certificate/certificate`
* :envvar:`saml/idp/certificate/privatekey`
* :envvar:`saml/idp/https`

:program:`Keycloak` redirects to HTTPS automatically.

For cookies, the following UCR variables existed:

* :envvar:`saml/idp/session-cookie/secure`
* :envvar:`saml/idp/session-cookie/samesite`
* :envvar:`saml/idp/language-cookie/secure`
* :envvar:`saml/idp/language-cookie/samesite`

To set the cookie policy for :program:`Keycloak` use the UCR variable
:envvar:`keycloak/cookies/samesite`. For possible values, see
:cite:t:`ucs-keycloak-doc`.

SAML doesn't use the following UCR variables anymore:

* :envvar:`stunnel/debuglevel`
* :envvar:`saml/idp/lookandfeel/theme`

To set the theme in :program:`Keycloak`, use the command :command:`ucr set
ucs/web/theme=dark|light` for :envvar:`ucs/web/theme`.

Use :command:`univention-keycloak saml` to handle the SAML integration done with
the following UCR variables before:

* :envvar:`saml/idp/enableSAML20-IdP`
* :envvar:`saml/idp/entityID`
* :envvar:`saml/idp/entityID/supplement`\ ``/.*``
