.. SPDX-FileCopyrightText: 2023-2024 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _doc-entry:

************
Introduction
************

 This documentation is for system administrators
who already operate |UCS| 5.0
and explains the required steps for the migration
from the single sign-on identity provider apps
:program:`SimpleSAMLphp` (SAML)
and :program:`OpenID Connect Provider` to the app :program:`Keycloak`.
The app :program:`OpenID Connect Provider` uses
:program:`Kopano Konnect` to provide OpenID Connect capability to |UCS|.

Notes about UCS 5.2
===================

Starting with |UCS| 5.2
the :program:`Keycloak` app replaces the apps
:program:`SimpleSAMLphp` and :program:`OpenID Connect Provider`
as the default identity providers in |UCS|.
The reason for this change is
that :program:`Keycloak` has many advantages in terms of features,
configurability, and maintainability over the alternatives,
for example, Keycloak provides OIDC and SAML endpoints in one component.

.. warning::

   Migration from :program:`SimpleSAMLphp` to :program:`Keycloak` is mandatory
   before upgrading from |UCS| 5.0 to |UCS| 5.2. :program:`SimpleSAMLphp` and
   :program:`OpenID Connect Provider` are deprecated and will be removed in |UCS| 5.2.

If you use single sign-on for authentication in your |UCS| domain, read this
document, migrate all services to use :program:`Keycloak` as |IDP| and complete
the migration with the steps in :ref:`update-to-ucs-5.2`.

If you are absolutely sure that single sign-on for authentication isn't used
in your |UCS| domain, you can skip the migration part and just prepare your
domain for the update to |UCS| 5.2, following the steps in
:ref:`update-to-ucs-5.2`.

About this document
===================

This document covers the following topics:

#. :ref:`limitations`
#. :ref:`migration-procedure`
#. :ref:`migration-examples`
#. :ref:`troubleshooting`
#. :ref:`update-to-ucs-5.2`

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

