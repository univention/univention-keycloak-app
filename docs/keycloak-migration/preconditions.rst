.. SPDX-FileCopyrightText: 2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _preconditions:

**************
Pre-conditions
**************

TODO
Before the migration can take place, please make sure:

* Single singe-on is used for at least one service.
* The UCS domain controller primary is on version 5.0-4 with the latest errata
  updates.
* The server where :program:`Keycloak` should be installed is also on
  version 5.0-4 with the latest errata updates.
* If :program:`Keycloak` is already installed, make sure to update to the
  latest version.
