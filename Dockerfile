ARG KEYCLOAK_VERSION=22.0.1
ARG ARTIFACTS_DIR=/tmp/artifacts/

FROM quay.io/keycloak/keycloak:${KEYCLOAK_VERSION} as ftl

ARG KEYCLOAK_VERSION
ARG ARTIFACTS_DIR

RUN mkdir -p ${ARTIFACTS_DIR}

# workaround: https://github.com/GoogleContainerTools/kaniko/issues/1080
WORKDIR ${ARTIFACTS_DIR}
COPY dependencies/*.jar .

COPY files/cache-ispn-jdbc-ping.xml ${ARTIFACTS_DIR}
COPY files/keycloak-healthcheck ${ARTIFACTS_DIR}
COPY files/themes/UCS/ ${ARTIFACTS_DIR}/UCS/

RUN mkdir /tmp/build/
WORKDIR /tmp/build/

COPY ad-hoc/ ./
RUN mvn clean package --file univention-directory-manager
RUN mvn install --file univention-directory-manager
RUN mvn clean package --file univention-authenticator

COPY extensions/ ./extensions/
RUN mvn install --file extensions/pom.xml

RUN cp /tmp/build/univention-directory-manager/target/univention-directory-manager.jar ${ARTIFACTS_DIR}\
 && cp /tmp/build/univention-authenticator/target/univention-authenticator-22.0.1-jar-with-dependencies.jar ${ARTIFACTS_DIR}\
 && cp /tmp/build/extensions/lib/univention*.jar ${ARTIFACTS_DIR}

# patch login's template.ftl if template.ftl.patch is not empty
ARG TEMPLATE_FTL_PATCH=${ARTIFACTS_DIR}/UCS/login/template.ftl.patch
COPY --from=ftl /opt/keycloak/lib/lib/main/org.keycloak.keycloak-themes-${KEYCLOAK_VERSION}.jar ${ARTIFACTS_DIR}

RUN unzip ${ARTIFACTS_DIR}/org.keycloak.keycloak-themes-${KEYCLOAK_VERSION}.jar "theme/base/login/template.ftl"\
 && cp theme/base/login/template.ftl template.ftl\
 && cp theme/base/login/template.ftl template.ftl.orig\
 && git apply -v "${TEMPLATE_FTL_PATCH}"\
 && cp template.ftl ${ARTIFACTS_DIR}/UCS/login/\
 && rm -rf ${ARTIFACTS_DIR}/UCS/login/*.patch

FROM quay.io/keycloak/keycloak:${KEYCLOAK_VERSION}

ARG KEYCLOAK_VERSION
ARG ARTIFACTS_DIR

COPY --from=maven --chown=keycloak ${ARTIFACTS_DIR} ${ARTIFACTS_DIR}

RUN cp ${ARTIFACTS_DIR}/*.jar /opt/keycloak/providers/\
 && cp ${ARTIFACTS_DIR}/cache-ispn-jdbc-ping.xml /opt/keycloak/conf/cache-ispn-jdbc-ping.xml\
 && cp ${ARTIFACTS_DIR}/keycloak-healthcheck /opt/keycloak/bin/\
 && cp -r ${ARTIFACTS_DIR}/UCS /opt/keycloak/themes/\
 && rm -rf ${ARTIFACTS_DIR}

EXPOSE 7600
