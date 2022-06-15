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
#. :ref:`app-configuration`
#. :ref:`app-architecture`
#. :ref:`app-limitations`
#. :ref:`app-troubleshooting`

This documentation doesn't cover the following topics:

* Usage of Keycloak itself, see the `Keycloak documentation <keycloak-docs_>`_.
* Usage of |UCS|, see :ref:`Univention Corporate Server - Manual for users and
  administrators <uv-manual:introduction>`.

To understand this documentation, you need to know the following concepts and
tasks:

* Use and navigate in a remote shell on Debian GNU/Linux derivative Linux
  distributions like |UCS|. For more information, see `Shell and Basic Commands
  <deb-admin-handbook-shell_>`_ from *The Debian Administrator's Handbook*,
  :cite:t:`deb-admin-handbook-shell`.

* :ref:`Manage an app through Univention App Center <uv-manual:computers-softwareselection>`.

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
   configuration/index
   architecture
   limitations
   troubleshooting
   bibliography
