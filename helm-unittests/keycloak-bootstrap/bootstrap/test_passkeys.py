# SPDX-License-Identifier: AGPL-3.0-only
# SPDX-FileCopyrightText: 2026 Univention GmbH

"""
Unit tests for ``bootstrap.passkeys`` of the keycloak-bootstrap chart.

The value is rendered as ``passkeys.yaml`` into the bootstrap data ConfigMap,
which the Ansible playbook reads to enable or disable passkeys.
When the value is not set, the playbook leaves passkeys unchanged.
"""

from pytest_helm.utils import load_yaml

from univention.testing.helm.base import Base


class TestPasskeysBootstrapData(Base):
    template_file = 'templates/configmap.yaml'
    configmap_name = 'release-name-keycloak-bootstrap-bootstrap-data'

    def _passkeys(self, helm, chart_path, values):
        result = helm.helm_template(chart_path, values, self.template_file)
        configmap = result.get_resource(kind='ConfigMap', name=self.configmap_name)
        return load_yaml(configmap['data']['passkeys.yaml'])

    def test_unset_by_default(self, helm, chart_path):
        # Rendered standalone the key stays as null; inside an umbrella chart Helm drops it.
        assert self._passkeys(helm, chart_path, {}).get('enabled') is None

    def test_enabled(self, helm, chart_path):
        values = {'bootstrap': {'passkeys': {'enabled': True}}}
        assert self._passkeys(helm, chart_path, values) == {'enabled': True}

    def test_disabled(self, helm, chart_path):
        values = {'bootstrap': {'passkeys': {'enabled': False}}}
        assert self._passkeys(helm, chart_path, values) == {'enabled': False}
