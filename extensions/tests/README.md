# Extensions tests

## Development

The project uses Apache Maven Build Lifecycle to manage the build process. You
can learn more about it [here](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html).

## Testing

To run the tests, you can use the `docker-compose.yml` in the `tests` parent
repository with following commands:

```bash
docker-compose up -d udm-rest-api ldap-server
docker compose run --remove-orphans --build test mvn test
```

This will run both the unit and integration tests.

> Note that the unit tests do not need the `udm-rest-api` and `ldap-server` services to be running,
> but the integration tests do.
