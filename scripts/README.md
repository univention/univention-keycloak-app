# Scritps

## setup_adhoc_provisioning.py

Automates the process of configuring Keycloak for ad-hoc federation through
OIDC with a dummy realm within the same Keycloak instance.

Requires a Nubus deployment with Keycloak and UDM. The Keycloak pod should have
the following environment variables:

```yaml
env:
  - name: KEYCLOAK_FEDERATION_SOURCE_IDENTIFIER
    value: univentionSourceIAM
  - name: KEYCLOAK_FEDERATION_REMOTE_IDENTIFIER
    value: univentionObjectIdentifier
  - name: KC_LOG_LEVEL
    value: DEBUG
```

The last one is optional.

See [setup_adhoc_provisioning.py](./setup_adhoc_provisioning.py) for usage instructions and more details.
