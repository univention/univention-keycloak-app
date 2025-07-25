# keycloak

- **Version**: 0.6.0
- **Type**: application
- **AppVersion**:
- **Homepage:** <https://www.univention.de>

## TL;DR

```console
helm upgrade --install keycloak oci://gitregistry.knut.univention.de/univention/components/keycloak/helm/keycloak
```

## Introduction

This chart does install the REST API for the Univention Directory Manager.

The server provides an abstraction layer for interacting with the LDAP.

## Installing

To install the chart with the release name `keycloak`:

```console
helm upgrade --install keycloak oci://gitregistry.knut.univention.de/univention/components/keycloak/helm/keycloak
```

## Uninstalling

To uninstall the chart with the release name `keycloak`:

```console
helm uninstall keycloak
```

## Requirements

| Repository | Name | Version |
|------------|------|---------|
| https://charts.bitnami.com/bitnami | common | ^2.x.x |

## Values

<table>
	<thead>
		<th>Key</th>
		<th>Type</th>
		<th>Default</th>
		<th>Description</th>
	</thead>
	<tbody>
		<tr>
			<td>affinity</td>
			<td>object</td>
			<td><pre lang="json">
{}
</pre>
</td>
			<td>Affinity for pod assignment Ref: https://kubernetes.io/docs/concepts/configuration/assign-pod-node/#affinity-and-anti-affinity Note: podAffinityPreset, podAntiAffinityPreset, and nodeAffinityPreset will be ignored when it's set.</td>
		</tr>
		<tr>
			<td>commonAnnotations</td>
			<td>object</td>
			<td><pre lang="json">
{}
</pre>
</td>
			<td>Additional custom annotations to add to all deployed objects.</td>
		</tr>
		<tr>
			<td>commonLabels</td>
			<td>object</td>
			<td><pre lang="json">
{}
</pre>
</td>
			<td>Additional custom labels to add to all deployed objects.</td>
		</tr>
		<tr>
			<td>config.baseUrl</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td></td>
		</tr>
		<tr>
			<td>config.enableMetrics</td>
			<td>bool</td>
			<td><pre lang="json">
true
</pre>
</td>
			<td>Enables Keycloak metrics endpoint Ref.: https://www.keycloak.org/server/configuration-metrics</td>
		</tr>
		<tr>
			<td>config.exposeAdminConsole</td>
			<td>bool</td>
			<td><pre lang="json">
false
</pre>
</td>
			<td>Expose admin console, if set to true no Ingress path restrictions are applied. Otherwise only /realms/ and /resources/ are made available to the public internet. Ref.: https://www.keycloak.org/server/reverseproxy#_exposed_path_recommendations</td>
		</tr>
		<tr>
			<td>config.hostname</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>Hostname. Ref.: https://www.keycloak.org/server/hostname Default: {{ .Values.global.subDomains.keycloak }}.{{ .Values.global.domain }}</td>
		</tr>
		<tr>
			<td>config.logLevel</td>
			<td>string</td>
			<td><pre lang="json">
"INFO"
</pre>
</td>
			<td>Set log level Ref.: https://docs.software-univention.de/keycloak-app/latest/configuration.html#envvar-keycloak-log-level</td>
		</tr>
		<tr>
			<td>config.proxy</td>
			<td>string</td>
			<td><pre lang="json">
"edge"
</pre>
</td>
			<td>Proxy mode. Ref.: https://www.keycloak.org/server/reverseproxy</td>
		</tr>
		<tr>
			<td>containerSecurityContext.allowPrivilegeEscalation</td>
			<td>bool</td>
			<td><pre lang="json">
false
</pre>
</td>
			<td>Enable container privileged escalation.</td>
		</tr>
		<tr>
			<td>containerSecurityContext.capabilities</td>
			<td>object</td>
			<td><pre lang="json">
{
  "drop": [
    "ALL"
  ]
}
</pre>
</td>
			<td>Security capabilities for container.</td>
		</tr>
		<tr>
			<td>containerSecurityContext.enabled</td>
			<td>bool</td>
			<td><pre lang="json">
true
</pre>
</td>
			<td>Enable security context.</td>
		</tr>
		<tr>
			<td>containerSecurityContext.privileged</td>
			<td>bool</td>
			<td><pre lang="json">
