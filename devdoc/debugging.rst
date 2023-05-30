.. _keycloak-debugging:

*********
Debugging
*********

.. highlight:: console

In some cases you want to debug :program:`Keycloak` interactively,
for example while developing specific modules or tracking down issues.

.. _debugging-prerequisites:

Prerequisites
=============

To build and run and debug :program:`Keycloak`, you need the following tools:

#. `Git <https://git-scm.com/downloads>`_ revision control system, version
   2.11.0 or later.

#. The Java Development Kit, `JDK <https://jdk.java.net/archive/>`_, version 11
   or later.

#. `Maven <https://archive.apache.org/dist/maven/maven-3/>`_, tool for building
   Java-based projects, version 3.8 or later.

#. Java Maven compatible |IDE|, like `IntelliJ IDEA <https://www.jetbrains.com/idea/>`_, 2022.3.1 or later.

.. _quarkus:

*Quarkus*
=========

*Quarkus* is a cloud native, Linux container first framework for writing Java
applications. It offers minimal footprint Java applications optimal for running
in containers. More details about *Quarkus*, refer to https://quarkus.io/.

.. _build-keycloak-core:

Build *Keycloak* core
---------------------

Build the *Keycloak* core for version 19.0.1, skip all tests and examples:

.. code-block::

   $ git clone git@github.com:keycloak/keycloak.git
   $ cd keycloak/quarkus
   $ git checkout 19.0.1
   $ mvn -f ../pom.xml install -DskipTestsuite -DskipExamples -DskipTests

.. _build-quarkus:

Build *Quarkus*
---------------

If building core *Keycloak* didn't yield any error, you now have the core
components ready and can build the *Quarkus* flavor with the following command:

.. code-block::

   $ mvn clean install -DskipTests

.. _run-quarkus:

Run *Quarkus*
-------------

To run *Quarkus* process from your |IDE|, which in turn gives you possibility to
set breakpoints, pause the running application, examine call stack, evaluate
variables and expressions and use other benefits, follow these steps:

#. Use your |IDE|, open the folder where you cloned the *Keycloak* source code.

#. Update the *Maven* dependencies. Usually this means to right click
   :file:`pom.xml` :menuselection:`Maven --> Reload project`.

#. Run the function ``main`` located in
   :file:`quarkus/server/src/main/java/org/keycloak/quarkus/_private/IDELauncher.java`.


In case of an error in the module ``map-hot-rod`` in the file
:file:`CommonPrimitivesProtoSchemaInitializer.java`:

.. code-block::

   java: cannot find symbol
   symbol:   class CommonPrimitivesProtoSchemaInitializerImpl
   location: interface org.keycloak.models.map.storage.hotRod.common.CommonPrimitivesProtoSchemaInitializer

It means you have to rebuild the module ``map-hot-rod``:

.. code-block::

   $ cd model/map-hot-rod
   $ mvn clean install -DskipExamples -DskipTests

Alternatively, you may use your |IDE| maven plugin to rebuild it. Afterwards,
run again the ``main`` function located in
:file:`quarkus/server/src/main/java/org/keycloak/quarkus/_private/IDELauncher.java`.
