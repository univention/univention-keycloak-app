
# Keycloak must be configured identically on UCS Primary and Backup Nodes


---

## Context and Problem Statement

Traditionally, we configure and provision distributed services by creating UDM objects for them (e.g. saml/serviceprovider)
and then have UDL modules to write the configuration on each host that runs an instance of that service.

With Keycloak, there are so many settings that can be adjusted in the Keycloak admin interface that we would need a
bidirectional connector between Keycloak and UDM to make this approach work.

Alternatively we should configure Keycloak to share the configuration via the backend database (either MariaDB Galera
cluster or simply the PostgreSQL on the Primary Directory Node) and only do basic initial provisioning for the instance
on the Primary Node (or really: any node will do).

## Considered Options

- PostgreSQL on the Primary Directory Node
- MariaDB Galera cluster

## Decision Outcome

Chosen option: TODO

