FROM maven:3.8.2-openjdk-11 as maven

RUN mkdir /tmp/artifacts/
COPY dependencies/*.jar /tmp/artifacts/
COPY files/cache-ispn-jdbc-ping.xml /tmp/artifacts/

RUN mkdir /tmp/build/
WORKDIR /tmp/build/

COPY ad-hoc/ ./
RUN mvn clean package --file univention-directory-manager
RUN mvn install --file univention-directory-manager
RUN mvn clean package --file univention-authenticator

COPY univention-ldap-mapper/ ./univention-ldap-mapper
RUN mvn clean package --file univention-ldap-mapper
RUN mvn install --file univention-ldap-mapper

RUN cp /tmp/build/univention-directory-manager/target/univention-directory-manager.jar /tmp/artifacts/\
 && cp /tmp/build/univention-authenticator/target/univention-authenticator-16.1.0-jar-with-dependencies.jar /tmp/artifacts/\
 && cp /tmp/build/univention-ldap-mapper/target/univention-ldap-mapper-19.0.2.jar /tmp/artifacts/

FROM quay.io/keycloak/keycloak:19.0.2

COPY --from=maven --chown=keycloak /tmp/artifacts/ /tmp/artifacts/

RUN cp /tmp/artifacts/*.jar /opt/keycloak/providers\
 && cp /tmp/artifacts/cache-ispn-jdbc-ping.xml /opt/keycloak/conf/cache-ispn-jdbc-ping.xml\
 && rm -rf /tmp/artifacts/

EXPOSE 7600