false
</pre>
</td>
			<td></td>
		</tr>
		<tr>
			<td>containerSecurityContext.readOnlyRootFilesystem</td>
			<td>bool</td>
			<td><pre lang="json">
true
</pre>
</td>
			<td>Mounts the container's root filesystem as read-only.</td>
		</tr>
		<tr>
			<td>containerSecurityContext.runAsGroup</td>
			<td>int</td>
			<td><pre lang="json">
1000
</pre>
</td>
			<td>Process group id.</td>
		</tr>
		<tr>
			<td>containerSecurityContext.runAsNonRoot</td>
			<td>bool</td>
			<td><pre lang="json">
true
</pre>
</td>
			<td>Run container as user.</td>
		</tr>
		<tr>
			<td>containerSecurityContext.runAsUser</td>
			<td>int</td>
			<td><pre lang="json">
1000
</pre>
</td>
			<td>Process user id.</td>
		</tr>
		<tr>
			<td>containerSecurityContext.seccompProfile.type</td>
			<td>string</td>
			<td><pre lang="json">
"RuntimeDefault"
</pre>
</td>
			<td>Disallow custom Seccomp profile by setting it to RuntimeDefault.</td>
		</tr>
		<tr>
			<td>extraEnvVars</td>
			<td>list</td>
			<td><pre lang="json">
[]
</pre>
</td>
			<td>Array with extra environment variables to add to containers.  extraEnvVars:   - name: FOO     value: "bar" </td>
		</tr>
		<tr>
			<td>extraStartupArgs</td>
			<td>list</td>
			<td><pre lang="json">
[]
</pre>
</td>
			<td>Array with extra startup arguments.</td>
		</tr>
		<tr>
			<td>extraVolumeMounts</td>
			<td>list</td>
			<td><pre lang="json">
[]
</pre>
</td>
			<td>Optionally specify extra list of additional volumeMounts.</td>
		</tr>
		<tr>
			<td>extraVolumes</td>
			<td>list</td>
			<td><pre lang="json">
[]
</pre>
</td>
			<td>Optionally specify extra list of additional volumes</td>
		</tr>
		<tr>
			<td>fullnameOverride</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>Provide a name to substitute for the full names of resources.</td>
		</tr>
		<tr>
			<td>global.domain</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>Define the domain name.</td>
		</tr>
		<tr>
			<td>global.imagePullSecrets</td>
			<td>list</td>
			<td><pre lang="json">
[]
</pre>
</td>
			<td>Credentials to fetch images from private registry Ref: https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/  imagePullSecrets:   - "docker-registry" </td>
		</tr>
		<tr>
			<td>global.imageRegistry</td>
			<td>string</td>
			<td><pre lang="json">
"docker.software-univention.de"
</pre>
</td>
			<td>Container registry address.</td>
		</tr>
		<tr>
			<td>global.nubusDeployment</td>
			<td>bool</td>
			<td><pre lang="json">
false
</pre>
</td>
			<td>Indicates wether this chart is part of a Nubus deployment.</td>
		</tr>
		<tr>
			<td>global.postgresql.connection.host</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td></td>
		</tr>
		<tr>
			<td>global.postgresql.connection.port</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td></td>
		</tr>
		<tr>
			<td>global.subDomains.keycloak</td>
			<td>string</td>
			<td><pre lang="json">
"id"
</pre>
</td>
			<td>Subdomain for keycloak.</td>
		</tr>
		<tr>
			<td>global.subDomains.portal</td>
			<td>string</td>
			<td><pre lang="json">
"portal"
</pre>
</td>
			<td>Subdomain for the Nubus portal.</td>
		</tr>
		<tr>
			<td>image.imagePullPolicy</td>
			<td>string</td>
			<td><pre lang="json">
"IfNotPresent"
</pre>
</td>
			<td>Define an ImagePullPolicy.  Ref.: https://kubernetes.io/docs/concepts/containers/images/#image-pull-policy  "IfNotPresent" => The image is pulled only if it is not already present locally. "Always" => Every time the kubelet launches a container, the kubelet queries the container image registry to             resolve the name to an image digest. If the kubelet has a container image with that exact digest cached             locally, the kubelet uses its cached image; otherwise, the kubelet pulls the image with the resolved             digest, and uses that image to launch the container. "Never" => The kubelet does not try fetching the image. If the image is somehow already present locally, the            kubelet attempts to start the container; otherwise, startup fails </td>
		</tr>
		<tr>
			<td>image.registry</td>
			<td>string</td>
			<td><pre lang="json">
