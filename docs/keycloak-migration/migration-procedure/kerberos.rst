.. SPDX-FileCopyrightText: 2023-2024 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _migration-kerberos-spn:

Adding Kerberos SPN to Samba on replicas
========================================

If Keycloak is installed after setting up the
:program:`Active Directory-compatible Domain Controller`
app on a replica (such as in UCS\@School environments), you need to run the
following commands on the replica to ensure Kerberos authentication
works properly:

.. code-block:: console

  $ eval "$(ucr shell)"
  $ samba-tool spn add "HTTP/${keycloak_server_sso_fqdn:-ucs-sso-ng.$domainname}"


