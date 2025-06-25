# SPDX-FileCopyrightText: 2023 Univention GmbH
# SPDX-License-Identifier: AGPL-3.0-only

ARG UCS_BASE_IMAGE_TAG=0.18.2-build-2025-06-12
ARG UCS_BASE_IMAGE=gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base-522

FROM ${UCS_BASE_IMAGE}:${UCS_BASE_IMAGE_TAG} AS base

WORKDIR /app

# FIXME: Get the latest univention-keycloak-client package from the repository
# To be done on the next version bump
RUN apt-get update \
  && apt-get --assume-yes --no-install-recommends install \
  ca-certificates \
  python3-ldap \
  python3-keycloak \
  python3-requests \
  python3-defusedxml \
  ansible-core \
  univention-keycloak-client \
  && apt-get autoremove --assume-yes \
  && rm -rf /var/lib/apt/lists/*

FROM base

COPY bootstrap-ums-keycloak/ /app

RUN chmod +x entrypoint.sh \
  && chmod +x univention-keycloak

CMD ["/app/entrypoint.sh"]
