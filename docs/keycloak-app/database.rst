.. SPDX-FileCopyrightText: 2022-2026 Univention GmbH
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

   Describing the setup of a database cluster,
   for example with :program:`PostgreSQL`
   is beyond the scope of this document.

   Since version 26.8.0, :program:`Keycloak` doesn't support *MariaDB Galera Cluster* anymore.
   For more information,
   see `Galera Cluster considerations <https://www.keycloak.org/server/db#galera-cluster-considerations>`_.

   For recommendations on how to configure the database connection
   of :program:`Keycloak` in production environments,
   see :ref:`app-database-production`.
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
        --set driver="org.mariadb.jdbc.Driver"

#. Re-configure one of the :program:`Keycloak` instances and verify that it works:

   .. code-block:: console

      $ univention-app configure keycloak

#. Re-configure the rest of the :program:`Keycloak` instances.

.. _app-database-production:

Recommendations for production environments
===========================================

The default database connection settings for the :program:`Keycloak` app
work well for a single :program:`Keycloak` instance with a dedicated database.
If several :program:`Keycloak` instances share one database,
or the database server also serves other applications,
you need to adjust some settings to keep the setup stable under load.

This section covers only the settings that need adjustment in such setups.
For details about configuring a database for :program:`Keycloak`,
see the official guide `Configuring the database <https://www.keycloak.org/server/db>`_.

In this section, you configure the settings as environment variables
for the :program:`Keycloak` app container,
as described in :ref:`additional-settings`.
Use the same values on all :program:`Keycloak` instances
that connect to the same database.
On Nubus for UCS, you can use a UCR policy
to distribute the settings to all systems with the :program:`Keycloak` app.

.. _app-database-production-pool:

Database connection pool configuration
--------------------------------------

Each :program:`Keycloak` instance maintains its own pool of database connections.
The following environment variables control the size of the pool:

.. envvar:: KC_DB_POOL_MAX_SIZE

   The maximum number of connections
   that one :program:`Keycloak` instance opens to the database.

   :Default: ``100``

.. envvar:: KC_DB_POOL_MIN_SIZE

   The number of connections that the pool keeps open,
   even if the :program:`Keycloak` app doesn't use them.

   :Default: The pool doesn't keep any idle connections open.

.. envvar:: KC_DB_POOL_INITIAL_SIZE

   The number of connections that the :program:`Keycloak` instance opens at startup.

   :Default: The :program:`Keycloak` app doesn't open any connections in advance.

.. _app-database-production-pool-guidelines:

Database connection pool guidelines
-----------------------------------

Choose the pool size according to the following guidelines.

.. _app-database-production-pool-guidelines-below-connection-limit:

Stay below the connection limit
   The database server accepts only a limited number of connections.
   The ``max_connections`` setting on the database server defines the limit.
   For example, :program:`PostgreSQL` uses ``100`` connections by default.
   :program:`MariaDB` uses ``151`` connections.
   To query the effective value, connect to the database server
   with a SQL client, such as :command:`psql` for :program:`PostgreSQL`
   or :command:`mariadb` for :program:`MariaDB`.
   Then run the command for your database:

   .. tab-set::

      .. tab-item:: PostgreSQL
         :sync: postgres

         .. code-block:: sql
            :caption: Query the connection limit in :program:`PostgreSQL`

            SHOW max_connections;

      .. tab-item:: MariaDB / MySQL
         :sync: mariadb

         .. code-block:: sql
            :caption: Query the connection limit in :program:`MariaDB` and :program:`MySQL`

            SHOW VARIABLES LIKE 'max_connections';

   The combined pool sizes of all :program:`Keycloak` instances
   must never exceed the connection limit of the database.
   Otherwise, the database refuses new connections under load,
   and :program:`Keycloak` fails to process requests.
   Use the following rule to check the combined number of connections.
   Allow for connection slots reserved by the database
   and additional capacity for operational needs.
   The variables in this rule have the following meanings:

   * :math:`n` is the number of :program:`Keycloak` instances.

   * :math:`s` is the value of :envvar:`KC_DB_POOL_MAX_SIZE`.

   * :math:`c` is the number of connections from all other database clients.

   * :math:`r` is the number of reserved slots and additional connections
     for operational needs.

   * :math:`m` is the database connection limit, ``max_connections``.

   .. math::
      :name: app-database-production-pool-guidelines-below-connection-limit-math

      n \times s + c + r \leq m

   Consider the following aspects:

   * Count all :program:`Keycloak` instances that connect to the database.

     .. tab-set::

        .. tab-item:: Nubus for UCS
           :sync: ucs

           On Nubus for UCS, these are all systems with the :program:`Keycloak` app installed.

        .. tab-item:: Nubus for Kubernetes
           :sync: kubernetes

           In Nubus for Kubernetes,
           use the maximum number of replicas that you allow after scaling.
           Consider instances that you might add later.

   * Reserve connections for all other clients of the database server,
     such as other applications, backup jobs, monitoring,
     and administrators.

   * A single :program:`Keycloak` instance
     with the default :envvar:`KC_DB_POOL_MAX_SIZE` value of ``100``
     already exhausts the default connection limit of a :program:`PostgreSQL` server.
     You must reduce the value as soon as you run more than one instance
     on a database with the default connection limit.

   Example
     For three :program:`Keycloak` instances,
     a :program:`PostgreSQL` server with ``max_connections`` set to ``100``
     allows a pool size of ``25`` per instance.
     The three instances then use up to ``75`` connections,
     which leaves ``25`` connections for other clients,
     reserved slots, and additional operational capacity.

