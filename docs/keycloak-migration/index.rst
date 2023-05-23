.. SPDX-FileCopyrightText: 2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _doc-entry:

#########################################################
Migration guide: SimpleSAMLPHP/Kopano Connect to Keycloak
#########################################################

TODO DRAFT TODO

Welcome to the documentation about the migration from the
:program:`SimpleSAMLPHP` (SAML) and :program:`Kopano Connect` (OIDC)
identity provider (IDP) to :program:`Keycloak` app.

Starting with |UCS| 5.2 the :program:`Keycloak` app will replace
:program:`SimpleSAMLPHP` and the :program:`Kopano Connect` app as the default
identity providers in |UCS|. The reason for this change is that :program:`Keycloak`
has many advantages in terms of functionality, configurability and
maintainability (e.g. Keycloak provides OIDC and SAML endpoints in one
component) over the alternatives.

This documentation is for system administrators who already operate |UCS| 5.0,
make use of the single sing-on features in |UCS| and want to update their
|UCS| systems to |UCS| 5.2. It covers the following topics:

.. keep in mind
   troubshooting for every section
   how to check/validate
   where can i find info (logfiles)

#. :ref:`limitations`
#. :ref:`preconditions`
#. :ref:`migration-procedure`
#. :ref:`examples`

.. warning::

   The update from UCS 5.0 to 5.2 will only be possible after the migration
   to Keycloak.

This documentation doesn't cover the following topics:

* Detailed information about the usage of the :program:`Keycloak` app,
  see :cite:t:`ucs-keycloak-doc`
* Usage of |UCS|, see :cite:t:`ucs-manual`.

To understand this documentation, you need to know the following concepts and
tasks:

* Use and navigate in a remote shell on Debian GNU/Linux derivative Linux
  distributions like |UCS|. For more information, see `Shell and Basic Commands
  <deb-admin-handbook-shell_>`_ from *The Debian Administrator's Handbook*,
  :cite:t:`deb-admin-handbook-shell`.

* :ref:`app-installation` and :ref:`app-configuration` of the app
  :program:`Keycloak` as described in :cite:t:`ucs-keycloak-doc`.

* Know the concepts of SAML (`Security Assertion Markup Language <w-saml_>`_)
  and OIDC (`OpenID Connect <w-openid-connect_>`_) and the differences between
  the two standards.

Your feedback is welcome and highly appreciated. If you have comments,
suggestions, or criticism, please `send your feedback
<https://www.univention.com/feedback/?keycloak-migration=generic>`_ for document
improvement.

.. toctree::
   :maxdepth: 1
   :numbered:
   :hidden:

   limitations
   preconditions
   migration-procedure
   examples
   bibliography
