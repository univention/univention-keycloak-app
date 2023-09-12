FROM maven:3.8.5-openjdk-17 as maven

WORKDIR /build

COPY ad-hoc/univention-directory-manager/ ./univention-directory-manager/
RUN mvn clean install --file univention-directory-manager -Dmaven.test.skip=true -Dmaven.javadoc.skip=true

COPY ad-hoc/univention-authenticator/ ./univention-authenticator/
RUN mvn clean package --file univention-authenticator

COPY univention-user-attribute-nameid-mapper-base64/ ./univention-user-attribute-nameid-mapper-base64
RUN mvn clean package --file univention-user-attribute-nameid-mapper-base64

COPY univention-app-authenticator/ ./univention-app-authenticator
RUN mvn clean package --file univention-app-authenticator

COPY univention-ldap-mapper/ ./univention-ldap-mapper
RUN mvn clean package --file univention-ldap-mapper

COPY files/cache-ispn-jdbc-ping.xml .
COPY files/keycloak-healthcheck .

RUN find . ! -name '*sources*' -wholename '*/target/univention-*.jar' -print0  -exec cp {} . \;

FROM quay.io/keycloak/keycloak:22.0.1

COPY --from=maven --chown=keycloak /build/ /tmp/

RUN cp /tmp/*.jar /opt/keycloak/providers\
 && cp /tmp/cache-ispn-jdbc-ping.xml /opt/keycloak/conf/cache-ispn-jdbc-ping.xml\
 && cp /tmp/keycloak-healthcheck /opt/keycloak/bin/\
 && rm -rf /tmp/*

EXPOSE 7600
