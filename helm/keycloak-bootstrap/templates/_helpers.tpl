{{- /*
SPDX-FileCopyrightText: 2024 Univention GmbH
SPDX-License-Identifier: AGPL-3.0-only
*/}}

{{- /*
These template definitions relate to the use of this Helm chart as a sub-chart of the Nubus Umbrella Chart.
Templates defined in other Helm sub-charts are imported to be used to configure this chart.
If the value .Values.global.nubusDeployment equates to true, the defined templates are imported.
*/}}

{{- define "keycloak-bootstrap.ldap.connection.protocol" -}}
{{- if .Values.ldap.connection.protocol -}}
{{- .Values.ldap.connection.protocol -}}
{{- else if .Values.global.nubusDeployment -}}
{{- include "nubusTemplates.ldapServer.ldap.connection.protocol" . -}}
{{- else -}}
ldap
{{- end -}}
{{- end -}}

{{- define "keycloak-bootstrap.ldap.connection.host" -}}
{{- if .Values.ldap.connection.host -}}
{{- .Values.ldap.connection.host -}}
{{- else if .Values.global.nubusDeployment -}}
{{- include "nubusTemplates.ldapServer.ldap.connection.host" . -}}
{{- else -}}
{{- required ".Values.ldap.connection.host must be defined." .Values.ldap.connection.host -}}
{{- end -}}
{{- end -}}

{{- define "keycloak-bootstrap.ldap.connection.port" -}}
{{- if .Values.ldap.connection.port -}}
{{- .Values.ldap.connection.port -}}
{{- else if .Values.global.nubusDeployment -}}
{{- include "nubusTemplates.ldapServer.ldap.connection.port" . -}}
{{- else -}}
{{- required ".Values.ldap.connection.port must be defined." .Values.ldap.connection.port -}}
{{- end -}}
{{- end -}}

{{- define "keycloak-bootstrap.ldap.connection.uri" -}}
{{- $protocol := include "keycloak-bootstrap.ldap.connection.protocol" . -}}
{{- $host := include "keycloak-bootstrap.ldap.connection.host" . -}}
{{- $port := include "keycloak-bootstrap.ldap.connection.port" . -}}
{{- printf "%s://%s:%s" $protocol $host $port -}}
{{- end -}}

{{- define "keycloak-bootstrap.ldap.baseDn" -}}
{{- if .Values.ldap.baseDn -}}
{{- .Values.ldap.baseDn -}}
{{- else if .Values.global.nubusDeployment -}}
{{- include "nubusTemplates.ldapServer.ldap.baseDn" . -}}
{{- else -}}
{{- required ".Values.ldap.baseDn must be defined." .Values.ldap.baseDn -}}
{{- end -}}
{{- end -}}

{{- /*
These template definitions are only used in this chart and do not relate to templates defined elsewhere.
*/}}

{{- define "keycloak-bootstrap.keycloak.connection.protocol" -}}
{{- if .Values.keycloak.connection.protocol -}}
{{- .Values.keycloak.connection.protocol -}}
{{- else -}}
http
{{- end -}}
{{- end -}}

{{- define "keycloak-bootstrap.keycloak.connection.host" -}}
{{- if .Values.keycloak.connection.host -}}
{{- .Values.keycloak.connection.host -}}
{{- else if .Values.global.nubusDeployment -}}
{{- printf "%s-keycloak" .Release.Name -}}
{{- else if not .Values.keycloak.connection.baseUrl -}}
{{- required ".Values.keycloak.connection.host must be defined." .Values.keycloak.connection.host -}}
{{- end -}}
{{- end -}}

{{- define "keycloak-bootstrap.keycloak.connection.port" -}}
{{- if .Values.keycloak.connection.port -}}
{{- .Values.keycloak.connection.port -}}
{{- else -}}
8080
{{- end -}}
{{- end -}}

{{- define "keycloak-bootstrap.keycloak.connection.baseUrl" -}}
{{- if .Values.keycloak.connection.baseUrl -}}
{{- .Values.keycloak.connection.baseUrl -}}
{{- else if .Values.global.nubusDeployment -}}
{{- $protocol := include "keycloak-bootstrap.keycloak.connection.protocol" . -}}
{{- $host := include "keycloak-bootstrap.keycloak.connection.host" . -}}
{{- $port := include "keycloak-bootstrap.keycloak.connection.port" . -}}
{{- printf "%s://%s:%s" $protocol $host $port -}}
{{- else -}}
{{- required ".Values.keycloak.connection.baseUrl must be defined." .Values.keycloak.connection.baseUrl -}}
{{- end -}}
{{- end -}}

{{- define "keycloak-bootstrap.keycloak.auth.realm" -}}
{{- if .Values.keycloak.auth.realm -}}
{{- .Values.keycloak.auth.realm -}}
{{- else if .Values.global.nubusDeployment -}}
{{- coalesce .Values.keycloak.auth.realm .Values.global.keycloak.realm "nubus" -}}
{{- else -}}
{{- required ".Values.keycloak.auth.realm must be defined." .Values.keycloak.auth.realm -}}
{{- end -}}
{{- end -}}

{{- define "keycloak-bootstrap.keycloak.auth.masterRealm" -}}
{{- if .Values.keycloak.auth.masterRealm -}}
{{- .Values.keycloak.auth.masterRealm -}}
{{- else if .Values.global.nubusDeployment -}}
master
{{- else -}}
{{- required ".Values.keycloak.auth.masterRealm must be defined." .Values.keycloak.auth.masterRealm -}}
{{- end -}}
{{- end -}}

{{- define "keycloak-bootstrap.saml.serviceProviderHostname" -}}
{{- if .Values.config.saml.serviceProviderHostname -}}
{{- .Values.config.saml.serviceProviderHostname -}}
{{- else if .Values.global.nubusDeployment -}}
{{- printf "%s.%s" .Values.global.subDomains.portal .Values.global.domain }}
{{- else -}}
{{- end -}}
{{- end -}}
