<!--
SPDX-FileCopyrightText: 2023 Univention GmbH
SPDX-License-Identifier: AGPL-3.0-only
-->
# ums-keycloak

Deploy the Keycloak component of the Univention Management Stack into Kubernetes

## Installing the Chart

To install the chart with the release name `my-release`, you have two options:

### Install via Repository
```console
helm repo add univention-keycloak https://gitlab.souvap-univention.de/api/v4/projects/161/packages/helm/stable
helm install my-release --version 1.0.2 univention-keycloak/ums-keycloak
```

### Install via OCI Registry
```console
helm repo add univention-keycloak oci://registry.souvap-univention.de/souvap/tooling/charts/univention-keycloak
helm install my-release --version 1.0.2 univention-keycloak/ums-keycloak
```

## Requirements

| Repository | Name | Version |
|------------|------|---------|
| oci://registry.souvap-univention.de/souvap/tooling/charts/bitnami-charts | common | ^2.x.x |

## Values

| Key | Type | Default | Description |
|-----|------|---------|-------------|
| affinity | object | `{}` | Affinity for pod assignment Ref: https://kubernetes.io/docs/concepts/configuration/assign-pod-node/#affinity-and-anti-affinity Note: podAffinityPreset, podAntiAffinityPreset, and nodeAffinityPreset will be ignored when it's set. |
| commonAnnotations | object | `{}` | Additional custom annotations to add to all deployed objects. |
| commonLabels | object | `{}` | Additional custom labels to add to all deployed objects. |
| config.admin.existingSecret.key | string | `""` | The key which identifies the password in the secret file. |
| config.admin.existingSecret.name | string | `""` | Existing secret which contains the keycloak admin password. |
| config.admin.password | string | `""` | Admin password or as secret via existingSecret. |
| config.admin.username | string | `"kcadmin"` | Admin username. |
| config.database.existingSecret.key | string | `"databasePassword"` | The key which identifies the password in the secret file. |
| config.database.existingSecret.name | string | `""` | Existing secret which contains the keycloak database password. |
| config.database.host | string | `"postgresql"` | Database host. |
| config.database.name | string | `"keycloak"` | Database name. |
| config.database.password | string | `""` | Database password or as secret via existingSecret. |
| config.database.port | int | `5432` | Database port. |
| config.database.properties | string | `""` | Database properties. |
| config.database.type | string | `"postgres"` | Database vendor. Possible values: dev-file (default), dev-mem, mariadb, mssql, mysql, oracle, postgres |
| config.database.username | string | `"keycloak_user"` | Database username. |
| config.features.disabled | list | `[]` | Disables a set of one or more features for keycloak. |
| config.features.enabled | list | `["admin-fine-grained-authz","token-exchange"]` | Enables a set of one or more features for keycloak. |
| config.hostname | string | `""` | Hostname. Ref.: https://www.keycloak.org/server/hostname Default: {{ .Values.global.hosts.keycloak }}.{{ .Values.global.domain }} |
| config.proxy | string | `"edge"` | Proxy mode. Ref.: https://www.keycloak.org/server/reverseproxy |
| containerSecurityContext.allowPrivilegeEscalation | bool | `false` | Enable container privileged escalation. |
| containerSecurityContext.capabilities | object | `{"drop":["ALL"]}` | Security capabilities for container. |
| containerSecurityContext.enabled | bool | `true` | Enable security context. |
| containerSecurityContext.readOnlyRootFilesystem | bool | `false` | Mounts the container's root filesystem as read-only. |
| containerSecurityContext.runAsGroup | int | `1000` | Process group id. |
| containerSecurityContext.runAsNonRoot | bool | `true` | Run container as user. |
| containerSecurityContext.runAsUser | int | `1000` | Process user id. |
| containerSecurityContext.seccompProfile.type | string | `"RuntimeDefault"` | Disallow custom Seccomp profile by setting it to RuntimeDefault. |
| extraEnvVars | list | `[]` | Array with extra environment variables to add to containers.  extraEnvVars:   - name: FOO     value: "bar"  |
| extraStartupArgs | list | `[]` | Array with extra startup arguments. |
| extraVolumeMounts | list | `[]` | Optionally specify extra list of additional volumeMounts. |
| extraVolumes | list | `[]` | Optionally specify extra list of additional volumes |
| fullnameOverride | string | `""` | Provide a name to substitute for the full names of resources. |
| global.domain | string | `"open-desk.cloud"` | Define the domain name. |
| global.hosts.keycloak | string | `"id"` | Subdomain for Keycloak. |
| global.imagePullSecrets | list | `[]` | Credentials to fetch images from private registry Ref: https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/  imagePullSecrets:   - "docker-registry"  |
| global.imageRegistry | string | `"docker.software-univention.de"` | Container registry address. |
| image.imagePullPolicy | string | `"IfNotPresent"` | Define an ImagePullPolicy.  Ref.: https://kubernetes.io/docs/concepts/containers/images/#image-pull-policy  "IfNotPresent" => The image is pulled only if it is not already present locally. "Always" => Every time the kubelet launches a container, the kubelet queries the container image registry to             resolve the name to an image digest. If the kubelet has a container image with that exact digest cached             locally, the kubelet uses its cached image; otherwise, the kubelet pulls the image with the resolved             digest, and uses that image to launch the container. "Never" => The kubelet does not try fetching the image. If the image is somehow already present locally, the            kubelet attempts to start the container; otherwise, startup fails  |
| image.registry | string | `""` | Container registry address. This setting has higher precedence than global.registry. |
| image.repository | string | `"keycloak-keycloak"` | Container repository string. |
| image.tag | string | `"22.0.3-ucs2@sha256:cf11399add8a9e73622fb52aa225a207ebc5b07514b8d634f591e5b540ba9731"` | Define image tag. |
| imagePullSecrets | list | `[]` | Credentials to fetch images from private registry Ref: https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/  imagePullSecrets:   - "docker-registry"  |
| ingress.annotations | object | `{}` | Define custom ingress annotations. annotations:   nginx.ingress.kubernetes.io/rewrite-target: / |
| ingress.enabled | bool | `false` | Enable creation of Ingress. |
| ingress.host | string | `""` | Define the Fully Qualified Domain Name (FQDN) where application should be reachable. |
| ingress.ingressClassName | string | `"nginx"` | The Ingress controller class name. |
| ingress.path | string | `"/"` | Define the Ingress path. |
| ingress.pathType | string | `"Prefix"` | Each path in an Ingress is required to have a corresponding path type. Paths that do not include an explicit pathType will fail validation. There are three supported path types:  "ImplementationSpecific" => With this path type, matching is up to the IngressClass. Implementations can treat this                             as a separate pathType or treat it identically to Prefix or Exact path types. "Exact" => Matches the URL path exactly and with case sensitivity. "Prefix" => Matches based on a URL path prefix split by /.  Ref.: https://kubernetes.io/docs/concepts/services-networking/ingress/#path-types |
| ingress.tls | object | `{"enabled":true,"secretName":""}` | Secure an Ingress by specifying a Secret that contains a TLS private key and certificate.  Ref.: https://kubernetes.io/docs/concepts/services-networking/ingress/#tls |
| ingress.tls.enabled | bool | `true` | Enable TLS/SSL/HTTPS for Ingress. |
| ingress.tls.secretName | string | `""` | The name of the kubernetes secret which contains a TLS private key and certificate. Hint: This secret is not created by this chart and must be provided. |
| lifecycleHooks | object | `{}` | Lifecycle to automate configuration before or after startup |
| livenessProbe.enabled | bool | `true` | Enables kubernetes LivenessProbe. |
| livenessProbe.failureThreshold | int | `3` | Number of failed executions until container is terminated. |
| livenessProbe.initialDelaySeconds | int | `1` | Delay after container start until LivenessProbe is executed. |
| livenessProbe.periodSeconds | int | `5` | Time between probe executions. |
| livenessProbe.successThreshold | int | `1` | Number of successful executions after failed ones until container is marked healthy. |
| livenessProbe.timeoutSeconds | int | `2` | Timeout for command return. |
| nameOverride | string | `""` | String to partially override release name. |
| nodeSelector | object | `{}` | Node labels for pod assignment Ref: https://kubernetes.io/docs/user-guide/node-selection/ |
| podAnnotations | object | `{}` | Pod Annotations. Ref: https://kubernetes.io/docs/concepts/overview/working-with-objects/annotations/ |
| podLabels | object | `{}` | Pod Labels. Ref: https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/ |
| podSecurityContext.enabled | bool | `true` | Enable security context. |
| podSecurityContext.fsGroup | int | `1000` | If specified, all processes of the container are also part of the supplementary group |
| podSecurityContext.fsGroupChangePolicy | string | `"Always"` | Change ownership and permission of the volume before being exposed inside a Pod. |
| readinessProbe.enabled | bool | `true` | Enables kubernetes ReadinessProbe. |
| readinessProbe.failureThreshold | int | `5` | Number of failed executions until container is terminated. |
| readinessProbe.initialDelaySeconds | int | `1` | Delay after container start until ReadinessProbe is executed. |
| readinessProbe.periodSeconds | int | `5` | Time between probe executions. |
| readinessProbe.successThreshold | int | `1` | Number of successful executions after failed ones until container is marked healthy. |
| readinessProbe.timeoutSeconds | int | `2` | Timeout for command return. |
| replicaCount | int | `1` | Set the amount of replicas of deployment. |
| resources.limits.memory | string | `"1Gi"` | The max number of RAM to consume. |
| resources.requests.cpu | string | `"100m"` | The number of CPUs which has to be available on the scheduled node. |
| resources.requests.memory | string | `"512Mi"` | The number of RAM which has to be available on the scheduled node. |
| service.annotations | object | `{}` | Additional custom annotations |
| service.enabled | bool | `true` | Enable kubernetes service creation. |
| service.ports.http.containerPort | int | `8080` | Internal port. |
| service.ports.http.port | int | `8080` | Accessible port. |
| service.ports.http.protocol | string | `"TCP"` | service protocol. |
| service.ports.https.containerPort | int | `8443` | Internal port. |
| service.ports.https.port | int | `8443` | Accessible port. |
| service.ports.https.protocol | string | `"TCP"` | service protocol. |
| service.type | string | `"ClusterIP"` | Choose the kind of Service, one of "ClusterIP", "NodePort" or "LoadBalancer". |
| serviceAccount.annotations | object | `{}` | Additional custom annotations for the ServiceAccount. |
| serviceAccount.automountServiceAccountToken | bool | `false` | Allows auto mount of ServiceAccountToken on the serviceAccount created. Can be set to false if pods using this serviceAccount do not need to use K8s API. |
| serviceAccount.create | bool | `true` | Enable creation of ServiceAccount for pod. |
| serviceAccount.labels | object | `{}` | Additional custom labels for the ServiceAccount. |
| startupProbe.enabled | bool | `true` | Enables kubernetes ReadinessProbe. |
| startupProbe.failureThreshold | int | `10` | Number of failed executions until container is terminated. |
| startupProbe.initialDelaySeconds | int | `30` | Delay after container start until ReadinessProbe is executed. |
| startupProbe.periodSeconds | int | `20` | Time between probe executions. |
| startupProbe.successThreshold | int | `1` | Number of successful executions after failed ones until container is marked healthy. |
| startupProbe.timeoutSeconds | int | `5` | Timeout for command return. |
| terminationGracePeriodSeconds | string | `""` | In seconds, time the given to the pod needs to terminate gracefully. Ref: https://kubernetes.io/docs/concepts/workloads/pods/pod/#termination-of-pods |
| theme.colors.primary | string | `"#5e27dd"` | Primary color. |
| theme.colors.primary15 | string | `"#e7dffa"` | Primary color 15%. |
| theme.favIcon | string | `"https://ums.example.org/favicon.ico"` | Logo as SVG content. |
| theme.texts.productName | string | `"openDesk"` | Branding name. |
| theme.univentionCustomTheme | string | `"https://ums.example.org/login/css/custom.css"` |  |
| theme.univentionTheme | string | `"https://ums.example.org/univention/theme.css"` | URI to the base theme |
| tolerations | list | `[]` | Tolerations for pod assignment Ref: https://kubernetes.io/docs/concepts/configuration/taint-and-toleration/ |
| topologySpreadConstraints | list | `[]` | Topology spread constraints rely on node labels to identify the topology domain(s) that each Node is in Ref: https://kubernetes.io/docs/concepts/workloads/pods/pod-topology-spread-constraints/  topologySpreadConstraints:   - maxSkew: 1     topologyKey: failure-domain.beta.kubernetes.io/zone     whenUnsatisfiable: DoNotSchedule |
| updateStrategy.type | string | `"RollingUpdate"` | Set to Recreate if you use persistent volume that cannot be mounted by more than one pods to make sure the pods is destroyed first. |

## Uninstalling the Chart

To install the release with name `my-release`:

```bash
helm uninstall my-release
```

## Signing

### Chart

Helm charts are signed with helm native signing method. You can verify the charts against this GPG key:

```
-----BEGIN PGP PUBLIC KEY BLOCK-----

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
XD85LYkPgsax8WY=
=cFEh
-----END PGP PUBLIC KEY BLOCK-----
```

### Images

Container images are signed via [cosign](https://github.com/sigstore/cosign) and can be verified with:

```
-----BEGIN PUBLIC KEY-----
MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAETIpFd4qC4ThMC4PmFIPulqFVhfD/
1ujC+TfS7hTH7X06tleO6a2Gl2Vkn0k88A4LDmvFfNFoHRnEhVsjcLDNDw==
-----END PUBLIC KEY-----

```

```
cosign verify --key cosign.pub --insecure-ignore-tlog <image>
```

## License
This project uses the following license: Apache-2.0

## Copyright
Copyright (C) 2023 Bundesministerium des Innern und für Heimat, PG ZenDiS "Projektgruppe für Aufbau ZenDiS"