"artifacts.software-univention.de"
</pre>
</td>
			<td>Container registry address. This setting has higher precedence than global.registry.</td>
		</tr>
		<tr>
			<td>image.repository</td>
			<td>string</td>
			<td><pre lang="json">
"nubus/images/keycloak"
</pre>
</td>
			<td>Container repository string.</td>
		</tr>
		<tr>
			<td>image.tag</td>
			<td>string</td>
			<td><pre lang="json">
"0.2.5@sha256:499006904d262bdd334b54583c359c7e34b521697d5fda32ea977d856bfa93d2"
</pre>
</td>
			<td>Define image tag.</td>
		</tr>
		<tr>
			<td>imagePullSecrets</td>
			<td>list</td>
			<td><pre lang="json">
[]
</pre>
</td>
			<td>Credentials to fetch images from private registry Ref: https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/  imagePullSecrets:   - "docker-registry" </td>
		</tr>
		<tr>
			<td>ingress.annotations</td>
			<td>object</td>
			<td><pre lang="json">
{
  "nginx.ingress.kubernetes.io/proxy-buffer-size": "8k",
  "nginx.org/proxy-buffer-size": "8k"
}
</pre>
</td>
			<td>Define custom ingress annotations. annotations:   nginx.ingress.kubernetes.io/rewrite-target: /</td>
		</tr>
		<tr>
			<td>ingress.certManager.enabled</td>
			<td>bool</td>
			<td><pre lang="json">
true
</pre>
</td>
			<td>Enable cert-manager.io annotaion.</td>
		</tr>
		<tr>
			<td>ingress.certManager.issuerRef.kind</td>
			<td>string</td>
			<td><pre lang="json">
"ClusterIssuer"
</pre>
</td>
			<td>Type of Issuer, f.e. "Issuer" or "ClusterIssuer".</td>
		</tr>
		<tr>
			<td>ingress.certManager.issuerRef.name</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>Name of cert-manager.io Issuer resource.</td>
		</tr>
		<tr>
			<td>ingress.enabled</td>
			<td>bool</td>
			<td><pre lang="json">
true
</pre>
</td>
			<td>Enable creation of Ingress.</td>
		</tr>
		<tr>
			<td>ingress.ingressClassName</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>The Ingress controller class name.</td>
		</tr>
		<tr>
			<td>ingress.path</td>
			<td>string</td>
			<td><pre lang="json">
"/"
</pre>
</td>
			<td>Define the Ingress path.</td>
		</tr>
		<tr>
			<td>ingress.pathType</td>
			<td>string</td>
			<td><pre lang="json">
"Prefix"
</pre>
</td>
			<td>Each path in an Ingress is required to have a corresponding path type. Paths that do not include an explicit pathType will fail validation. There are three supported path types:  "ImplementationSpecific" => With this path type, matching is up to the IngressClass. Implementations can treat this                             as a separate pathType or treat it identically to Prefix or Exact path types. "Exact" => Matches the URL path exactly and with case sensitivity. "Prefix" => Matches based on a URL path prefix split by /.  Ref.: https://kubernetes.io/docs/concepts/services-networking/ingress/#path-types</td>
		</tr>
		<tr>
			<td>ingress.paths[0].path</td>
			<td>string</td>
			<td><pre lang="json">
"/admin"
</pre>
</td>
			<td></td>
		</tr>
		<tr>
			<td>ingress.paths[0].pathType</td>
			<td>string</td>
			<td><pre lang="json">
"Prefix"
</pre>
</td>
			<td></td>
		</tr>
		<tr>
			<td>ingress.paths[1].path</td>
			<td>string</td>
			<td><pre lang="json">
"/realms"
</pre>
</td>
			<td></td>
		</tr>
		<tr>
			<td>ingress.paths[1].pathType</td>
			<td>string</td>
			<td><pre lang="json">
"Prefix"
</pre>
</td>
			<td></td>
		</tr>
		<tr>
			<td>ingress.paths[2].path</td>
			<td>string</td>
			<td><pre lang="json">
"/resources"
</pre>
</td>
			<td></td>
		</tr>
		<tr>
			<td>ingress.paths[2].pathType</td>
			<td>string</td>
			<td><pre lang="json">
