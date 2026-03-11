# SPDX-License-Identifier: AGPL-3.0-only
# SPDX-FileCopyrightText: 2025 Univention GmbH

from univention.testing.helm.auth_flavors.password_usage import AuthPasswordUsageViaEnv
from univention.testing.helm.auth_flavors.secret_generation import AuthSecretGenerationOwner
from univention.testing.helm.auth_flavors.username import AuthUsernameViaEnv


class SettingsTestKeycloakSecret:
    secret_name = "release-name-keycloak-credentials"
    prefix_mapping = {"keycloak.auth": "auth"}

    # Used by AuthSecretGenerationUser only
    path_password = "stringData.adminPassword"

    # Used by AuthSecretGenerationUser and AuthUsernameViaEnv only
    sub_path_env_password = "env[?@name=='KEYCLOAK_ADMIN_PASSWORD']"
    sub_path_env_username = "env[?@name=='KEYCLOAK_ADMIN']"
    secret_default_key = "adminPassword"


class TestChartCreatesKeycloakSecretAsOwner(SettingsTestKeycloakSecret, AuthSecretGenerationOwner):
    derived_password = "999b42043c6722dc62b22d22c326ba3d72c8b8f0"


class TestKeycloakUsesKeycloakCredentialsByEnv(SettingsTestKeycloakSecret, AuthPasswordUsageViaEnv, AuthUsernameViaEnv):
    workload_name = "release-name-keycloak"
    workload_kind = "StatefulSet"
