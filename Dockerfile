FROM maven:3.8.2-openjdk-11 as maven
WORKDIR /root/
COPY extensions/ ./
RUN mvn clean package --file ad-hoc/univention-directory-manager
RUN mvn install --file ad-hoc/univention-directory-manager
RUN mvn clean package --file ad-hoc/univention-authenticator
RUN mvn clean package --file reCaptcha-auth-flow
RUN mvn install --file reCaptcha-auth-flow

FROM quay.io/keycloak/keycloak:19.0.1
WORKDIR /opt/keycloak/providers
COPY --from=maven /root/ad-hoc/univention-directory-manager/target/univention-directory-manager.jar .
COPY --from=maven /root/ad-hoc/univention-authenticator/target/univention-authenticator-16.1.0-jar-with-dependencies.jar .
COPY --from=maven /root/reCaptcha-auth-flow/target/reCaptcha-auth-flow.jar .
COPY dependencies/*.jar ./

COPY files/cache-ispn-jdbc-ping.xml /opt/keycloak/conf/cache-ispn-jdbc-ping.xml
EXPOSE 7600