.. _app-database-production-pool-guidelines-fixed-size:

Use a database connection pool with a fixed size
   Set :envvar:`KC_DB_POOL_INITIAL_SIZE`, :envvar:`KC_DB_POOL_MIN_SIZE`, and :envvar:`KC_DB_POOL_MAX_SIZE`
   to the same value, as recommended in `Concepts for database connection pools <https://www.keycloak.org/high-availability/multi-cluster/concepts-database-connections>`_.

   With the default settings, the pool starts empty,
   opens connections on demand, and closes them again when idle.
   During peaks, :program:`Keycloak` then has to open many connections at once,
   which delays requests and puts additional load on the database.

   Example
      To configure a pool with a fixed size of ``25`` connections,
      set the following environment variables on each :program:`Keycloak` instance:

      .. tab-set::

         .. tab-item:: Nubus for UCS
            :sync: ucs

            .. code-block:: console
               :caption: Configure a fixed pool size on UCS

               $ ucr set \
                   keycloak/environment/KC_DB_POOL_INITIAL_SIZE=25 \
                   keycloak/environment/KC_DB_POOL_MIN_SIZE=25 \
                   keycloak/environment/KC_DB_POOL_MAX_SIZE=25
               $ univention-app configure keycloak --run-script settings

         .. tab-item:: Nubus for Kubernetes
            :sync: kubernetes

            .. code-block:: yaml
               :caption: Configure a fixed pool size in Nubus for Kubernetes

               keycloak:
                 extraEnvVars:
                   - name: KC_DB_POOL_INITIAL_SIZE
                     value: "25"
                   - name: KC_DB_POOL_MIN_SIZE
                     value: "25"
                   - name: KC_DB_POOL_MAX_SIZE
                     value: "25"

      To find out whether the pool size fits your load,
      monitor the metrics of the database connection pool,
      as described in `Database Metrics <https://www.keycloak.org/observability/metrics-for-troubleshooting-database>`_.
      If ``agroal_awaiting_count`` is regularly greater than ``0``,
      or ``agroal_active_count`` regularly reaches the pool size,
      the pool is too small.
      Increase the pool size only if the database has enough capacity.
      Otherwise, scale the database first.

      For instructions about how to apply the settings,
      see :ref:`additional-settings`.

.. _app-database-production-pool-guidelines-not-too-small:

Don't make the database connection pool too small
   If all connections of the pool are in use,
   requests wait for a free connection.
   If no connection becomes available in time,
   :program:`Keycloak` cancels the request with an error.
   By default, a request waits up to 20 seconds.

   :program:`Keycloak` processes requests with up to 50 worker threads,
   or four times the number of CPU cores, whichever is higher.
   Not every request needs a database connection,
   because :program:`Keycloak` keeps a large part of its data in its cache.
   Therefore, the pool can be considerably smaller than the number of worker threads.
   The :program:`Keycloak` reference deployment
   `Deploying Keycloak for HA with the Operator <https://www.keycloak.org/high-availability/multi-cluster/deploy-keycloak-kubernetes>`_
   uses a pool size of ``30`` per instance,
   which is a reasonable starting point.
