.. SPDX-FileCopyrightText: 2022-2024 Univention GmbH
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
<https://www.keycloak.org/docs/latest/release_notes>`_.

Version 26.5.2-ucs2
===================

Released: 30. Jan 2025

* The app updates to :program:`Keycloak` version 26.5.2.
  (https://www.keycloak.org/docs/26.5.0/release_notes).
  (https://www.keycloak.org/docs/26.5.2/release_notes).
  (https://www.keycloak.org/2026/01/keycloak-2652-released).

* This version fixes CVE-2025-67735, CVE-2025-66560, CVE-2025-14559, CVE-2025-14082)


Version 26.4.7-ucs1
===================

Released: 10. Dec 2025

* The app updates to :program:`Keycloak` version 26.4.7
  (https://www.keycloak.org/docs/26.4.7/release_notes).
  (https://www.keycloak.org/2025/12/keycloak-2647-released).

* This version fixes CVE-2025-13467.


Version 26.4.4-ucs1
===================

Released: 18. Nov 2025

* The app updates to :program:`Keycloak` version 26.4.4
  (https://www.keycloak.org/docs/26.4.4/release_notes).
  (https://www.keycloak.org/2025/11/keycloak-2644-released).

* This version fixes a bug where LDAP federated users with capital
  letters in their usernames experienced login problems.


Version 26.4.2-ucs1
===================

Released: 05. Nov 2025

* The app updates to :program:`Keycloak` version 26.4.2
  (https://www.keycloak.org/docs/26.4.2/release_notes).
  (https://www.keycloak.org/2025/10/keycloak-2642-released).

* This version fixes CVE-2025-48924, CVE-2025-7962, and CVE-2025-11429,
  CVE-2025-11419.

Version 26.3.5-ucs1
===================

Released: 14. Oct 2025

* This version updates the ACL that control the access
  to the database password.

* The app updates to :program:`Keycloak` version 26.3.5
  (https://www.keycloak.org/docs/26.3.5/release_notes).
  (https://www.keycloak.org/2025/09/keycloak-2635-released).

* This version fixes CVE-2025-58057 and CVE-2025-58056

Version 26.3.3-ucs1
===================

Released: 15. Sep 2025

* The app updates to :program:`Keycloak` version 26.3.3
  (https://www.keycloak.org/docs/26.3.3/release_notes).
  (https://www.keycloak.org/2025/08/keycloak-2633-released).

* This version fixes CVE-2025-8419.

Version 26.3.1-ucs1
===================

Released: 14. Aug 2025

* The app updates to :program:`Keycloak` version 26.3.1
  (https://www.keycloak.org/docs/26.3.1/release_notes).
  (https://www.keycloak.org/2025/07/keycloak-2631-released).

* This version fixes CVE-2025-7365 and CVE-2025-7784.

Version 26.2.5-ucs1
===================

Released: 19. June 2025

* The app updates to :program:`Keycloak` version 26.2.5
  (https://www.keycloak.org/docs/26.2.0/release_notes).
  (https://www.keycloak.org/docs/26.2.5/release_notes).

Version 26.1.4-ucs2
===================

Released: 08. May 2025

* This release of the :program:`Keycloak` app includes again the |SPI| extension for so
  called ad-hoc provisioning.

Version 26.1.4-ucs1
===================

Released: 22. April 2025

* The app updates to :program:`Keycloak` version 26.1.4.

* This version of Keycloak requires higher versions for the database backends:

  * At least version 12 for PostgreSQL.

  * At least version 10.0.4 for MariaDB.

* In the configuration for the LDAP federation Keycloak no longer allows
  ``connectionPooling=true`` together with ``startTLS=true``. The default in
  UCS is now ``connectionPooling=false`` and ``startTLS=true``.

Version 25.0.6-ucs4
===================

Released: 20. December 2024

* Starting with this version the Keycloak app will create a UCR policy for the
  :envvar:`ucs/server/sso/uri` used from UCS 5.2 on to define the default
  |IDP| for services.

Version 25.0.6-ucs3
===================

Released: 26. November 2024

* Security updates for the base docker image of the Keycloak app have been added.



Version 25.0.6-ucs2
===================

Released: 14. November 2024

* The Keycloak App now ships an additional conditional authenticator.
  This authentication flow runs
  authenticators conditionally depending on the client's IP address.
  Administrators can restrict the Kerberos authentication to certain IP address subnetworks
  to prevent pop ups on Microsoft Windows clients that haven't joined the domain.

  For information about the setup, see :ref:`kerberos-authentication-ipaddress`.



Version 25.0.6-ucs1
===================

Released: 09. October 2024

* The Keycloak App has been updated to version 25.0.6

* You can now add additional CA certificates to Keycloak's CA store by
  putting CA certificate files in the ``pem`` format into
  :file:`/var/lib/univention-appcenter/apps/keycloak/conf/ca-certificates`
  on the UCS system. For more information, see :ref:`additional-ca-certificates`.

Version 25.0.1-ucs2
===================

Released: 28. August 2024

* The OIDC consent dialog theme has been improved.

* After a successful password change in the :program:`Keycloak` login flow,
  it could happen that the new password was still not valid on the server
  one was connecting too. This resulted in permission errors.
  The :program:`Keycloak` password change will now redirect to the login page,
  if the password is not valid yet.


Version 25.0.1-ucs1
===================

Released: 15. August 2024

* The Keycloak App has been updated to version 25

* With version 25, :program:`Keycloak` has adjusted the password hashing method.
  The default :program:`Keycloak` admin user will be automatically migrated.
  A downgrade to an older version of :program:`Keycloak` is not advised.


Version 24.0.5-ucs2
===================

Released: 11. July 2024

* Installing Keycloak after establishing an AD-Connection as member in MS AD
  now correctly creates a DNS record

Version 24.0.5-ucs2
===================

Released: 4. July 2024

* Installing Keycloak after establishing an AD-Connection as member in MS AD
  now correctly creates a DNS record

Version 24.0.5-ucs1
===================

Released: 14. June 2024

* The app updates to :program:`Keycloak` version 24.0.5
  (https://www.keycloak.org/docs/24.0.5/release_notes/).

* The Content Security Policy of Keycloak is expanded to allow
  https://login.microsoftonline.com as a frame ancestor. This is needed for
  proper Single Logout from Microsoft 365.

* The FQDN configured for Keycloak is now suggested as and passed to the
  container as lower case. This should fix some problems with mixed case
  domains caused by Keycloak checking its FQDN with case sensitivity.

Version 24.0.3-ucs1
===================

Released: 6. May 2024

* The app updates to :program:`Keycloak` version 24.0.3
  (https://www.keycloak.org/docs/24.0.5/release_notes/#keycloak-24-0-0).

* From this version on :program:`Keycloak` automatically redirects from the
  welcome page to the login page of the *Keycloak Admin Console*.
  The internal docker health check script has been changed to no longer expect
  the welcome page, but instead ask the :program:`Keycloak` health endpoints
  (enabled by the option ``--health-enabled=true``) for the status.

Version 23.0.7-ucs1
===================

Released: 6. April 2024

* The app updates to :program:`Keycloak`  version 23.0.7 of the upstream Docker
  image from https://quay.io/repository/keycloak/keycloak.

* The ad hoc federation feature has been removed from the App due to incompatibility
  with the new :program:`Keycloak` version. If you used this feature in production,
  do not upgrade and contact the support of Univention.

Version 22.0.3-ucs2
===================

Released: 20. December 2023

* Using an Oracle DB backend for :program:`Keycloak` is no longer possible. The Oracle DB
  drivers that were provided by :program:`Keycloak` have been removed. If you are currently
  using an Oracle DB as a backend for :program:`Keycloak`, a migration according to
  ref:`app-database-custom` is necessary to continue using this app.

* The container of the :program:`Keycloak` app has been changed from the upstream `Redhat`
  `ubi-micro-build` to the ucs-base-image, which is based on Debian.

* The :program:`Keycloak` app added support for PostgreSQL 15 databases.

* The error messages shown during login using :program:`Keycloak` have been
  adapted to show more detailed information in case an account is locked, expired or disabled.


Version 22.0.3-ucs1
===================

Released: 27. September 2023

* The app setting :envvar:`keycloak/theme` has been removed. The UCS theme, controlled
  by the UCR variable :envvar:`ucs/web/theme` is now used.

* The :program:`Keycloak` app supports configurable links below the login dialog
  on the login page.

* When opening the login page provided by :program:`Keycloak` for the first
  time, the page shows a cookie banner, if the administrator has configured it.
  Users must accept the cookie banner, otherwise they can't continue to use
  :program:`Keycloak`.

* The app updates to *Keycloak* version 22.0.3 of the upstream Docker image from
  https://quay.io/repository/keycloak/keycloak.

Version 22.0.1-ucs1
===================

Released: 30. August 2023

* The app updates to *Keycloak* version 22.0.1 of the upstream Docker image from
  https://quay.io/repository/keycloak/keycloak.

Version 21.1.2-ucs2
===================

Released: 18. August 2023

* The app can now be configured to restrict access to certain apps
  using group memberships. For more information about the
  configuration of this feature, see :ref:`application-authorization`.

* If the *Keycloak* hostname is accessed using http, you are now
  directly redirected to https

* Due to longer replication times during password updates, it could happen
  that after a successful password update during the *Keycloak* login an
  error was shown. This has been fixed.

Version 21.1.2-ucs1
===================

Released: 19. July 2023

* The app updates to *Keycloak* version 21.1.2 of the upstream Docker image from
  https://quay.io/repository/keycloak/keycloak.

Version 21.1.1-ucs1
===================

Released: 5. July 2023

* The app updates to *Keycloak* version 21.1.1 of the upstream Docker image from
  https://quay.io/repository/keycloak/keycloak. See `release notes for Keycloak
  21.1.0
  <https://www.keycloak.org/docs/latest/release_notes/index.html#keycloak-21-1-0>`_
  for more details.

* The app now configures :program:`Kerberos` ticket authentication through the
  web browser. For more information, see :ref:`kerberos-authentication`.

Version 21.0.1-ucs4
===================

Released: 28. June 2023

* A Base64 *NameID* mapper has been added, to make the
  migration of the Microsoft365 connector to
  :program:`Keycloak` possible.

Version 21.0.1-ucs3
===================

Released: 31. May 2023

* The UCR variable :envvar:`keycloak/apache/config` replaces the variable
  :envvar:`ucs/server/sso/virtualhost`. In case you set
  :envvar:`ucs/server/sso/virtualhost` to ``false`` to turn off the UCS web
  server configuration for :program:`Keycloak`, set
  :envvar:`keycloak/apache/config` to ``true`` before the update.

* The app can use a different URL path for the single sign-on endpoint. For more
  information about the configuration, see :ref:`use-case-reconfigure-sso`.


Version 21.0.1-ucs2
===================

Released: 28. April 2023

* The :program:`Keycloak` app can use an external fully qualified domain name.
  For more information about the configuration, see :ref:`use-case-reconfigure-sso`.

Version 21.0.1-ucs1
===================

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

Version 19.0.2-ucs2
===================

Released: 23. March 2023

* This release of the :program:`Keycloak` app includes extensions for

  #. Univention LDAP mapper
  #. Univention Password reset
  #. Univention Self service

* :program:`Keycloak` now checks the password expiry during the sign-in and
  presents a password change dialog if the password has expired.

* The app now offers a setting to deny the sign-in for unverified, self
  registered user accounts. For more information, see :ref:`use cases <app-use-cases>`.

Version 19.0.1-ucs3
===================

Released: 14. October 2022

* This release of the :program:`Keycloak` app includes an extended version of
  the command line program :program:`univention-keycloak`. Use it to directly
  create Keycloak *Client* configurations for :term:`SAML Service Providers
  <SAML SP>` and :term:`OpenID Connect Relying Parties <OIDC RP>`.

Version 19.0.1-ucs2
===================

Released: 9. September 2022

* This release of the :program:`Keycloak` app includes an |SPI| extension for so
  called ad-hoc federation. See the documentation for details.

* Administrators can install the app :program:`Keycloak` on UCS 5.0-x UCS
  Primary Directory Nodes. For more information, see
  :ref:`limitation-primary-node`.

Version 19.0.1-ucs1
===================

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

Version 18.0.0-ucs1
===================

Released: 28. June 2022

* Initial release of the app.

* Administrators can install the :program:`Keycloak` app on UCS 5.0-x Primary
  Directory Nodes.

* The app uses the upstream Docker image from
  https://quay.io/repository/keycloak/keycloak.