"Prefix"
</pre>
</td>
			<td></td>
		</tr>
		<tr>
			<td>ingress.tls</td>
			<td>object</td>
			<td><pre lang="json">
{
  "enabled": true,
  "secretName": ""
}
</pre>
</td>
			<td>Secure an Ingress by specifying a Secret that contains a TLS private key and certificate.  Ref.: https://kubernetes.io/docs/concepts/services-networking/ingress/#tls</td>
		</tr>
		<tr>
			<td>ingress.tls.enabled</td>
			<td>bool</td>
			<td><pre lang="json">
true
</pre>
</td>
			<td>Enable TLS/SSL/HTTPS for Ingress.</td>
		</tr>
		<tr>
			<td>ingress.tls.secretName</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>The name of the kubernetes secret which contains a TLS private key and certificate. Hint: This secret is not created by this chart and must be provided.</td>
		</tr>
		<tr>
			<td>keycloak</td>
			<td>object</td>
			<td><pre lang="json">
{
  "auth": {
    "existingSecret": {
      "keyMapping": {
        "adminPassword": null
      },
      "name": null
    },
    "username": ""
  },
  "features": {
    "disabled": [],
    "enabled": [
      "admin-fine-grained-authz",
      "token-exchange"
    ]
  },
  "login": {
    "messages": {
      "de": {
        "accountDisabled": "Das Benutzerkonto ist deaktiviert.",
        "accountExpired": "Das Benutzerkonto ist abgelaufen.",
        "accountLocked": "Das Benutzerkonto ist gesperrt.",
        "loginTitle": "Univention Corporate Server Single-Sign On",
        "loginTitleHtml": "Anmelden bei {{ .Values.global.domain | default \"Nubus\"}}",
        "tryAgain": "Erneut versuchen",
        "updatePasswordTitle": "Das Passwort ist abgelaufen und muss erneuert werden."
      },
      "en": {
        "accountDisabled": "The account is disabled.",
        "accountExpired": "The account has expired.",
        "accountLocked": "The account is locked.",
        "loginTitle": "Univention Corporate Server Single-Sign On",
        "loginTitleHtml": "Login at {{ .Values.global.domain | default \"Nubus\" }}",
        "tryAgain": "Try again",
        "updatePasswordTitle": "The password has expired and must be renewed."
      }
    }
  }
}
</pre>
</td>
			<td>Keycloak settings.</td>
		</tr>
		<tr>
			<td>keycloak.auth.existingSecret</td>
			<td>object</td>
			<td><pre lang="json">
{
  "keyMapping": {
    "adminPassword": null
  },
  "name": null
}
</pre>
</td>
			<td>Keycloak password secret reference.</td>
		</tr>
		<tr>
			<td>keycloak.auth.username</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>Keycloak user.</td>
		</tr>
		<tr>
			<td>keycloak.features.disabled</td>
			<td>list</td>
			<td><pre lang="json">
[]
</pre>
</td>
			<td>Disables a set of one or more features for keycloak.</td>
		</tr>
		<tr>
			<td>keycloak.features.enabled</td>
			<td>list</td>
			<td><pre lang="json">
[
  "admin-fine-grained-authz",
  "token-exchange"
]
</pre>
</td>
			<td>Enables a set of one or more features for keycloak.</td>
		</tr>
		<tr>
			<td>lifecycleHooks</td>
			<td>object</td>
			<td><pre lang="json">
{}
</pre>
</td>
			<td>Lifecycle to automate configuration before or after startup</td>
		</tr>
		<tr>
			<td>livenessProbe.enabled</td>
			<td>bool</td>
			<td><pre lang="json">
true
</pre>
</td>
			<td>Enables kubernetes LivenessProbe.</td>
		</tr>
		<tr>
			<td>livenessProbe.failureThreshold</td>
			<td>int</td>
			<td><pre lang="json">
3
</pre>
</td>
			<td>Number of failed executions until container is terminated.</td>
		</tr>
		<tr>
			<td>livenessProbe.initialDelaySeconds</td>
			<td>int</td>
			<td><pre lang="json">
1
</pre>
</td>
			<td>Delay after container start until LivenessProbe is executed.</td>
		</tr>
		<tr>
			<td>livenessProbe.periodSeconds</td>
			<td>int</td>
			<td><pre lang="json">
