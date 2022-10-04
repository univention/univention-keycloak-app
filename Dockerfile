FROM maven:3.8.2-openjdk-11 as maven
WORKDIR /root/
COPY ad-hoc/ ./
RUN mvn clean package --file univention-directory-manager
RUN mvn install --file univention-directory-manager
RUN mvn clean package --file univention-authenticator

FROM quay.io/keycloak/keycloak:19.0.1
WORKDIR /opt/keycloak/providers
COPY --from=maven /root/univention-directory-manager/target/univention-directory-manager.jar .
COPY --from=maven /root/univention-authenticator/target/univention-authenticator-16.1.0-jar-with-dependencies.jar .
COPY dependencies/*.jar ./

COPY cache-ispn-jdbc-ping.xml /opt/keycloak/conf/
EXPOSE 7600
