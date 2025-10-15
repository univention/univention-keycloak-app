# SPDX-License-Identifier: AGPL-3.0-only
# SPDX-FileCopyrightText: 2025 Univention GmbH

from univention.testing.helm.auth_flavors.password_usage import \
    AuthPasswordUsageViaEnv
from univention.testing.helm.auth_flavors.secret_generation import \
    AuthSecretGenerationUser
from univention.testing.helm.auth_flavors.username import AuthUsernameViaEnv


class SettingsTestKeycloakSecret:
    secret_name = "release-name-keycloak-bootstrap-keycloak-credentials"
    prefix_mapping = {"keycloak.auth": "auth"}

    # Used by AuthSecretGenerationUser only
    path_password = "stringData.adminPassword"

    # Used by AuthSecretGenerationUser and AuthUsernameViaEnv only
    sub_path_env_password = "env[?@name=='KEYCLOAK_PASSWORD']"
    sub_path_env_username = "env[?@name=='KEYCLOAK_USERNAME']"
    secret_default_key = "adminPassword"


class TestChartCreatesKeycloakSecretAsUser(SettingsTestKeycloakSecret,
                                           AuthSecretGenerationUser):
    pass


class TestHandlerUsesKeycloakCredentialsByEnv(SettingsTestKeycloakSecret,
                                              AuthPasswordUsageViaEnv,
                                              AuthUsernameViaEnv):
    workload_name = "release-name-keycloak-bootstrap-bootstrap-1"
    workload_kind = "Job"
