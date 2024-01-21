.. SPDX-FileCopyrightText: 2023-2024 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _migration-prerequisites:

Prerequisites
=============

Before the migration can take place, verify to comply to the following
prerequisites:

* At least one service uses single sign-on.

* The UCS Primary Directory Node is on UCS version 5.0-4 with the latest errata
  updates.

* The servers where you want to install :program:`Keycloak` must also be on UCS
  version 5.0-4 with the latest errata updates.

* If you have :program:`Keycloak` already installed, make sure to update to the
  latest app version.
