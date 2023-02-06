FROM maven:3.8.2-openjdk-11 as maven

ARG ARTIFACTS_DIR="/tmp/artifacts/"
ARG BUILD_DIR="/tmp/build/"

RUN mkdir ${ARTIFACTS_DIR}
COPY dependencies/*.jar ${ARTIFACTS_DIR}
COPY files/cache-ispn-jdbc-ping.xml ${ARTIFACTS_DIR}

RUN mkdir ${BUILD_DIR}
WORKDIR ${BUILD_DIR}

COPY ad-hoc/ ./
RUN mvn clean package --file univention-directory-manager
RUN mvn install --file univention-directory-manager
RUN mvn clean package --file univention-authenticator

COPY univention-ldap-mapper/ ./univention-ldap-mapper
RUN mvn clean package --file univention-ldap-mapper
RUN mvn install --file univention-ldap-mapper

RUN cp ${BUILD_DIR}/univention-directory-manager/target/univention-directory-manager.jar ${ARTIFACTS_DIR}\
 && cp ${BUILD_DIR}/univention-authenticator/target/univention-authenticator-16.1.0-jar-with-dependencies.jar ${ARTIFACTS_DIR}\
 && cp ${BUILD_DIR}/univention-ldap-mapper/target/univention-ldap-mapper-19.0.1.jar ${ARTIFACTS_DIR}

FROM quay.io/keycloak/keycloak:19.0.2

ARG ARTIFACTS_DIR="/tmp/artifacts/"

COPY --from=maven --chown=keycloak ${ARTIFACTS_DIR} ${ARTIFACTS_DIR}

RUN cp ${ARTIFACTS_DIR}/*.jar /opt/keycloak/providers\
 && cp ${ARTIFACTS_DIR}/cache-ispn-jdbc-ping.xml /opt/keycloak/conf/cache-ispn-jdbc-ping.xml\
 && rm -rf ${ARTIFACTS_DIR}

EXPOSE 7600
