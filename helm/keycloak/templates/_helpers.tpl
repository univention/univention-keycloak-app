{{- /*
SPDX-FileCopyrightText: 2024 Univention GmbH
SPDX-License-Identifier: AGPL-3.0-only
*/}}

{{- /*
These template definitions are only used in this chart and do not relate to templates defined elsewhere.
*/}}
{{- define "keycloak.postgresql.connection.host" -}}
{{- if or .Values.postgresql.connection.host .Values.global.postgresql.connection.host -}}
{{- tpl ( coalesce .Values.postgresql.connection.host .Values.global.postgresql.connection.host ) . -}}
{{- else if .Values.global.nubusDeployment -}}
{{- printf "%s-postgresql" .Release.Name -}}
{{- else -}}
{{- required ".Values.postgresql.connection.host or .Values.global.postgresql.connection.host must be defined." (coalesce .Values.postgresql.connection.host .Values.global.postgresql.connection.host) -}}
{{- end -}}
{{- end -}}

{{- define "keycloak.postgresql.connection.port" -}}
{{- if or .Values.postgresql.connection.port .Values.global.postgresql.connection.port -}}
{{- tpl ( coalesce .Values.postgresql.connection.port .Values.global.postgresql.connection.port ) . -}}
{{- else -}}
5432
{{- end -}}
{{- end -}}

{{- define "keycloak.postgresql.auth.database" -}}
{{- if .Values.postgresql.auth.database -}}
{{- .Values.postgresql.auth.database -}}
{{- else if .Values.global.nubusDeployment -}}
keycloak
{{- else -}}
{{- required ".Values.postgresql.auth.database must be defined." .Values.postgresql.auth.database -}}
{{- end -}}
{{- end -}}

{{- define "keycloak.service.baseUrl" -}}
{{- if .Values.config.baseUrl -}}
{{- .Values.config.baseUrl -}}
{{- else if .Values.global.nubusDeployment -}}
{{- printf "https://%s.%s" .Values.global.subDomains.portal .Values.global.domain -}}
{{- end -}}
{{- end -}}

{{- define "keycloak.style.themeUrl" -}}
{{- if .Values.theme.univentionTheme -}}
{{- .Values.theme.univentionTheme -}}
{{- else -}}
{{- $baseUrl := include "keycloak.service.baseUrl" . -}}
{{- printf "%s/univention/theme.css" $baseUrl -}}
{{- end -}}
{{- end -}}

{{- define "keycloak.style.customUrl" -}}
{{- if .Values.theme.univentionCustomTheme -}}
{{- .Values.theme.univentionCustomTheme -}}
{{- else -}}
{{- $baseUrl := include "keycloak.service.baseUrl" . -}}
{{- printf "%s/univention/portal/css/custom.css" $baseUrl -}}
{{- end -}}
{{- end -}}

{{- define "keycloak.style.faviconUrl" -}}
{{- if .Values.theme.favIcon -}}
{{- .Values.theme.favIcon -}}
{{- else -}}
{{- $baseUrl := include "keycloak.service.baseUrl" . -}}
{{- printf "%s/favicon.ico" $baseUrl -}}
{{- end -}}
{{- end -}}

{{- define "keycloak.ingress.certManagerIssuer" -}}
{{- if .Values.global.certManagerIssuer -}}
{{- .Values.global.certManagerIssuer -}}
{{- end -}}
{{- end -}}

{{- define "keycloak.ingress.host" -}}
{{- if .Values.ingress.host -}}
{{- .Values.ingress.host -}}
{{- else if .Values.global.nubusDeployment -}}
{{- printf "%s.%s" .Values.global.subDomains.keycloak .Values.global.domain -}}
{{- end -}}
{{- end -}}

{{- define "keycloak.ingress.tls.secretName" -}}
{{- if .Values.ingress.tls.secretName -}}
{{- tpl .Values.ingress.tls.secretName . -}}
{{- else if .Values.global.nubusDeployment -}}
{{- printf "%s-keycloak-tls" .Release.Name -}}
{{- else -}}
{{- required ".Values.ingress.tls.secretName must be defined." .Values.ingress.tls.secretName -}}
{{- end -}}
{{- end -}}
