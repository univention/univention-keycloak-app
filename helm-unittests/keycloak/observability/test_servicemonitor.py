# SPDX-License-Identifier: AGPL-3.0-only
# SPDX-FileCopyrightText: 2026 Univention GmbH

"""
Unit tests for the Keycloak ServiceMonitor.

Covers conditional rendering and the management metrics endpoint.
"""

from univention.testing.helm.base import Base


class TestServiceMonitor(Base):
    template_file = 'templates/servicemonitor.yaml'

    def test_not_rendered_by_default(self, helm, chart_path):
        manifests = helm.helm_template(chart_path, {})

        assert not any(manifest['kind'] == 'ServiceMonitor' for manifest in manifests)

    def test_rendered_for_management_metrics_endpoint(self, helm, chart_path):
        values = {'serviceMonitor': {'enabled': True}}

        servicemonitor = self.helm_template_file(helm, chart_path, values, self.template_file)

        assert servicemonitor['kind'] == 'ServiceMonitor'
        endpoints = servicemonitor['spec']['endpoints']
        assert len(endpoints) == 1
        assert endpoints[0]['port'] == 'management'
        assert endpoints[0]['path'] == '/metrics'

    def test_not_rendered_when_metrics_disabled(self, helm, chart_path):
        values = {'serviceMonitor': {'enabled': True}, 'config': {'enableMetrics': False}}

        manifests = helm.helm_template(chart_path, values)

        assert not any(manifest['kind'] == 'ServiceMonitor' for manifest in manifests)

    def test_not_rendered_when_service_disabled(self, helm, chart_path):
        values = {'serviceMonitor': {'enabled': True}, 'service': {'enabled': False}}

        manifests = helm.helm_template(chart_path, values)

        assert not any(manifest['kind'] == 'ServiceMonitor' for manifest in manifests)

    def test_scrape_timing_overrides(self, helm, chart_path):
        values = {
            'serviceMonitor': {
                'enabled': True,
                'interval': '15s',
                'scrapeTimeout': '5s',
            },
        }

        servicemonitor = self.helm_template_file(helm, chart_path, values, self.template_file)

        endpoint = servicemonitor['spec']['endpoints'][0]
        assert endpoint['interval'] == '15s'
        assert endpoint['scrapeTimeout'] == '5s'


class TestServiceManagementPort(Base):
    template_file = 'templates/service.yaml'

    def test_exposes_management_port(self, helm, chart_path):
        service = self.helm_template_file(helm, chart_path, {}, self.template_file)

        management_port = next(port for port in service['spec']['ports'] if port['name'] == 'management')
        assert management_port['port'] == 9000
        assert management_port['targetPort'] == 'management'
