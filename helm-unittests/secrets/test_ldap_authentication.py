# SPDX-License-Identifier: AGPL-3.0-only
# SPDX-FileCopyrightText: 2025 Univention GmbH

from univention.testing.helm.auth_flavors.password_usage import \
    AuthPasswordUsageViaEnv
from univention.testing.helm.auth_flavors.secret_generation import \
    AuthSecretGenerationUser
from univention.testing.helm.auth_flavors.username import AuthUsernameViaEnv


class SettingsTestLdapSecret:
    secret_name = "release-name-keycloak-bootstrap-ldap-credentials"
    prefix_mapping = {"auth.bindDn": "auth.username", "ldap.auth": "auth"}

    # Used by AuthSecretGenerationUser and AuthUsernameViaEnv only
    sub_path_env_password = "env[?@name=='LDAP_BIND_PW']"
    sub_path_env_username = "env[?@name=='LDAP_BIND_DN']"


class TestChartCreatesLdapSecretAsUser(SettingsTestLdapSecret,
                                       AuthSecretGenerationUser):
    pass


class TestHandlerUsesLdapCredentialsByEnv(SettingsTestLdapSecret,
                                          AuthPasswordUsageViaEnv,
                                          AuthUsernameViaEnv):
    workload_name = "release-name-keycloak-bootstrap-bootstrap-1"
    workload_kind = "Job"
