# SPDX-License-Identifier: AGPL-3.0-only
# SPDX-FileCopyrightText: 2025 Univention GmbH

from univention.testing.helm.auth_flavors.password_usage import \
    AuthPasswordUsageViaEnv
from univention.testing.helm.auth_flavors.secret_generation import \
    AuthSecretGenerationOwner


class SettingsTestOidcSecret:
    secret_name = "release-name-keycloak-bootstrap-oidc-rp-umc-server"
    prefix_mapping = {"oidc.rp.umcserver.clientSecret": "auth"}

    # Used by AuthSecretGenerationUser and AuthUsernameViaEnv only
    sub_path_env_password = "env[?@name=='UMC_OIDC_CLIENT_SECRET']"


class TestChartCreatesOidcSecretAsOwner(SettingsTestOidcSecret,
                                        AuthSecretGenerationOwner):
    derived_password = "de98d48eb2ede8dfd7806f788289cec648ab79c7"


class TestKeycloakUsesOidcCredentialsByEnv(SettingsTestOidcSecret,
                                           AuthPasswordUsageViaEnv):
    workload_name = "release-name-keycloak-bootstrap-job"
    workload_kind = "Job"
