<!--
SPDX-FileCopyrightText: 2023 Univention GmbH
SPDX-License-Identifier: AGPL-3.0-only
-->

# Univention Keycloak Bootstrap Container Image

This image is used by the Univention Keycloak Bootstrap Helm Chart to bootstrap the Keycloak that is part of the Univention Management Stack in a K8s deployment.

## Getting Started

The image that is build by this repository's contents should only be used in conjunction with the related Helm Chart.
It is therefore tested with Kubernetes deployments only. Though you can make use of the image in stand alone
container environments for debugging purposes.

**Note**: This setup is not idempotent.

## Contents

### Python script: `univention_keycloak`

The image uses the `univention_keycloak` script to perform an inital bootstrapping of Univention Keycloak in Kubernetes.

The script expects the following files to be available / mounted into the container by the Helm Chart:

- `/etc/ldapread.secret`: The admin secret to access the UMS LDAP server.
- `/etc/keycloak.secret`: The admin secret to access the Keycloak.

### Ansible playbook: `main.yaml`

Ansible is utilized to map the Helm Chart data into CLI calls for the `univention_keycloak` script. The Helm Chart data
is placed in `/app/values.yaml`.

The following actions are supported at the moment:
- Initialize UMS configuration
- Enable 2FA
- Configure additional LDAP mappers
- Configure links on the login page

## Environment variables

The following environment variables are supported:

- `UNIVENTION_KEYCLOAK_BOOTSTRAP_DEBUG_PAUSE_BEFORE_SCRIPT_START`: When set to a positiv integer value it will pause the execution of the `entrypoint.sh` script which can be convenient in debugging scenarios.

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## License

This project uses the following license: AGPL-3.0-only

## Copyright

Copyright © 2023 Univention GmbH
