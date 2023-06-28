.. SPDX-FileCopyrightText: 2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _doc-entry:

#########################################################
Migration guide: SimpleSAMLphp/Kopano Konnect to Keycloak
#########################################################

This document explains the required steps for the migration from the apps
:program:`SimpleSAMLphp` (SAML) and :program:`Kopano Konnect` (OIDC) as |IDP| to
the app :program:`Keycloak`.

In future releases of |UCS| the :program:`Keycloak` app will replace
:program:`SimpleSAMLphp` and the :program:`Kopano Konnect` app as the default
identity providers in |UCS|. The reason for this change is that :program:`Keycloak`
has many advantages in terms of functionality, configurability and
maintainability (e.g. Keycloak provides OIDC and SAML endpoints in one
component) over the alternatives.

This documentation is for system administrators who already operate |UCS| 5.0,
make use of the single sign-on features in |UCS| and want to update their
single sign-on configuration to :program:`Keycloak`.

It covers the following topics:

#. :ref:`limitations`
#. :ref:`migration-procedure`
#. :ref:`migration-examples`
#. :ref:`troubleshooting`

.. warning::

   The migration from :program:`SimpleSAMLphp` to :program:`Keycloak` is mandatory.
   :program:`SimpleSAMLphp` is deprecated and planned for removal in a future
   version of |UCS|.

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
   migration-procedure/index
   migration-examples/index
   troubleshooting
   bibliography
