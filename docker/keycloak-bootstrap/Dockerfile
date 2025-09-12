# SPDX-FileCopyrightText: 2023 Univention GmbH
# SPDX-License-Identifier: AGPL-3.0-only


# TODO: Interim we use a customized version of the package
# "univention-keycloak-client".
#
# See: https://git.knut.univention.de/univention/dev/ucs/-/merge_requests/1604

# ARG UCS_BASE_IMAGE_TAG=5.2.2-build.20250821@sha256:839b93e89298c51a9c8cd45c119a284f9161b18d7270da4dc92588772ca20f4d
# ARG UCS_BASE_IMAGE=gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base
ARG UCS_BASE_IMAGE_TAG=latest
ARG UCS_BASE_IMAGE=gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base-dev-523

FROM ${UCS_BASE_IMAGE}:${UCS_BASE_IMAGE_TAG} AS base

WORKDIR /app

# hadolint ignore=DL3009
RUN ucs-dev-add-branch.sh nubus-patches-keycloak-bootstrap \
    && apt-get update

# hadolint ignore=DL3008
RUN apt-get --assume-yes --no-install-recommends install \
    ansible-core \
    ca-certificates \
    python3-defusedxml \
    python3-keycloak \
    python3-ldap \
    python3-requests \
    # TODO: Remove the special version pin once the regular base image is used again.
    univention-keycloak-client=3.3.1A~5.2.3.202509101236 \
    && apt-get autoremove --assume-yes

FROM base

COPY bootstrap-ums-keycloak/ /app

CMD ["/app/entrypoint.sh"]
