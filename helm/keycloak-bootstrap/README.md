<!--
SPDX-FileCopyrightText: 2023 Univention GmbH
SPDX-License-Identifier: AGPL-3.0-only
-->
# keycloak-bootstrap

A Helm chart to bootstrap Keycloak

## Installing the Chart

To install the chart with the release name `my-release`:

```console
helm repo add ${CI_PROJECT_NAME} ${CI_SERVER_PROTOCOL}://${CI_SERVER_HOST}/api/v4/projects/${CI_PROJECT_ID}/packages/helm/stable
helm install my-release ${CI_PROJECT_NAME}/keycloak-bootstrap
```

## Requirements

| Repository | Name | Version |
|------------|------|---------|
| oci://artifacts.software-univention.de/nubus/charts | nubus-common | 0.28.0 |

## Values

| Key | Type | Default | Description |
|-----|------|---------|-------------|
| additionalAnnotations | object | `{}` | Additional custom annotations to add to all deployed objects. |
| additionalLabels | object | `{}` | Additional custom labels to add to all deployed objects. |
| affinity | object | `{}` | Affinity for pod assignment Ref: https://kubernetes.io/docs/concepts/configuration/assign-pod-node/#affinity-and-anti-affinity Note: podAffinityPreset, podAntiAffinityPreset, and  nodeAffinityPreset will be ignored when it's set |
| bootstrap.ldapMappers | object | `{}` (empty dict) | Configure additional LDAP attributes to map to the Keycloak User object. This is a dict where each key is the name of the mapper and the value contains the mapper configuration. The attribute name is used as both the LDAP attribute and the Keycloak user model attribute.  By default, when "Import Users" is enabled in Keycloak (ldapImportUsers: true), user attributes are only imported on the first login and cached in Keycloak's database. For mutable attributes that may change in LDAP (e.g., OXContextId), set alwaysReadFromLdap to true to ensure the value is always read fresh from LDAP.  Example:   ldapMappers:     OXContextId:       alwaysReadFromLdap: true     department:       alwaysReadFromLdap: false  Each mapper entry supports:   alwaysReadFromLdap: (boolean, default: true) When true, the attribute value     is always read from LDAP instead of Keycloak's cache. Recommended for     attributes that may change over time.  Note: This setting is only for internal use and not recommended for general usage. |
| bootstrap.loginLinks | list | `[]` |  |
| bootstrap.twoFactorAuthentication.enabled | bool | `false` | Enable Keycloak's built-in 2FA support |
| bootstrap.twoFactorAuthentication.group | string | `""` | LDAP group DN which membership enables 2FA for users |
| bootstrap.userFederation.ldapImportUsers | bool | `true` | Enable the option "Import Users" for the user federation LDAP provider. This option only takes effect on the first deployment. |
| cleanup.deletePodsOnSuccess | bool | `false` | Keep Pods/Job logs after successful run. |
| cleanup.keepPVCOnDelete | bool | `false` | Keep persistence on delete of this release. |
| config.debug.enabled | bool | `false` | Enable debug output of included Ansible scripts |
| config.debug.pauseBeforeScriptStart | int | `0` | Seconds for the job to pause before starting the actual bootstrapping. |
| config.saml.serviceProviderHostname | string | `""` | Service provider public hostname |
| containerSecurityContext.allowPrivilegeEscalation | bool | `false` | Enable container privileged escalation. |
| containerSecurityContext.capabilities | object | `{"drop":["ALL"]}` | Security capabilities for container. |
| containerSecurityContext.enabled | bool | `true` | Enable security context. |
| containerSecurityContext.privileged | bool | `false` |  |
| containerSecurityContext.readOnlyRootFilesystem | bool | `true` | Mounts the container's root filesystem as read-only. |
| containerSecurityContext.runAsGroup | int | `1000` | Process group id. |
| containerSecurityContext.runAsNonRoot | bool | `true` | Run container as user. |
| containerSecurityContext.runAsUser | int | `1000` | Process user id. |
| containerSecurityContext.seccompProfile.type | string | `"RuntimeDefault"` | Disallow custom Seccomp profile by setting it to RuntimeDefault. |
| extraEnvVars | list | `[]` | Array with extra environment variables to add to containers.  extraEnvVars:   - name: FOO     value: "bar"  |
| extraVolumeMounts | list | `[]` | Optionally specify an extra list of additional volumeMounts. |
| extraVolumes | list | `[]` | Optionally specify an extra list of additional volumes. |
| global.domain | string | `""` |  |
| global.imagePullPolicy | string | `nil` | Define an ImagePullPolicy.  Ref.: https://kubernetes.io/docs/concepts/containers/images/#image-pull-policy  "IfNotPresent" => The image is pulled only if it is not already present locally. "Always" => Every time the kubelet launches a container, the kubelet queries the container image registry to             resolve the name to an image digest. If the kubelet has a container image with that exact digest cached             locally, the kubelet uses its cached image; otherwise, the kubelet pulls the image with the resolved             digest, and uses that image to launch the container. "Never" => The kubelet does not try fetching the image. If the image is somehow already present locally, the            kubelet attempts to start the container; otherwise, startup fails. |
| global.imagePullSecrets | list | `[]` | Credentials to fetch images from private registry Ref: https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/  imagePullSecrets:   - "docker-registry"  |
| global.imageRegistry | string | `"artifacts.software-univention.de"` | Container registry address. |
| global.nubusDeployment | bool | `false` | Indicates wether this chart is part of a Nubus deployment. |
| global.subDomains.keycloak | string | `""` |  |
| global.subDomains.portal | string | `"portal"` |  |
| image.pullPolicy | string | `nil` | Define an ImagePullPolicy.  Ref.: https://kubernetes.io/docs/concepts/containers/images/#image-pull-policy  "IfNotPresent" => The image is pulled only if it is not already present locally. "Always" => Every time the kubelet launches a container, the kubelet queries the container image registry to             resolve the name to an image digest. If the kubelet has a container image with that exact digest cached             locally, the kubelet uses its cached image; otherwise, the kubelet pulls the image with the resolved             digest, and uses that image to launch the container. "Never" => The kubelet does not try fetching the image. If the image is somehow already present locally, the            kubelet attempts to start the container; otherwise, startup fails  |
| image.registry | string | `""` | Container registry address. This setting has higher precedence than global.registry. |
| image.repository | string | `"nubus-dev/images/keycloak-bootstrap"` | Container repository string. |
| image.tag | string | `"latest"` | Define image tag. |
| imagePullSecrets | list | `[]` | Credentials to fetch images from private registry Ref: https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/  imagePullSecrets:   - "docker-registry"  |
| keycloak | object | `{"auth":{"existingSecret":{"keyMapping":{"adminPassword":null},"name":null},"password":"","realm":"","username":""},"connection":{"host":"","port":""}}` | Keycloak settings. |
| keycloak.auth.existingSecret | object | `{"keyMapping":{"adminPassword":null},"name":null}` | Keycloak password secret reference. |
| keycloak.auth.password | string | `""` | Keycloak password. |
| keycloak.auth.realm | string | `""` | Keycloak realm. |
| keycloak.auth.username | string | `""` | Keycloak user. |
| keycloak.connection | object | `{"host":"","port":""}` | Connection parameters. |
| keycloak.connection.host | string | `""` | Keycloak host. |
| keycloak.connection.port | string | `""` | Keycloak port. |
| ldap | object | `{"auth":{"bindDn":"","existingSecret":{"keyMapping":{"password":null},"name":null},"password":""},"connection":{"host":"","port":"","protocol":"","tls":{"ca":{"secretKeyRef":{"key":"ca.crt","name":""}},"cert":{"secretKeyRef":{"key":"tls.crt","name":""}},"enabled":false,"key":{"secretKeyRef":{"key":"tls.key","name":""}}}}}` | LDAP settings. |
| ldap.auth | object | `{"bindDn":"","existingSecret":{"keyMapping":{"password":null},"name":null},"password":""}` | LDAP authentication parameters. |
| ldap.auth.bindDn | string | `""` | LDAP bind DN. (user to authenticate with LDAP server) |
| ldap.auth.existingSecret | object | `{"keyMapping":{"password":null},"name":null}` | LDAP password secret reference. |
| ldap.auth.password | string | `""` | LDAP bind password. |
| ldap.connection | object | `{"host":"","port":"","protocol":"","tls":{"ca":{"secretKeyRef":{"key":"ca.crt","name":""}},"cert":{"secretKeyRef":{"key":"tls.crt","name":""}},"enabled":false,"key":{"secretKeyRef":{"key":"tls.key","name":""}}}}` | LDAP connection parameters. |
| ldap.connection.host | string | `""` | LDAP host. |
| ldap.connection.port | string | `""` | LDAP port. |
| ldap.connection.protocol | string | `""` | LDAP protocol. |
| ldap.connection.tls | object | `{"ca":{"secretKeyRef":{"key":"ca.crt","name":""}},"cert":{"secretKeyRef":{"key":"tls.crt","name":""}},"enabled":false,"key":{"secretKeyRef":{"key":"tls.key","name":""}}}` | TLS settings. |
| ldap.connection.tls.ca | object | `{"secretKeyRef":{"key":"ca.crt","name":""}}` | TLS CA secret reference. |
| ldap.connection.tls.cert | object | `{"secretKeyRef":{"key":"tls.crt","name":""}}` | TLS certificate secret reference. |
| ldap.connection.tls.enabled | bool | `false` | Enable TLS. |
| ldap.connection.tls.key | object | `{"secretKeyRef":{"key":"tls.key","name":""}}` | TLS key secret reference. |
| nodeSelector | object | `{}` | Node labels for pod assignment Ref: https://kubernetes.io/docs/user-guide/node-selection/ |
| oidc | object | `{"relyingParty":{"umcServer":{"clientSecret":{"existingSecret":{"keyMapping":{"password":null},"name":null},"password":null}}}}` | Configuration for Open ID Connect Clients |
| podAnnotations | object | `{}` | Pod Annotations. Ref: https://kubernetes.io/docs/concepts/overview/working-with-objects/annotations/ |
| podLabels | object | `{}` | Pod Labels. Ref: https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/ |
| podSecurityContext.enabled | bool | `false` | Enable security context. |
| podSecurityContext.fsGroup | int | `1000` | If specified, all processes of the container are also part of the supplementary group. |
| podSecurityContext.fsGroupChangePolicy | string | `"Always"` | Change ownership and permission of the volume before being exposed inside a Pod. |
| resources.limits.memory | string | `"1Gi"` | The max amount of RAM to consume. |
| resources.requests.cpu | string | `"100m"` | The amount of CPUs which has to be available on the scheduled node. |
| resources.requests.memory | string | `"256Mi"` | The amount of RAM which has to be available on the scheduled node. |
| serviceAccount.annotations | object | `{}` | Additional custom annotations for the ServiceAccount. |
| serviceAccount.automountServiceAccountToken | bool | `false` | Allows auto mount of ServiceAccountToken on the serviceAccount created. Can be set to false if pods using this serviceAccount do not need to use K8s API. |
| serviceAccount.create | bool | `true` | Enable creation of ServiceAccount for pod. |
| serviceAccount.labels | object | `{}` | Additional custom labels for the ServiceAccount. |
| terminationGracePeriodSeconds | string | `""` | In seconds, time the given to the pod needs to terminate gracefully. Ref: https://kubernetes.io/docs/concepts/workloads/pods/pod/#termination-of-pods |
| tolerations | list | `[]` | Tolerations for pod assignment Ref: https://kubernetes.io/docs/concepts/configuration/taint-and-toleration/ |
| topologySpreadConstraints | list | `[]` | Topology spread constraints rely on node labels to identify the topology domain(s) that each Node is in Ref: https://kubernetes.io/docs/concepts/workloads/pods/pod-topology-spread-constraints/  topologySpreadConstraints:   - maxSkew: 1     topologyKey: failure-domain.beta.kubernetes.io/zone     whenUnsatisfiable: DoNotSchedule |
| waitForDependency.image.pullPolicy | string | `nil` |  |
| waitForDependency.image.registry | string | `nil` |  |
| waitForDependency.image.repository | string | `"nubus/images/wait-for-dependency"` |  |
| waitForDependency.image.tag | string | `"0.36.6@sha256:72f5c4a9bab57394706207d75bca9e959df84db566ae30ad3c3db2e350cdff40"` |  |

## Uninstalling the Chart

To install the release with name `my-release`:

```bash
helm uninstall my-release
```

## Signing

### Chart

Helm charts are signed with helm native signing method. You can verify the charts against this GPG key:

```
${GPG_SOUVAP_UNIVENTION_PUB}
```

### Images

Container images are signed via [cosign](https://github.com/sigstore/cosign) and can be verified with:

```
${COSIGN_PUBLIC_KEY}
```

```
cosign verify --key cosign.pub --insecure-ignore-tlog <image>
```

## License
This project uses the following license: AGPL-3.0-only

## Copyright
Copyright (C) 2023 Univention GmbH
