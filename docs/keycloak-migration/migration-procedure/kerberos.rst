.. SPDX-FileCopyrightText: 2023-2024 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _migration-kerberos:

Kerberos
========

This section provides information regarding Kerberos
that you need to consider during the migration procedure.

.. _migration-kerberos-spn:

Add Kerberos SPN to Samba on replicas
-------------------------------------

If you installed :program:`Keycloak`
after you setup :program:`Active Directory-compatible Domain Controller`
on a UCS Replica Directory Node,
for example in UCS\@school environments,
you need to run the command in
:numref:`migration-kerberos-spn-listing`
on the UCS Replicate Directory Node
to ensure that Kerberos authentication works properly.



.. code-block:: console
   :caption: Add Kerberos SPN to Samba Replicas
   :name: migration-kerberos-spn-listing

   $ eval "$(ucr shell)"
   $ samba-tool spn add "HTTP/${keycloak_server_sso_fqdn:-ucs-sso-ng.$domainname}"


.. _migration-kerberos-subnets:

Migrate the Kerberos filter-subnets settings to Keycloak
--------------------------------------------------------

In SimpleSAMLphp, you could restrict the Kerberos authentication to certain IP subnets.
You can add IP subnetworks to the UCR Variable :envvar:`saml/idp/negotiate/filter-subnets`.

.. versionadded:: 25.0.6-ucs2

   Add the filter-subnet settings to Keycloak.

Starting with :program:`Keycloak` version ``25.0.6-ucs2``,
you can limit authentication to select subnetworks, as well.
For information about how to configure it, see FOR THE REFERENCE, SEE THE INLINE COMMENT.

..
   :external+uv-keycloak-app:ref:`kerberos-authentication-ipaddress`.

