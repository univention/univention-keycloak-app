.. SPDX-FileCopyrightText: 2022-2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _app-changelog:

*********
Changelog
*********

This changelog documents all notable changes to the :program:`Keycloak` app.
`Keep a Changelog <https://keepachangelog.com/en/1.0.0/>`_ is the format and
this project adheres to `Semantic Versioning
<https://semver.org/spec/v2.0.0.html>`_.

Please also consider the `upstream release notes
<https://www.keycloak.org/docs/latest/release_notes/index.html>`_.

22.0.3-ucs1
===========

Released: TODO

* The app setting :envvar:`keycloak/theme` has been removed. The UCS theme, controlled
  by the UCR variable :envvar:`ucs/web/theme` is now used.

* The app updates to *Keycloak* version 22.0.3 of the upstream Docker image from
  https://quay.io/repository/keycloak/keycloak.

22.0.1-ucs1
===========

Released: 30. August 2023

* The app updates to *Keycloak* version 22.0.1 of the upstream Docker image from
  https://quay.io/repository/keycloak/keycloak.

21.1.2-ucs2
===========

Released: 18. August 2023

* The app can now be configured to restrict access to certain apps
  using group memberships. For more information about the
  configuration of this feature, see :ref:`application-authorization`.

* If the *Keycloak* hostname is accessed using http, you are now
  directly redirected to https

* Due to longer replication times during password updates, it could happen
  that after a successful password update during the *Keycloak* login an
  error was shown. This has been fixed.

21.1.2-ucs1
===========

Released: 19. July 2023

* The app updates to *Keycloak* version 21.1.2 of the upstream Docker image from
  https://quay.io/repository/keycloak/keycloak.

21.1.1-ucs1
===========

Released: 5. July 2023

* The app updates to *Keycloak* version 21.1.1 of the upstream Docker image from
  https://quay.io/repository/keycloak/keycloak. See `release notes for Keycloak
  21.1.0
  <https://www.keycloak.org/docs/latest/release_notes/index.html#keycloak-21-1-0>`_
  for more details.

* The app now configures :program:`Kerberos` ticket authentication through the
  web browser. For more information, see :ref:`kerberos-authentication`.

21.0.1-ucs4
===========

Released: 28. June 2023

* A Base64 *NameID* mapper has been added, to make the
  migration of the Microsoft365 connector to
  :program:`Keycloak` possible.

21.0.1-ucs3
===========

Released: 31. May 2023

* The UCR variable :envvar:`keycloak/apache/config` replaces the variable
  :envvar:`ucs/server/sso/virtualhost`. In case you set
  :envvar:`ucs/server/sso/virtualhost` to ``false`` to turn off the UCS web
  server configuration for :program:`Keycloak`, set
  :envvar:`keycloak/apache/config` to ``true`` before the update.

* The app can use a different URL path for the single sign-on endpoint. For more
  information about the configuration, see :ref:`use-case-reconfigure-sso`.


21.0.1-ucs2
===========

Released: 28. April 2023

* The :program:`Keycloak` app can use an external fully qualified domain name.
  For more information about the configuration, see :ref:`use-case-reconfigure-sso`.

21.0.1-ucs1
===========

Released: 19. April 2023

* From this version on the :program:`Keycloak` app requires a CPU that
  supports the micro architecture level ``x86-64-v2``. For more information,
  see :uv:help:`21420`.

* The app updates *Keycloak* to version 21.0.1 of the upstream Docker image from
  `keycloak / keycloak - Quay <https://quay.io/repository/keycloak/keycloak>`_.
  See `release notes for Keycloak 21.0.0
  <https://www.keycloak.org/docs/latest/release_notes/index.html#keycloak-21-0-0>`_
  for more details.

* Accessing the ``userinfo`` endpoint now requires inclusion of ``openid`` in
  the list of requested scopes. For background information, see `this upstream
  issue <https://github.com/keycloak/keycloak/issues/14184>`_.

19.0.2-ucs2
============

Released: 23. March 2023

* This release of the :program:`Keycloak` app includes extensions for

  #. Univention LDAP mapper
  #. Univention Password reset
  #. Univention Self service

* :program:`Keycloak` now checks the password expiry during the sign-in and
  presents a password change dialog if the password has expired.

* The app now offers a setting to deny the sign-in for unverified, self
  registered user accounts. For more information, see :ref:`use cases <app-use-cases>`.

19.0.1-ucs3
============

Released: 14. October 2022

* This release of the :program:`Keycloak` app includes an extended version of
  the command line program :program:`univention-keycloak`. Use it to directly
  create Keycloak *Client* configurations for :term:`SAML Service Providers
  <SAML SP>` and :term:`OpenID Connect Relying Parties <OIDC RP>`.

19.0.1-ucs2
============

Released: 9. September 2022

* This release of the :program:`Keycloak` app includes an |SPI| extension for so
  called ad-hoc federation. See the documentation for details.

* Administrators can install the app :program:`Keycloak` on UCS 5.0-x UCS
  Primary Directory Nodes. For more information, see
  :ref:`limitation-primary-node`.

19.0.1-ucs1
============

Released: 7. September 2022

* The app now offers :program:`univention-keycloak`, a command line program to
  configure :term:`SAML SP` and :term:`OIDC Provider` clients in *Keycloak*
  directly.

  :program:`univention-keycloak` simplifies the integration of client apps with
  *Keycloak* and the downloads of signing certificates for example as PEM file (see
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

* Administrators can install the :program:`Keycloak` app on UCS 5.0-x Primary
  Directory Nodes.

* The app uses the upstream Docker image from
  https://quay.io/repository/keycloak/keycloak.
