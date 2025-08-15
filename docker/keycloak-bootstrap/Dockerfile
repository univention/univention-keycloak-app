# SPDX-FileCopyrightText: 2023 Univention GmbH
# SPDX-License-Identifier: AGPL-3.0-only

ARG UCS_BASE_IMAGE_TAG=5.2.2-build.20250814@sha256:02036c4d80d8db60e5c8f327fed73ae52bc00ae99c1c459fa73459a2c4c1f93f
ARG UCS_BASE_IMAGE=gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base

FROM ${UCS_BASE_IMAGE}:${UCS_BASE_IMAGE_TAG} AS base

WORKDIR /app

# hadolint ignore=DL3025
RUN echo 'deb [trusted=yes] http://omar.knut.univention.de/build2/git/jtorres-client-scope git main' >> /etc/apt/sources.list

# FIXME: Get the latest univention-keycloak-client package from the repository
# To be done on the next version bump
RUN apt-get update \
  && apt-get --assume-yes --no-install-recommends install \
  ansible-core \
  ca-certificates \
  python3-defusedxml \
  python3-keycloak \
  python3-ldap \
  python3-requests \
  univention-keycloak-client \
  && apt-get autoremove --assume-yes \
  && rm -rf /var/lib/apt/lists/*

FROM base

COPY bootstrap-ums-keycloak/ /app

CMD ["/app/entrypoint.sh"]
