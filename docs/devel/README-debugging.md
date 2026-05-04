## Debugging

In some cases you want to debug Keycloak interactively, for example while developing specific modules or tracking down issues.

### Prerequisites

To build, run, and debug Keycloak, you need the following tools:

1. **Git** revision control system, version 2.11.0 or later.
2. **JDK** (Java Development Kit), version 11 or later.
3. **Maven**, tool for building Java-based projects, version 3.8 or later.
4. **Java Maven compatible IDE**, like IntelliJ IDEA, 2022.3.1 or later.

---

### Quarkus

*Quarkus* is a cloud native, Linux container first framework for writing Java applications. It offers minimal footprint Java applications optimal for running in containers. For more details about *Quarkus*, refer to [https://quarkus.io/](https://quarkus.io/).

#### Build Keycloak core

Build the Keycloak core for version 19.0.1, skipping all tests and examples:

```console
$ git clone git@github.com:keycloak/keycloak.git
$ cd keycloak/quarkus
$ git checkout 19.0.1
$ mvn -f ../pom.xml install -DskipTestsuite -DskipExamples -DskipTests
```

#### Build Quarkus

If building core Keycloak didn't yield any error, you now have the core components ready and can build the *Quarkus* flavor with the following command:

```console
$ mvn clean install -DskipTests
```

#### Run Quarkus

To run the *Quarkus* process from your **IDE**, which allows you to set breakpoints, pause the running application, examine call stack, evaluate variables/expressions, and use other benefits, follow these steps:

1. Use your **IDE** to open the folder where you cloned the Keycloak source code.
2. Update the **Maven** dependencies. Usually, this means right-clicking `pom.xml` and selecting **Maven -> Reload project**.
3. Run the `main` function located in:  
   `quarkus/server/src/main/java/org/keycloak/quarkus/_private/IDELauncher.java`

**Troubleshooting:**

In case of an error in the module `map-hot-rod` in the file `CommonPrimitivesProtoSchemaInitializer.java`:

```text
java: cannot find symbol
symbol:   class CommonPrimitivesProtoSchemaInitializerImpl
location: interface org.keycloak.models.map.storage.hotRod.common.CommonPrimitivesProtoSchemaInitializer
```

It means you have to rebuild the module `map-hot-rod`:

```console
$ cd model/map-hot-rod
$ mvn clean install -DskipExamples -DskipTests
```

Alternatively, you may use your IDE maven plugin to rebuild it. Afterwards, run the `main` function located in `IDELauncher.java` again.
