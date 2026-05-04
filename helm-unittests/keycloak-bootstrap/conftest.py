# SPDX-License-Identifier: AGPL-3.0-only
# SPDX-FileCopyrightText: 2025 Univention GmbH

from pathlib import Path

import pytest


base_dir = (Path(__file__).parent / '../../').resolve()


@pytest.fixture
def helm_default_values(request):
    return [
        base_dir / 'helm/keycloak-bootstrap/linter_values.yaml',
    ]


@pytest.fixture
def chart_default_path():
    return base_dir / 'helm/keycloak-bootstrap'
