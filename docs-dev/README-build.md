# Building the container image locally

## Prerequisites

- Ensure that you can reach the registry in the `knut` domain. Compare the
  references within the `Dockerfile`.

## Required variables

Source: See the file [`.gitlab-ci.yml`](../.gitlab-ci.yml) regarding the current
default values.

The following environment variables should be set based on the values from the
CI configuration:

- `KEYCLOAK_VERSION`
- `KEYCLOAK_SOURCE`

## Building the image

```shell
wget ${KEYCLOAK_SOURCE}
docker build \
  --build-arg KEYCLOAK_VERSION=${KEYCLOAK_VERSION} \
  --platform=linux/amd64 .
```
