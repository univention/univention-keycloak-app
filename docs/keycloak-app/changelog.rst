.. SPDX-FileCopyrightText: 2022-2026 Univention GmbH
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

Version 26.6.1-ucs1
===================

Released: 22. Apr 2026

* The app updates to :program:`Keycloak` version 26.6.1:
  https://www.keycloak.org/docs/26.6.1/release_notes/

* The container base image has been updated to include the latest security updates.

* This version fixes the following CVEs:

  * :uv:cve:`2025-14083`
  * :uv:cve:`2026-1002`
  * :uv:cve:`2026-3429`
  * :uv:cve:`2026-3872`
  * :uv:cve:`2026-4282`
  * :uv:cve:`2026-4325`
  * :uv:cve:`2026-4366`
  * :uv:cve:`2026-4633`
  * :uv:cve:`2026-4634`
  * :uv:cve:`2026-4636`

Version 26.5.6-ucs2
===================

Released: 27. Mar 2026

* Version 26.5.6-ucs1 mistakenly contained an older Keycloak version.

* The app updates to :program:`Keycloak` version 26.5.6:
  https://www.keycloak.org/docs/26.5.6/release_notes.

* This version fixes the following CVEs:

  * :uv:cve:`2025-14082`
  * :uv:cve:`2025-14777`
  * :uv:cve:`2026-1035`
  * :uv:cve:`2026-1180`
  * :uv:cve:`2026-2366`
  * :uv:cve:`2026-3121`
  * :uv:cve:`2026-3190`
  * :uv:cve:`2026-3911`

Version 26.5.6-ucs1
===================

Released: 25. Mar 2026

* The app updates to :program:`Keycloak` version 26.5.6:
  https://www.keycloak.org/docs/26.5.6/release_notes

* This version fixes the following CVEs:

  * :uv:cve:`2025-14082`
  * :uv:cve:`2025-14777`
  * :uv:cve:`2026-1035`
  * :uv:cve:`2026-1180`
  * :uv:cve:`2026-2366`
  * :uv:cve:`2026-3121`
  * :uv:cve:`2026-3190`
  * :uv:cve:`2026-3911`

Version 26.5.5-ucs1
===================

Released: 10. Mar 2026

* The app updates to :program:`Keycloak` version 26.5.5:

  * https://www.keycloak.org/docs/26.5.5/release_notes
  * https://www.keycloak.org/2026/03/keycloak-2655-released

* This release of the :program:`Keycloak` app includes log level settings for
  JBoss Logging |SPI| audit events.

* This version fixes the following CVEs:

  * :uv:cve:`2026-2092`
  * :uv:cve:`2026-2603`
  * :uv:cve:`2026-3009`
  * :uv:cve:`2026-3047`

Version 26.5.4-ucs1
===================

Released: 2. Mar 2026

* The app updates to :program:`Keycloak` version 26.5.4:

  * https://www.keycloak.org/docs/26.5.4/release_notes
  * https://www.keycloak.org/2026/02/keycloak-2654-released

* This version fixes the following CVES:

  * :uv:cve:`2025-5416`
  * :uv:cve:`2026-0707`
  * :uv:cve:`2026-1190`
  * :uv:cve:`2026-2575`
  * :uv:cve:`2026-2733`


Version 26.5.3-ucs1
===================

Released: 11. Feb 2026

* The app updates to :program:`Keycloak` version 26.5.3:

  * https://www.keycloak.org/docs/26.5.3/release_notes
  * https://www.keycloak.org/2026/02/keycloak-2653-released

* This version fixes an issue where, in some environments, the Apache
  configuration used for Keycloak interfered with Let’s Encrypt certificate
  validation (ACME HTTP-01 challenges) by incorrectly redirecting required HTTP
  requests.

* This version fixes the following CVEs:

  * :uv:cve:`2025-14778`
  * :uv:cve:`2026-1486`
  * :uv:cve:`2026-1529`
  * :uv:cve:`2026-1609`

Version 26.5.2-ucs2
===================

Released: 30. Jan 2026

* The app updates to :program:`Keycloak` version 26.5.2:

  * https://www.keycloak.org/docs/26.5.2/release_notes
  * https://www.keycloak.org/2026/01/keycloak-2652-released

* This version fixes the following CVEs:

  * :uv:cve:`2025-14082`
  * :uv:cve:`2025-14559`
  * :uv:cve:`2025-66560`
  * :uv:cve:`2025-67735`


Version 26.4.7-ucs1
===================

Released: 10. Dec 2025

* The app updates to :program:`Keycloak` version 26.4.7:

  * https://www.keycloak.org/docs/26.4.7/release_notes
  * https://www.keycloak.org/2025/12/keycloak-2647-released

* This version fixes :uv:cve:`2025-13467`.


Version 26.4.4-ucs1
===================

Released: 18. Nov 2025

* The app updates to :program:`Keycloak` version 26.4.4:

  * https://www.keycloak.org/docs/26.4.4/release_notes
  * https://www.keycloak.org/2025/11/keycloak-2644-released

* This version fixes a bug where LDAP federated users with capital
  letters in their usernames experienced login problems.


Version 26.4.2-ucs1
===================

Released: 05. Nov 2025

* The app updates to :program:`Keycloak` version 26.4.2:

  * https://www.keycloak.org/docs/26.4.2/release_notes
  * https://www.keycloak.org/2025/10/keycloak-2642-released

* This version fixes the following CVEs:

  * :uv:cve:`2025-7962`
  * :uv:cve:`2025-11419`
  * :uv:cve:`2025-11429`
  * :uv:cve:`2025-48924`

Version 26.3.5-ucs1
===================

Released: 14. Oct 2025

* This version updates the ACL that control the access
  to the database password.

* The app updates to :program:`Keycloak` version 26.3.5:

  * https://www.keycloak.org/docs/26.3.5/release_notes
  * https://www.keycloak.org/2025/09/keycloak-2635-released

* This version fixes the following CVEs:

  * :uv:cve:`2025-58056`
  * :uv:cve:`2025-58057`

Version 26.3.3-ucs1
===================

Released: 15. Sep 2025

* The app updates to :program:`Keycloak` version 26.3.3
  (https://www.keycloak.org/docs/26.3.3/release_notes).
  (https://www.keycloak.org/2025/08/keycloak-2633-released).

* This version fixes :uv:cve:`2025-8419`.

Version 26.3.1-ucs1
===================

Released: 14. Aug 2025

* The app updates to :program:`Keycloak` version 26.3.1:

  * https://www.keycloak.org/docs/26.3.1/release_notes
  * https://www.keycloak.org/2025/07/keycloak-2631-released

* This version fixes the following CVEs:

  * :uv:cve:`2025-7365`
  * :uv:cve:`2025-7784`

Version 26.2.5-ucs1
===================

Released: 19. June 2025

* The app updates to :program:`Keycloak` version 26.2.5:

  * https://www.keycloak.org/docs/26.2.0/release_notes
  * https://www.keycloak.org/docs/26.2.5/release_notes

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
