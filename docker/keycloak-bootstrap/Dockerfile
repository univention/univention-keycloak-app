# SPDX-FileCopyrightText: 2023 Univention GmbH
# SPDX-License-Identifier: AGPL-3.0-only

ARG UCS_BASE_IMAGE_TAG=5.2.3-build.20251016@sha256:779104f80174bea204d5e48de723302320bf73e6ef58a0e200ff77cc87ded4c2
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