5
</pre>
</td>
			<td>Time between probe executions.</td>
		</tr>
		<tr>
			<td>livenessProbe.successThreshold</td>
			<td>int</td>
			<td><pre lang="json">
1
</pre>
</td>
			<td>Number of successful executions after failed ones until container is marked healthy.</td>
		</tr>
		<tr>
			<td>livenessProbe.timeoutSeconds</td>
			<td>int</td>
			<td><pre lang="json">
2
</pre>
</td>
			<td>Timeout for command return.</td>
		</tr>
		<tr>
			<td>nameOverride</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>String to partially override release name.</td>
		</tr>
		<tr>
			<td>nodeSelector</td>
			<td>object</td>
			<td><pre lang="json">
{}
</pre>
</td>
			<td>Node labels for pod assignment Ref: https://kubernetes.io/docs/user-guide/node-selection/</td>
		</tr>
		<tr>
			<td>podAnnotations</td>
			<td>object</td>
			<td><pre lang="json">
{}
</pre>
</td>
			<td>Pod Annotations. Ref: https://kubernetes.io/docs/concepts/overview/working-with-objects/annotations/</td>
		</tr>
		<tr>
			<td>podLabels</td>
			<td>object</td>
			<td><pre lang="json">
{}
</pre>
</td>
			<td>Pod Labels. Ref: https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/</td>
		</tr>
		<tr>
			<td>podManagementPolicy</td>
			<td>string</td>
			<td><pre lang="json">
"OrderedReady"
</pre>
</td>
			<td>Pod management policy. Ref: https://kubernetes.io/docs/concepts/workloads/controllers/statefulset/</td>
		</tr>
		<tr>
			<td>podSecurityContext.enabled</td>
			<td>bool</td>
			<td><pre lang="json">
true
</pre>
</td>
			<td>Enable security context.</td>
		</tr>
		<tr>
			<td>podSecurityContext.fsGroup</td>
			<td>int</td>
			<td><pre lang="json">
1000
</pre>
</td>
			<td>If specified, all processes of the container are also part of the supplementary group</td>
		</tr>
		<tr>
			<td>podSecurityContext.fsGroupChangePolicy</td>
			<td>string</td>
			<td><pre lang="json">
"Always"
</pre>
</td>
			<td>Change ownership and permission of the volume before being exposed inside a Pod.</td>
		</tr>
		<tr>
			<td>postgresql</td>
			<td>object</td>
			<td><pre lang="json">
{
  "auth": {
    "database": "",
    "existingSecret": {
      "keyMapping": {
        "password": null
      },
      "name": null
    },
    "username": ""
  },
  "connection": {
    "host": "",
    "port": ""
  }
}
</pre>
</td>
			<td>PostgreSQL settings.</td>
		</tr>
		<tr>
			<td>postgresql.auth.database</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>PostgreSQL database.</td>
		</tr>
		<tr>
			<td>postgresql.auth.existingSecret</td>
			<td>object</td>
			<td><pre lang="json">
{
  "keyMapping": {
    "password": null
  },
  "name": null
}
</pre>
</td>
			<td>PostgreSQL password secret reference.</td>
		</tr>
		<tr>
			<td>postgresql.auth.username</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>PostgreSQL user.</td>
		</tr>
		<tr>
			<td>postgresql.connection</td>
			<td>object</td>
			<td><pre lang="json">
{
  "host": "",
  "port": ""
}
</pre>
</td>
			<td>Connection parameters.</td>
		</tr>
		<tr>
			<td>postgresql.connection.host</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>PostgreSQL host.</td>
		</tr>
		<tr>
			<td>postgresql.connection.port</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>PostgreSQL port.</td>
		</tr>
		<tr>
			<td>readinessProbe.enabled</td>
			<td>bool</td>
			<td><pre lang="json">
true
</pre>
</td>
			<td>Enables kubernetes ReadinessProbe.</td>
		</tr>
		<tr>
			<td>readinessProbe.failureThreshold</td>
			<td>int</td>
			<td><pre lang="json">
5
</pre>
</td>
			<td>Number of failed executions until container is terminated.</td>
		</tr>
		<tr>
			<td>readinessProbe.initialDelaySeconds</td>
			<td>int</td>
			<td><pre lang="json">
1
</pre>
</td>
			<td>Delay after container start until ReadinessProbe is executed.</td>
		</tr>
		<tr>
			<td>readinessProbe.periodSeconds</td>
			<td>int</td>
			<td><pre lang="json">
