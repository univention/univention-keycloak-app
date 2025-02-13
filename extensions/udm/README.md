# Java Univention Directory Manager Client
This is a Java client for the UDM-REST-API. It is a simple, low-dependency (2),
good test coverage wrapper around the UDM REST API.

Half of the code is there to ensure that the other half works.

## Limitations

Currently, the client only supports the following operations:
    - Create a User
    - Search Users (with pagination)
    - Delete a User

## Dependencies
The project has been initialized using maven.

```bash
mvn archetype:generate \
    -DgroupId=de.univention.udm \
    -DartifactId=udm \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DarchetypeVersion=1.5 \
    -DinteractiveMode=false
```

The dependencies are specified in the `pom.xml`, with the `<scope>test</scope>` tag
for the test dependencies.

## Development

The project uses Apache Maven Build Lifecycle to manage the build process. You
can learn more about it [here](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html).

## Testing

To run the tests, you can use the `docker-compose.yml` in the `tests` parent
repository with following commands:

```bash
docker-compose up -d udm-rest-api ldap-server
docker compose run --remove-orphans --build test mvn verify
```

This will run both the unit and integration tests. You can use `mvn test` to 
only run the unit tests - or `mvn integration-test` to run the integration tests.

> Note that the unit tests do not need the `udm-rest-api` and `ldap-server` services to be running,
> but the integration tests do.
