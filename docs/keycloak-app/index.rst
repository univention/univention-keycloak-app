.. SPDX-FileCopyrightText: 2022-2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _doc-entry:

#####################################
Univention Keycloak app documentation
#####################################

Welcome to the documentation about the Univention :program:`Keycloak` app. The
app installs `Keycloak <keycloak_>`_, an open source software product for single
sign-on with identity and access management. Furthermore, the app adds
authentication to applications and secure services.

This documentation is for system administrators who operate the
:program:`Keycloak` app from Univention App Center connected to the LDAP
directory in Univention Corporate Server (UCS). It covers the following topics:

#. :ref:`app-installation`
#. :ref:`app-update`
#. :ref:`app-configuration`
#. :ref:`app-database`
#. :ref:`app-architecture`
#. :ref:`app-limitations`
#. :ref:`app-use-cases`
#. :ref:`app-troubleshooting`

This documentation doesn't cover the following topics:

* Usage of Keycloak itself, see the :cite:t:`keycloak-docs`.
* Usage of |UCS|, see :cite:t:`ucs-manual`.

To understand this documentation, you need to know the following concepts and
tasks:

* Use and navigate in a remote shell on Debian GNU/Linux derivative Linux
  distributions like |UCS|. For more information, see `Shell and Basic Commands
  <deb-admin-handbook-shell_>`_ from *The Debian Administrator's Handbook*,
  :cite:t:`deb-admin-handbook-shell`.

* :ref:`Manage an app through Univention App Center
  <uv-manual:computers-softwareselection>` in :cite:t:`ucs-manual`.

* Know the concepts of SAML (`Security Assertion Markup Language
  <w-saml_>`_) and OIDC (`OpenID Connect <w-openid-connect_>`_) and the differences
  between the two standards.

Your feedback is welcome and highly appreciated. If you have comments,
suggestions, or criticism, please `send your feedback
<https://www.univention.com/feedback/?keycloak-app=generic>`_ for document
improvement.

.. toctree::
   :maxdepth: 1
   :numbered:
   :hidden:

   installation
   update
   configuration
   database
   architecture
   limitations
   use-cases
   troubleshooting
   changelog
   bibliography
