# SPDX-License-Identifier: AGPL-3.0-only
# SPDX-FileCopyrightText: 2026 Univention GmbH

"""
Unit tests for the observability metrics configuration of the Keycloak chart.

Covers ``config.userEventMetrics`` and ``config.httpMetrics``:
* the values are rendered into the ConfigMap as ``KC_*`` environment variables,
* enabling user event metrics adds the ``user-event-metrics`` feature to the
  start command without duplicating an existing ``--features`` flag.
"""

from univention.testing.helm.base import Base


def _main_container_args(statefulset: dict) -> list:
    containers = statefulset['spec']['template']['spec']['containers']
    main = next(c for c in containers if c['name'] == 'main')
    return main.get('args', [])


def _feature_flags(args: list) -> list:
    return [a for a in args if a.startswith('--features=')]


class TestUserEventMetricsConfigMap(Base):
    template_file = 'templates/configmap.yaml'

    def test_disabled_by_default(self, helm, chart_path):
        configmap = self.helm_template_file(helm, chart_path, {}, self.template_file)
        assert 'KC_EVENT_METRICS_USER_ENABLED' not in configmap['data']
        assert 'KC_EVENT_METRICS_USER_TAGS' not in configmap['data']
        assert 'KC_EVENT_METRICS_USER_EVENTS' not in configmap['data']

    def test_enabled_renders_env_vars(self, helm, chart_path):
        values = {
            'config': {
                'userEventMetrics': {
                    'enabled': True,
                    'tags': 'realm,clientId',
                    'events': 'login,logout',
                },
            },
        }
        configmap = self.helm_template_file(helm, chart_path, values, self.template_file)
        data = configmap['data']
        assert data['KC_EVENT_METRICS_USER_ENABLED'] == 'true'
        assert data['KC_EVENT_METRICS_USER_TAGS'] == 'realm,clientId'
        assert data['KC_EVENT_METRICS_USER_EVENTS'] == 'login,logout'


class TestHttpMetricsConfigMap(Base):
    template_file = 'templates/configmap.yaml'

    def test_absent_by_default(self, helm, chart_path):
        configmap = self.helm_template_file(helm, chart_path, {}, self.template_file)
        assert 'KC_HTTP_METRICS_HISTOGRAMS_ENABLED' not in configmap['data']
        assert 'KC_HTTP_METRICS_SLOS' not in configmap['data']

    def test_rendered_when_configured(self, helm, chart_path):
        values = {'config': {'httpMetrics': {'histograms': True, 'slos': '5,10,25,50'}}}
        configmap = self.helm_template_file(helm, chart_path, values, self.template_file)
        data = configmap['data']
        assert data['KC_HTTP_METRICS_HISTOGRAMS_ENABLED'] == 'true'
        assert data['KC_HTTP_METRICS_SLOS'] == '5,10,25,50'


class TestUserEventMetricsFeature(Base):
    template_file = 'templates/statefulset.yaml'

    def test_feature_absent_by_default(self, helm, chart_path):
        statefulset = self.helm_template_file(helm, chart_path, {}, self.template_file)
        for flag in _feature_flags(_main_container_args(statefulset)):
            assert 'user-event-metrics' not in flag

    def test_feature_added_when_enabled(self, helm, chart_path):
        values = {'config': {'userEventMetrics': {'enabled': True}}}
        statefulset = self.helm_template_file(helm, chart_path, values, self.template_file)
        flags = _feature_flags(_main_container_args(statefulset))
        assert flags, 'no --features flag rendered'
        assert 'user-event-metrics' in flags[0]

    def test_feature_not_duplicated_when_already_listed(self, helm, chart_path):
        values = {
            'config': {'userEventMetrics': {'enabled': True}},
            'keycloak': {'features': {'enabled': ['token-exchange', 'user-event-metrics']}},
        }
        statefulset = self.helm_template_file(helm, chart_path, values, self.template_file)
        flags = _feature_flags(_main_container_args(statefulset))
        assert len(flags) == 1, 'expected exactly one --features flag'
        assert flags[0].count('user-event-metrics') == 1
