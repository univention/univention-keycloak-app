ARG KEYCLOAK_VERSION=22.0.1

# to get the original template files
FROM quay.io/keycloak/keycloak:${KEYCLOAK_VERSION} as ftl

# buíld ad hoc federation and our extensions
FROM maven:3.8.2-openjdk-17 as maven
WORKDIR /ad-hoc
COPY ad-hoc/ ./
RUN mvn clean package --file univention-directory-manager
RUN mvn install --file univention-directory-manager
RUN mvn clean package --file univention-authenticator
RUN ls univention-authenticator/target/
WORKDIR /extensions
COPY extensions ./
RUN mvn clean package --file pom.xml

# build login/template patch
FROM docker-registry.knut.univention.de/knut/pipeline_helper as theme
ARG KEYCLOAK_VERSION
WORKDIR /themes
COPY --from=ftl /opt/keycloak/lib/lib/main/org.keycloak.keycloak-themes-${KEYCLOAK_VERSION}.jar /
COPY files/themes ./
RUN cd UCS/login \
 && unzip /org.keycloak.keycloak-themes-${KEYCLOAK_VERSION}.jar "theme/base/login/template.ftl" \
 && cp theme/base/login/template.ftl template.ftl \
 && cp theme/base/login/template.ftl template.ftl.orig \
 && rm -rf theme \
 && git apply -v template.ftl.patch

# copy everything together so that we can use one COPY statement for the final image
FROM docker-registry.knut.univention.de/knut/pipeline_helper as artifacts
COPY dependencies/*.jar /opt/keycloak/providers/
COPY files/cache-ispn-jdbc-ping.xml /opt/keycloak/conf/cache-ispn-jdbc-ping.xml
COPY files/keycloak-healthcheck /opt/keycloak/bin/
COPY --from=theme /themes /opt/keycloak/themes/
COPY --from=maven /extensions/lib/univention-app-authenticator-*.jar /opt/keycloak/providers/
COPY --from=maven /extensions/lib/univention-ldap-mapper-*.jar /opt/keycloak/providers/
COPY --from=maven /extensions/lib/univention-user-attribute-nameid-mapper-base64-*.jar /opt/keycloak/providers/
COPY --from=maven /ad-hoc/univention-directory-manager/target/univention-directory-manager.jar /opt/keycloak/providers/
COPY --from=maven /ad-hoc/univention-authenticator/target/univention-authenticator-*-jar-with-dependencies.jar /opt/keycloak/providers/

# the keycloak image
FROM quay.io/keycloak/keycloak:${KEYCLOAK_VERSION}
COPY --from=artifacts --chown=keycloak /opt/keycloak /opt/keycloak
EXPOSE 7600
