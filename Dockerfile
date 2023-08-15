FROM maven:3.8.3-openjdk-17 as maven

ARG ARTIFACTS_DIR=/tmp/artifacts/

RUN mkdir -p ${ARTIFACTS_DIR}
COPY dependencies/*.jar ${ARTIFACTS_DIR}
COPY files/cache-ispn-jdbc-ping.xml ${ARTIFACTS_DIR}
COPY files/keycloak-healthcheck ${ARTIFACTS_DIR}

RUN mkdir /tmp/build/
WORKDIR /tmp/build/

COPY ad-hoc/ ./
RUN mvn clean package --file univention-directory-manager
RUN mvn install --file univention-directory-manager
RUN mvn clean package --file univention-authenticator

COPY extensions/ ./extensions/
RUN mvn install --file extensions/pom.xml

RUN cp /tmp/build/univention-directory-manager/target/univention-directory-manager.jar ${ARTIFACTS_DIR}\
 && cp /tmp/build/univention-authenticator/target/univention-authenticator-16.1.0-jar-with-dependencies.jar ${ARTIFACTS_DIR}\
 && cp /tmp/build/extensions/lib/univention*.jar ${ARTIFACTS_DIR}

RUN cp /tmp/build/univention-directory-manager/target/univention-directory-manager.jar /tmp/artifacts/\
 && cp /tmp/build/univention-authenticator/target/univention-authenticator-22.0.1-jar-with-dependencies.jar /tmp/artifacts/\
 && cp /tmp/build/univention-ldap-mapper/target/univention-ldap-mapper-22.0.1.jar /tmp/artifacts/ \
 && cp /tmp/build/univention-user-attribute-nameid-mapper-base64/target/univention-user-attribute-nameid-mapper-base64-22.0.1.jar /tmp/artifacts/ \
 && cp /tmp/build/univention-app-authenticator/target/univention-app-authenticator-22.0.1.jar /tmp/artifacts/

FROM quay.io/keycloak/keycloak:22.0.1

ARG ARTIFACTS_DIR=/tmp/artifacts/

COPY --from=maven --chown=keycloak ${ARTIFACTS_DIR} ${ARTIFACTS_DIR}

RUN cp ${ARTIFACTS_DIR}/*.jar /opt/keycloak/providers\
 && cp ${ARTIFACTS_DIR}/cache-ispn-jdbc-ping.xml /opt/keycloak/conf/cache-ispn-jdbc-ping.xml\
 && cp ${ARTIFACTS_DIR}/keycloak-healthcheck /opt/keycloak/bin/\
 && rm -rf ${ARTIFACTS_DIR}

EXPOSE 7600
