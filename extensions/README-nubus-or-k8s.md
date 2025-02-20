# Efficient extension development

When developing extensions, we want to quickly run new extension versions
without waiting for a container rebuild and redeployment.

## Build .jar locally

Most syntax errors can already be caught by building the extension jar from source.

To build for example the `univention-authenticator` from source
you can interactively run the maven container in docker,
mount the `extensions` directory
and execute the same commands as the Dockerfile does.

Run the build container:
`docker run --rm -it --volume ./:/app/ maven:3.8.2-openjdk-17 -- bash`

Build the .jar files
```sh
cd /app/extensions
mvn clean package --file udm && mvn install -DskipITs --file udm
mvn clean package --file univention-authenticator && mvn clean package --file pom.xml
```

## Test deployment

Deploy a normal Nubus for Kubernetes. You can disable the multiple LDAP, UDM and Keycloak replicas to simplify the setup.

Modify the keycloak statefulset manifest:
- add `command` to execute sleep as the main container process.
- remove any command `args`.
- remove any `readinessProbe` and `livenessProbe` config so that the container does not get killed by Kubernetes.
```sh
kubectl -n namespace patch statefulset nubus-keycloak --type='json' -p='[
  {"op": "add", "path": "/spec/template/spec/containers/0/command", "value": ["sleep", "infinity"]},
  {"op": "remove", "path": "/spec/template/spec/containers/0/args"}
  {"op": "remove", "path": "/spec/template/spec/containers/0/readinessProbe"},
  {"op": "remove", "path": "/spec/template/spec/containers/0/livenessProbe"}
]'
```

Exec into the keycloak container:
`kubectl exec -it -n namespace  nubus-keycloak-0  -- bash`

Run the keycloak process in the foreground:
```sh
KC_LOG_LEVEL=INFO /opt/keycloak/bin/kc.sh start --features=admin-fine-grained-authz,token-exchange --metrics-enabled=true
```

Copy the jar built in the previous section into the container
```sh
kubectl -n namespace cp extensions/univention-authenticator/target/univention-authenticator-25.0.6-jar-with-dependencies.jar nubus-keycloak-0:/opt/keycloak/providers
```
