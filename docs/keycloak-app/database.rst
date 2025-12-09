.. SPDX-FileCopyrightText: 2022-2024 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _app-database:

**********************
Database configuration
**********************

:program:`Keycloak` uses an external database to store configurations and settings.
By default, the :program:`Keycloak` app installs and configures a :program:`PostgreSQL` database.
In case you want to use your own database backend,
you can change the database settings for :program:`Keycloak` prior or after the app installation.

.. _app-database-default:

Default database
================

The :program:`Keycloak` app installation procedure
automatically installs and configures the :program:`PostgreSQL` database
during the initial installation of the app in the UCS domain.
:program:`Keycloak` uses this database for all additional installations of the app in the UCS domain.
The default :program:`PostgreSQL` database on UCS
doesn't configure high availability, load balancing, and replication.

However, it's not mandatory to use the default :program:`PostgreSQL` database instance.
Administrators may decide to use another one, for example,
if there is a need to use an already existing or clustered database.

.. tip::
   If you need database replication and failover,
   you must use a database cluster setup.
   Then, you configure the database connection
   in the :program:`Keycloak` app,
   as described in :ref:`app-database-change-before`,
   to use the database cluster instead of the local single database instance.

   Examples:
      For :program:`PostgreSQL` you need to set up your own database cluster
      and cover the topics high availability, load balancing, and replication.
      Describing this setup is beyond the scope of this document.

      For :program:`MariaDB` you need to set up a *MariaDB Galera Cluster*.
      Describing this setup is beyond the scope of this document.

.. _app-database-custom:

Custom database
===============

:program:`Keycloak` supports a wide range of different databases as backend.
For detailed information,
see `Supported databases <https://www.keycloak.org/server/db>`_
in :cite:t:`keycloak-db`.

.. note::

   On the contrary to the official :program:`Keycloak` container image,
   the custom image that the :program:`Keycloak` App provides,
   doesn't contain the drivers that support an Oracle Database.
   Using an Oracle Database as a backend for :program:`Keycloak`
   isn't supported.

The :program:`Keycloak` app provides app settings
for the configuration of the database backend.
For the available settings, see the :ref:`app-settings`
section.

.. important::

   Changing these settings doesn't affect the database itself, no matter if you
   use the command line tools or the *App Center*. The database settings only
   tell :program:`Keycloak` where and how to connect to the database. Ensure
   that you first perform the needed changes on the database itself.

.. _app-database-change-before:

Changing the database configuration
===================================

The following sections explain how to change the database settings. The example
uses the :program:`MariaDB` database and the following assumptions:

* The database for Keycloak exists.

* The :program:`Keycloak` server can connect to the database.

* A user account with the appropriate permissions for the database exists.

.. note::

   The database user needs the following minimum privileges to work in a single
   machine setup. Use the `GRANT command <mariadb-grant_>`_:

   .. code-block:: sql

      GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, REFERENCES, INDEX, DROP
      ON `<database>`.* TO `<user>`@`<host>`;

.. _app-database-change-before-installation:

Initial installation
--------------------

This section explains how to setup the :program:`Keycloak` app to use a
different database, such as :program:`MariaDB` in this example, during the
initial and first installation of the :program:`Keycloak` app in the UCS domain.

To specify an existing MariaDB database during the initial installation, you can
either run the following installation command from the command line:

.. code-block:: console
   :caption: Install Keycloak with alternative database settings

   $ univention-app install keycloak --set \
       kc/db/url="jdbc:mariadb://database-server:3306/database-name" \
       kc/db/password="database-password" \
       kc/db/username="database-username"

Or alternatively, you can set the corresponding app settings
:envvar:`kc/db/url`, :envvar:`kc/db/password` and :envvar:`kc/db/username`
during the installation in the *Univention App Center*.

Additional installations of the :program:`Keycloak` app automatically use
these database settings without any further database configuration.

.. _app-database-change-after-installation:

After initial installation
--------------------------

After you completed the app installation in the UCS domain, :program:`Keycloak`
stores the database settings in a domain wide settings object. Subsequent
installations of the :program:`Keycloak` app use these settings, regardless of
the database settings during the installation.

.. warning::

   Changing the database settings after the installations means losing every
   existing configuration settings and session.

   You have to manually backup :program:`Keycloak` before and restore the
   settings after changing the database backend. For more information, see
   :ref:`backup-and-restore`.

To change the database settings for existing :program:`Keycloak` instances you
have to use the following steps:

#. Change the domain wide database settings with the following command on one of
   the UCS systems that has :program:`Keycloak` installed:

   .. code-block:: console

      $ univention-keycloak domain-config \
        --set username="database-username" \
        --set uri="jdbc:mariadb://database-server:3306/database-name" \
        --set password="database-password" \
        --set driver="org.mariadb.jdbc.Driver" \
        --set ping_datatype="VARBINARY(255)"

#. Re-configure one of the :program:`Keycloak` instances and verify that it works:

   .. code-block:: console

      $ univention-app configure keycloak

#. Re-configure the rest of the :program:`Keycloak` instances.
