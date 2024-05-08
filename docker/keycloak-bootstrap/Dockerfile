# SPDX-FileCopyrightText: 2023 Univention GmbH
# SPDX-License-Identifier: AGPL-3.0-only

ARG UCS_BASE_IMAGE_TAG=0.12.0
ARG UCS_BASE_IMAGE=gitregistry.knut.univention.de/univention/components/ucs-base-image/ucs-base-520

FROM ${UCS_BASE_IMAGE}:${UCS_BASE_IMAGE_TAG}

# We want to fetch it from upstream and not place it statically into the image but first our changes need to be merged:
# ARG UV_KC_PY=https://raw.githubusercontent.com/rossnet/univention-keycloak/5.0-5/services/univention-keycloak/scripts/univention-keycloak
#    wget=1.21.3-1+b2 \
#  && wget -q -nv ${UV_KC_PY} \
#  && apt-get purge --assume-yes wget \
#  && chmod +x univention-keycloak \

WORKDIR /app

RUN apt-get update \
  && apt-get --assume-yes --no-install-recommends install \
    ca-certificates=20230311 \
    python3-ldap=3.4.3-2+b2 \
    python3-keycloak=3.3.0+dfsg-2 \
    python3-requests=2.28.1+dfsg-1 \
    python3-defusedxml=0.7.1-2 \
    ansible=7.3.0+dfsg-1 \
  && apt-get autoremove --assume-yes \
  && rm -rf /var/lib/apt/lists/*

COPY bootstrap-ums-keycloak/ /app

RUN chmod +x entrypoint.sh \
  && chmod +x univention-keycloak

CMD ["/app/entrypoint.sh"]
