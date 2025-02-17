# Scritps

## setup_adhoc_provisioning.py

Automates the process of configuring Keycloak for ad-hoc federation through
OIDC with a dummy realm within the same Keycloak instance.

Requires a Nubus deployment with Keycloak and UDM. You may add the following 
environment variable to the Keycloak deployment to enable debug logging:

```yaml
keycloak:
  config:
    logLevel: "DEBUG"
```

See [setup_adhoc_provisioning.py](./setup_adhoc_provisioning.py) for usage instructions and more details.