5
</pre>
</td>
			<td>Time between probe executions.</td>
		</tr>
		<tr>
			<td>readinessProbe.successThreshold</td>
			<td>int</td>
			<td><pre lang="json">
1
</pre>
</td>
			<td>Number of successful executions after failed ones until container is marked healthy.</td>
		</tr>
		<tr>
			<td>readinessProbe.timeoutSeconds</td>
			<td>int</td>
			<td><pre lang="json">
2
</pre>
</td>
			<td>Timeout for command return.</td>
		</tr>
		<tr>
			<td>replicaCount</td>
			<td>int</td>
			<td><pre lang="json">
1
</pre>
</td>
			<td>Set the amount of replicas of deployment.</td>
		</tr>
		<tr>
			<td>resources.limits.memory</td>
			<td>string</td>
			<td><pre lang="json">
"1Gi"
</pre>
</td>
			<td>The max number of RAM to consume.</td>
		</tr>
		<tr>
			<td>resources.requests.cpu</td>
			<td>string</td>
			<td><pre lang="json">
"100m"
</pre>
</td>
			<td>The number of CPUs which has to be available on the scheduled node.</td>
		</tr>
		<tr>
			<td>resources.requests.memory</td>
			<td>string</td>
			<td><pre lang="json">
"512Mi"
</pre>
</td>
			<td>The number of RAM which has to be available on the scheduled node.</td>
		</tr>
		<tr>
			<td>service.annotations</td>
			<td>object</td>
			<td><pre lang="json">
{}
</pre>
</td>
			<td>Additional custom annotations</td>
		</tr>
		<tr>
			<td>service.clusterIP</td>
			<td>string</td>
			<td><pre lang="json">
"None"
</pre>
</td>
			<td></td>
		</tr>
		<tr>
			<td>service.enabled</td>
			<td>bool</td>
			<td><pre lang="json">
true
</pre>
</td>
			<td>Enable kubernetes service creation.</td>
		</tr>
		<tr>
			<td>service.ports.http.containerPort</td>
			<td>int</td>
			<td><pre lang="json">
8080
</pre>
</td>
			<td>Internal port.</td>
		</tr>
		<tr>
			<td>service.ports.http.port</td>
			<td>int</td>
			<td><pre lang="json">
8080
</pre>
</td>
			<td>Accessible port.</td>
		</tr>
		<tr>
			<td>service.ports.http.protocol</td>
			<td>string</td>
			<td><pre lang="json">
"TCP"
</pre>
</td>
			<td>service protocol.</td>
		</tr>
		<tr>
			<td>service.ports.https.containerPort</td>
			<td>int</td>
			<td><pre lang="json">
8443
</pre>
</td>
			<td>Internal port.</td>
		</tr>
		<tr>
			<td>service.ports.https.port</td>
			<td>int</td>
			<td><pre lang="json">
8443
</pre>
</td>
			<td>Accessible port.</td>
		</tr>
		<tr>
			<td>service.ports.https.protocol</td>
			<td>string</td>
			<td><pre lang="json">
"TCP"
</pre>
</td>
			<td>service protocol.</td>
		</tr>
		<tr>
			<td>service.ports.ispn.containerPort</td>
			<td>int</td>
			<td><pre lang="json">
7800
</pre>
</td>
			<td>Internal port.</td>
		</tr>
		<tr>
			<td>service.ports.ispn.port</td>
			<td>int</td>
			<td><pre lang="json">
7800
</pre>
</td>
			<td>Accessible port.</td>
		</tr>
		<tr>
			<td>service.ports.ispn.protocol</td>
			<td>string</td>
			<td><pre lang="json">
"TCP"
</pre>
</td>
			<td>service protocol.</td>
		</tr>
		<tr>
			<td>service.type</td>
			<td>string</td>
			<td><pre lang="json">
"ClusterIP"
</pre>
</td>
			<td>Choose the kind of Service, one of "ClusterIP", "NodePort" or "LoadBalancer".</td>
		</tr>
		<tr>
			<td>serviceAccount.annotations</td>
			<td>object</td>
			<td><pre lang="json">
{}
</pre>
</td>
			<td>Additional custom annotations for the ServiceAccount.</td>
		</tr>
		<tr>
			<td>serviceAccount.automountServiceAccountToken</td>
			<td>bool</td>
			<td><pre lang="json">
