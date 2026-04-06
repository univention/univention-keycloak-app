# SPDX-FileCopyrightText: 2023-2026 Univention GmbH
# SPDX-License-Identifier: AGPL-3.0-only

ARG UCS_BASE_IMAGE_TAG=5.2.5-build.20260326@sha256:8381ca70cdad990e82dd44a9cb58a04be22cef60ef50a025a42a7620dcb09a9e
ARG UCS_BASE_IMAGE=gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base

FROM ${UCS_BASE_IMAGE}:${UCS_BASE_IMAGE_TAG} AS base

WORKDIR /app

RUN apt-get --assume-yes --no-install-recommends install \
    ansible-core \
    ca-certificates \
    python3-defusedxml \
    python3-keycloak \
    python3-ldap \
    python3-requests \
    && apt-get autoremove --assume-yes

# Install univention-keycloak scripts from source instead of the Debian package
COPY univention-keycloak/scripts/univention-keycloak /usr/sbin/univention-keycloak
COPY univention-keycloak/scripts/univention-keycloak-migration-status /usr/sbin/univention-keycloak-migration-status
COPY univention-keycloak/scripts/fix-duplicate-uid-mappers /usr/share/univention-keycloak/fix-duplicate-uid-mappers

FROM base

COPY docker/keycloak-bootstrap/bootstrap-ums-keycloak/ /app

CMD ["/app/entrypoint.sh"]
