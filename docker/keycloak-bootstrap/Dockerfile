# SPDX-FileCopyrightText: 2023-2026 Univention GmbH
# SPDX-License-Identifier: AGPL-3.0-only

ARG UCS_BASE_IMAGE_TAG=5.2.5-build.20260326@sha256:21ff2cc6a9676e48258ffad624962e55293d0a9e7ba471d170da3bb9563c6e1c
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
    univention-keycloak-client \
    && apt-get autoremove --assume-yes

FROM base

COPY bootstrap-ums-keycloak/ /app

CMD ["/app/entrypoint.sh"]
