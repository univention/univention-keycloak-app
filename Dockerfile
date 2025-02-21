# buíld ad hoc federation and our extensions
FROM maven:3.8.2-openjdk-17 AS maven
WORKDIR /extensions
COPY extensions ./
RUN --mount=type=cache,target=/root/.m2 mvn clean package --file pom.xml \
  && cp /root/.m2/repository/com/github/seancfoley/ipaddress/5.5.1/ipaddress-5.5.1.jar ./

# keycloak itself
FROM docker-registry.knut.univention.de/knut/pipeline_helper AS keycloak
ARG KEYCLOAK_DIST # downloading the archive for every pipeline job just takes to long
ARG KEYCLOAK_VERSION
WORKDIR /keycloak
ADD $KEYCLOAK_DIST .
RUN tar -xvf keycloak-${KEYCLOAK_VERSION}.tar.gz && rm keycloak-${KEYCLOAK_VERSION}.tar.gz

# templates and themes
FROM docker-registry.knut.univention.de/knut/pipeline_helper AS theme
ARG KEYCLOAK_VERSION
WORKDIR /themes
COPY --from=keycloak /keycloak/keycloak-${KEYCLOAK_VERSION}/lib/lib/main/org.keycloak.keycloak-themes-${KEYCLOAK_VERSION}.jar .
COPY files/themes ./
RUN unzip org.keycloak.keycloak-themes-${KEYCLOAK_VERSION}.jar "theme/base/login/template.ftl" \
 && cp theme/base/login/template.ftl UCS/login/template.ftl \
 && cp theme/base/login/template.ftl UCS/login/template.ftl.orig \
 && rm -rf theme \
 && rm org.keycloak.keycloak-themes-${KEYCLOAK_VERSION}.jar \
 && cd UCS/login \
 && git apply -v template.ftl.patch

# copy everything together so that we can use one COPY statement for the final image
FROM docker-registry.knut.univention.de/knut/pipeline_helper AS artifacts
ARG KEYCLOAK_VERSION
RUN apt-get update && apt-get install -y zip
RUN touch empty && zip empty.jar empty
COPY dependencies/*.jar /opt/keycloak/providers/
COPY files/cache-ispn-jdbc-ping.xml /opt/keycloak/conf/cache-ispn-jdbc-ping.xml
COPY files/keycloak-healthcheck /opt/keycloak/bin/
COPY --from=keycloak /keycloak/keycloak-${KEYCLOAK_VERSION} /opt/keycloak
COPY --from=theme /themes /opt/keycloak/themes/
COPY --from=maven /extensions/lib/univention-app-authenticator-*.jar /opt/keycloak/providers/
COPY --from=maven /extensions/lib/univention-ldap-mapper-*.jar /opt/keycloak/providers/
COPY --from=maven /extensions/lib/univention-condition-ipaddress-*.jar /opt/keycloak/providers/
COPY --from=maven /extensions/lib/univention-user-attribute-nameid-mapper-base64-*.jar /opt/keycloak/providers/
COPY --from=maven /extensions/lib/univention-condition-ipaddress-*.jar /opt/keycloak/providers
COPY --from=maven /extensions/ipaddress-5.5.1.jar /opt/keycloak/providers
COPY --from=maven /extensions/udm/target/udm.jar /opt/keycloak/providers/
COPY --from=maven /extensions/univention-authenticator/target/univention-authenticator-*-jar-with-dependencies.jar /opt/keycloak/providers/
RUN cp empty.jar opt/keycloak/lib/lib/main/com.oracle.database.jdbc.ojdbc11-*.jar \
 && cp empty.jar opt/keycloak/lib/lib/main/com.oracle.database.nls.orai18n-*.jar \
 && cp empty.jar opt/keycloak/lib/lib/deployment/io.quarkus.quarkus-jdbc-oracle-deployment-*.Final.jar \
 && cp empty.jar opt/keycloak/lib/lib/deployment/org.jboss.metadata.jboss-metadata-web-*.Final.jar

# the keycloak image
# see https://github.com/keycloak/keycloak/tree/main/quarkus/container
FROM gitregistry.knut.univention.de/univention/components/ucs-base-image/ucs-base-520
RUN apt-get update \
 && apt-get install -y --no-install-recommends --no-install-suggests openjdk-17-jre-headless \
 && apt-get -y dist-upgrade \
 && echo "keycloak:x:0:root" >> /etc/group \
 && echo "keycloak:x:1000:0:keycloak user:/opt/keycloak:/sbin/nologin" >> /etc/passwd \
 && apt-get clean autoclean && rm -rf /var/lib/{apt,dpkg,cache,log} /var/cache/apt/archives \
 && apt-get purge -y --allow-remove-essential univention-archive-key apt
COPY --from=artifacts --chown=keycloak /opt/keycloak /opt/keycloak
USER 1000
EXPOSE 7600
EXPOSE 8080
EXPOSE 8443
ENTRYPOINT [ "/opt/keycloak/bin/kc.sh" ]
