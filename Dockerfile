FROM maven:3.8.3-openjdk-17 as maven

RUN mkdir /tmp/artifacts/
COPY dependencies/*.jar /tmp/artifacts/
COPY files/cache-ispn-jdbc-ping.xml /tmp/artifacts/
COPY files/keycloak-healthcheck /tmp/artifacts/

RUN mkdir /tmp/build/
WORKDIR /tmp/build/

COPY ad-hoc/ ./
RUN mvn clean package --file univention-directory-manager
RUN mvn install --file univention-directory-manager
RUN mvn clean package --file univention-authenticator

COPY univention-user-attribute-nameid-mapper-base64/ ./univention-user-attribute-nameid-mapper-base64
RUN mvn clean package --file univention-user-attribute-nameid-mapper-base64 \
 && mvn install --file univention-user-attribute-nameid-mapper-base64

COPY univention-app-authenticator/ ./univention-app-authenticator
RUN mvn clean package --file univention-app-authenticator \
 && mvn install --file univention-app-authenticator

COPY univention-ldap-mapper/ ./univention-ldap-mapper
RUN mvn clean package --file univention-ldap-mapper \
 && mvn install --file univention-ldap-mapper


RUN cp /tmp/build/univention-directory-manager/target/univention-directory-manager.jar /tmp/artifacts/\
 && cp /tmp/build/univention-authenticator/target/univention-authenticator-16.1.0-jar-with-dependencies.jar /tmp/artifacts/\
 && cp /tmp/build/univention-ldap-mapper/target/univention-ldap-mapper-22.0.1.jar /tmp/artifacts/ \
 && cp /tmp/build/univention-user-attribute-nameid-mapper-base64/target/univention-user-attribute-nameid-mapper-base64-22.0.1.jar /tmp/artifacts/ \
 && cp /tmp/build/univention-app-authenticator/target/univention-app-authenticator-22.0.1.jar /tmp/artifacts/

FROM quay.io/keycloak/keycloak:22.0.1

COPY --from=maven --chown=keycloak /tmp/artifacts/ /tmp/artifacts/

RUN cp /tmp/artifacts/*.jar /opt/keycloak/providers\
 && cp /tmp/artifacts/cache-ispn-jdbc-ping.xml /opt/keycloak/conf/cache-ispn-jdbc-ping.xml\
 && cp /tmp/artifacts/keycloak-healthcheck /opt/keycloak/bin/\
 && rm -rf /tmp/artifacts/

EXPOSE 7600
