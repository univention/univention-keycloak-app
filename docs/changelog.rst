.. _app-changelog:

*********
Changelog
*********

This changelog documents all notable changes to the Keycloak app. `Keep a
Changelog <https://keepachangelog.com/en/1.0.0/>`_ is the format and this
project adheres to `Semantic Versioning <https://semver.org/spec/v2.0.0.html>`_.

19.0.2-ucs2
============

Released: 23. March 2023

* This release of Keycloak app includes extensions for

  #. Univention LDAP mapper
  #. Univention Password reset
  #. Univention Self service

* :program:`Keycloak` now checks the password expiry during the login and
  presents a password change dialog if the password is expired.
* The app now offers a setting to deny the login for unverified, self
  registered user accounts. For more information, see :ref:`use cases <app-use-cases>`.

19.0.1-ucs3
============

Released: 14. October 2022

* This release of Keycloak includes an extended version of the command line
  program :program:`univention-keycloak`. Use it to directly create Keycloak
  *Client* configurations for :term:`SAML Service Providers <SAML SP>` and
  :term:`OpenID Connect Relying Parties <OIDC RP>`.

19.0.1-ucs2
============

Released: 9. September 2022

* This release of :program:`Keycloak` includes an |SPI| extension for so called
  ad-hoc federation. See the documentation for details.

* Administrators can install the app :program:`Keycloak` on UCS 5.0-x UCS
  Primary Directory Nodes. For more information, see
  :ref:`limitation-primary-node`.

19.0.1-ucs1
============

Released: 7. September 2022

* The app now offers :program:`univention-keycloak`, a command line program to
  configure :term:`SAML SP` and :term:`OIDC Provider` clients in Keycloak
  directly.

  :program:`univention-keycloak` simplifies the integration of client apps with
  Keycloak and the downloads of signing certificates for example as PEM file (see
  option groups ``saml/idp/cert`` or ``oidc/op/cert``).

* :program:`univention-keycloak` supports the setup of a |2FA| authentication
  flow for the members of a specific LDAP group. The second factor is a
  time-based one-time password (TOTP) in this case.

* The app updates to *Keycloak* version 19.0.1 of the upstream Docker image from
  https://quay.io/repository/keycloak/keycloak.

* Administrators can install the app :program:`Keycloak` on UCS 5.0-x UCS
  Primary Directory Nodes. For more information, see
  :ref:`limitation-primary-node`.

18.0.0-ucs1
============

Released: 28. June 2022

* Initial release of the app.

* Administrators can install the Keycloak app on UCS 5.0-x Primary Directory
  Nodes.

* The app uses the upstream Docker image from
  https://quay.io/repository/keycloak/keycloak.
