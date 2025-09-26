# SPDX-License-Identifier: AGPL-3.0-only
# SPDX-FileCopyrightText: 2025 Univention GmbH

from univention.testing.helm.auth_flavors.password_usage import AuthPasswordUsageViaEnv
from univention.testing.helm.auth_flavors.secret_generation import AuthSecretGenerationUser
from univention.testing.helm.auth_flavors.username import AuthUsernameViaEnv


class SettingsTestPostgresqlSecret:
    secret_name = "release-name-keycloak-postgresql-credentials"
    prefix_mapping = {"postgresql.auth": "auth"}

    # Used by AuthSecretGenerationUser and AuthUsernameViaEnv only
    sub_path_env_password = "env[?@name=='KC_DB_PASSWORD']"
    sub_path_env_username = "env[?@name=='KC_DB_USERNAME']"


class TestChartCreatesPostgresqlSecretAsUser(SettingsTestPostgresqlSecret, AuthSecretGenerationUser):
    pass


class TestKeycloakUsesPostgresqlCredentialsByEnv(SettingsTestPostgresqlSecret, AuthPasswordUsageViaEnv, AuthUsernameViaEnv):
    workload_name = "release-name-keycloak"
    workload_kind = "StatefulSet"
