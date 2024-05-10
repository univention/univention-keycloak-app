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

{{- define "keycloak-bootstrap.keycloak.auth.username" -}}
{{- if .Values.keycloak.auth.username -}}
{{- .Values.keycloak.auth.username -}}
{{- else if .Values.global.nubusDeployment -}}
kcadmin
{{- else -}}
{{- required ".Values.keycloak.auth.username must be defined." .Values.keycloak.auth.username -}}
{{- end -}}
{{- end -}}

{{- define "keycloak-bootstrap.keycloak.auth.credentialSecret.name" -}}
{{- if .Values.keycloak.auth.credentialSecret.name -}}
{{- .Values.keycloak.auth.credentialSecret.name -}}
{{- else if .Values.keycloak.auth.password -}}
{{ printf "%s-keycloak-credentials" (include "common.names.fullname" .) }}
{{- else if .Values.global.nubusDeployment -}}
{{- printf "%s-keycloak-bootstrap-keycloak-credentials" .Release.Name -}}
{{- else -}}
{{ required ".Values.keycloak.auth.password must be defined." .Values.keycloak.auth.password}}
{{- end -}}
{{- end -}}

{{- define "keycloak-bootstrap.keycloak.auth.password" -}}
{{- if .Values.keycloak.auth.credentialSecret.name -}}
valueFrom:
  secretKeyRef:
    name: {{ .Values.keycloak.auth.credentialSecret.name | quote }}
    key: {{ .Values.keycloak.auth.credentialSecret.key | quote }}
{{- else if .Values.global.nubusDeployment -}}
valueFrom:
  secretKeyRef:
    name: {{ include "keycloak-bootstrap.keycloak.auth.credentialSecret.name" . | quote }}
    key: {{ .Values.keycloak.auth.credentialSecret.key | quote }}
{{- else -}}
value: {{ required ".Values.keycloak.auth.password is required." .Values.keycloak.auth.password | quote }}
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

{{- define "keycloak-bootstrap.ldap.auth.bindDn" -}}
{{- if .Values.ldap.auth.bindDn -}}
{{- .Values.ldap.auth.bindDn -}}
{{- else if .Values.global.nubusDeployment -}}
{{- $baseDn := include "nubusTemplates.ldapServer.ldap.baseDn" . -}}
{{ printf "uid=%s,cn=users,%s" "readonly" $baseDn }}
{{- else -}}
{{- required ".Values.ldap.auth.bindDn must be defined." .Values.ldap.auth.bindDn -}}
{{- end -}}
{{- end -}}


{{- define "keycloak-bootstrap.ldap.auth.credentialSecret.name" -}}
{{- if .Values.ldap.auth.credentialSecret.name -}}
{{- .Values.ldap.auth.credentialSecret.name -}}
{{- else if .Values.ldap.auth.password -}}
{{ printf "%s-ldap-credentials" (include "common.names.fullname" .) }}
{{- else if .Values.global.nubusDeployment -}}
{{- printf "%s-keycloak-bootstrap-ldap-credentials" .Release.Name -}}
{{- else -}}
{{ required ".Values.ldap.auth.password must be defined." .Values.ldap.auth.password}}
{{- end -}}
{{- end -}}

{{- define "keycloak-bootstrap.ldap.auth.password" -}}
{{- if .Values.ldap.auth.credentialSecret.name -}}
valueFrom:
  secretKeyRef:
    name: {{ .Values.ldap.auth.credentialSecret.name | quote }}
    key: {{ .Values.ldap.auth.credentialSecret.key | quote }}
{{- else if .Values.global.nubusDeployment -}}
valueFrom:
  secretKeyRef:
    name: {{ include "keycloak-bootstrap.ldap.auth.credentialSecret.name" . | quote }}
    key: {{ .Values.ldap.auth.credentialSecret.key | quote }}
{{- else -}}
value: {{ required ".Values.ldap.auth.password is required." .Values.ldap.auth.password | quote }}
{{- end -}}
{{- end -}}
