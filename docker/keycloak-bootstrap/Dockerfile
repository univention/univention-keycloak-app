# SPDX-FileCopyrightText: 2023 Univention GmbH
# SPDX-License-Identifier: AGPL-3.0-only

ARG UCS_BASE_IMAGE_TAG=0.12.0
ARG UCS_BASE_IMAGE=gitregistry.knut.univention.de/univention/components/ucs-base-image/ucs-base-520

FROM ${UCS_BASE_IMAGE}:${UCS_BASE_IMAGE_TAG} as base

WORKDIR /app

RUN apt-get update \
  && apt-get --assume-yes --no-install-recommends install \
    ca-certificates=20230311 \
    python3-ldap=3.4.3-2+b2 \
    python3-keycloak=3.3.0+dfsg-2 \
    python3-requests=2.28.1+dfsg-1 \
    python3-defusedxml=0.7.1-2 \
    ansible=7.3.0+dfsg-1 \
    univention-keycloak-client=3.0.11 \
  && apt-get autoremove --assume-yes \
  && rm -rf /var/lib/apt/lists/*

FROM base

COPY bootstrap-ums-keycloak/ /app

CMD ["/app/entrypoint.sh"]
