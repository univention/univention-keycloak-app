.. SPDX-FileCopyrightText: 2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _migration-procedure:

*******************
Migration procedure
*******************

This section explains the steps to migrate the UCS domain and connected services
from :program:`SimpleSAMLPHP` for SAML and :program:`Kopano Connect` for OIDC to
:program:`Keycloak`. This includes the installation and configuration of
:program:`Keycloak` and the configuration of SAML and OIDC services.

Installation of Keycloak
========================

TODO

Single sing-on between Keycloak and SimpleSAMLPHP (optional)
=============================================================

TODO

Configure SAML/OIDC clients in Keycloak for all services
========================================================

TODO

Configure services to use Keycloak
==================================

TODO

Validate services and troubleshooting
=====================================

TODO, maybe link to Keycloak doc for troubleshooting

Remove SimpleSAMLPHP (TODO)
======================================

TODO to be discussed, how do make clear that the migration has taken place,
remove :program:`univention-saml`?