false
</pre>
</td>
			<td>Allows auto mount of ServiceAccountToken on the serviceAccount created. Can be set to false if pods using this serviceAccount do not need to use K8s API.</td>
		</tr>
		<tr>
			<td>serviceAccount.create</td>
			<td>bool</td>
			<td><pre lang="json">
true
</pre>
</td>
			<td>Enable creation of ServiceAccount for pod.</td>
		</tr>
		<tr>
			<td>serviceAccount.labels</td>
			<td>object</td>
			<td><pre lang="json">
{}
</pre>
</td>
			<td>Additional custom labels for the ServiceAccount.</td>
		</tr>
		<tr>
			<td>startupProbe.enabled</td>
			<td>bool</td>
			<td><pre lang="json">
true
</pre>
</td>
			<td>Enables kubernetes ReadinessProbe.</td>
		</tr>
		<tr>
			<td>startupProbe.failureThreshold</td>
			<td>int</td>
			<td><pre lang="json">
10
</pre>
</td>
			<td>Number of failed executions until container is terminated.</td>
		</tr>
		<tr>
			<td>startupProbe.initialDelaySeconds</td>
			<td>int</td>
			<td><pre lang="json">
30
</pre>
</td>
			<td>Delay after container start until ReadinessProbe is executed.</td>
		</tr>
		<tr>
			<td>startupProbe.periodSeconds</td>
			<td>int</td>
			<td><pre lang="json">
20
</pre>
</td>
			<td>Time between probe executions.</td>
		</tr>
		<tr>
			<td>startupProbe.successThreshold</td>
			<td>int</td>
			<td><pre lang="json">
1
</pre>
</td>
			<td>Number of successful executions after failed ones until container is marked healthy.</td>
		</tr>
		<tr>
			<td>startupProbe.timeoutSeconds</td>
			<td>int</td>
			<td><pre lang="json">
5
</pre>
</td>
			<td>Timeout for command return.</td>
		</tr>
		<tr>
			<td>terminationGracePeriodSeconds</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>In seconds, time the given to the pod needs to terminate gracefully. Ref: https://kubernetes.io/docs/concepts/workloads/pods/pod/#termination-of-pods</td>
		</tr>
		<tr>
			<td>theme.colors.primary</td>
			<td>string</td>
			<td><pre lang="json">
"#5e27dd"
</pre>
</td>
			<td>Primary color.</td>
		</tr>
		<tr>
			<td>theme.colors.primary15</td>
			<td>string</td>
			<td><pre lang="json">
"#e7dffa"
</pre>
</td>
			<td>Primary color 15%.</td>
		</tr>
		<tr>
			<td>theme.favIcon</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>Logo as SVG content.</td>
		</tr>
		<tr>
			<td>theme.texts.productName</td>
			<td>string</td>
			<td><pre lang="json">
"openDesk"
</pre>
</td>
			<td>Branding name.</td>
		</tr>
		<tr>
			<td>theme.univentionCustomTheme</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td></td>
		</tr>
		<tr>
			<td>theme.univentionTheme</td>
			<td>string</td>
			<td><pre lang="json">
""
</pre>
</td>
			<td>URI to the base theme</td>
		</tr>
		<tr>
			<td>tolerations</td>
			<td>list</td>
			<td><pre lang="json">
[]
</pre>
</td>
			<td>Tolerations for pod assignment Ref: https://kubernetes.io/docs/concepts/configuration/taint-and-toleration/</td>
		</tr>
		<tr>
			<td>topologySpreadConstraints</td>
			<td>list</td>
			<td><pre lang="json">
[]
</pre>
</td>
			<td>Topology spread constraints rely on node labels to identify the topology domain(s) that each Node is in Ref: https://kubernetes.io/docs/concepts/workloads/pods/pod-topology-spread-constraints/  topologySpreadConstraints:   - maxSkew: 1     topologyKey: failure-domain.beta.kubernetes.io/zone     whenUnsatisfiable: DoNotSchedule</td>
		</tr>
		<tr>
			<td>updateStrategy.type</td>
			<td>string</td>
			<td><pre lang="json">
"RollingUpdate"
</pre>
</td>
			<td>Set to Recreate if you use persistent volume that cannot be mounted by more than one pods to make sure the pods is destroyed first.</td>
		</tr>
	</tbody>
</table>

