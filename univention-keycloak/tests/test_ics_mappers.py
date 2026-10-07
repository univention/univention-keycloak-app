# SPDX-License-Identifier: AGPL-3.0-only
# SPDX-FileCopyrightText: 2026 Univention GmbH

from importlib.machinery import SourceFileLoader
from importlib.util import module_from_spec, spec_from_loader
from pathlib import Path
from types import ModuleType
from typing import Any
from unittest.mock import MagicMock

import pytest


SCRIPT = Path(__file__).resolve().parent.parent / 'scripts' / 'univention-keycloak'


@pytest.fixture
def cli() -> ModuleType:
    """Load the univention-keycloak script as a module."""
    loader = SourceFileLoader('univention_keycloak', str(SCRIPT))
    module = module_from_spec(spec_from_loader(loader.name, loader))
    loader.exec_module(module)
    return module


def audience_mappers(mappers: list[dict[str, Any]]) -> list[dict[str, Any]]:
    """
    Return only the audience mappers of a list of protocol mappers.

    Args:
        mappers: Protocol mapper payloads.

    Returns:
        The mappers of type oidc-audience-mapper.
    """
    return [m for m in mappers if m['protocolMapper'] == 'oidc-audience-mapper']


@pytest.mark.parametrize('client_id', ['intercom', 'opendesk-intercom'])
def test_ics_audience_mapper_uses_client_id(cli: ModuleType, client_id: str) -> None:
    (mapper,) = audience_mappers(cli.ics_mappers(client_id))

    assert mapper['config']['included.client.audience'] == client_id


def test_ics_mappers_return_new_objects(cli: ModuleType) -> None:
    first = cli.ics_mappers('intercom')
    first[1]['config']['included.client.audience'] = 'changed'

    assert cli.ics_mappers('intercom')[1]['config']['included.client.audience'] == 'intercom'


def created_mappers(cli: ModuleType, monkeypatch: pytest.MonkeyPatch, *args: str) -> list[dict[str, Any]]:
    """
    Run "oidc/rp create intercom --add-ics-mappers" without Keycloak and return the mappers it sends.

    Args:
        cli: The loaded univention-keycloak module.
        monkeypatch: The pytest monkeypatch fixture.
        *args: Additional arguments for "oidc/rp create".

    Returns:
        The protocol mappers of the client payload.
    """
    create_or_update_client = MagicMock()
    monkeypatch.setattr(cli, 'UniventionKeycloakAdmin', MagicMock())
    monkeypatch.setattr(cli, 'create_or_update_client', create_or_update_client)
    opt = cli.parse_args(
        [
            '--keycloak-url',
            'https://id.example.test',
            '--bindpwd',
            'secret',
            'oidc/rp',
            'create',
            'intercom',
            '--add-ics-mappers',
            '--host-fqdn',
            'id.example.test',
            *args,
        ]
    )

    cli.create_oidc_client(opt)

    return create_or_update_client.call_args.args[1]['protocolMappers']


def test_create_oidc_client_with_ics_mappers(cli: ModuleType, monkeypatch: pytest.MonkeyPatch) -> None:
    (mapper,) = audience_mappers(created_mappers(cli, monkeypatch))

    assert mapper['config']['included.client.audience'] == 'intercom'


def test_ics_mappers_keep_additional_audiences(cli: ModuleType, monkeypatch: pytest.MonkeyPatch) -> None:
    mappers = created_mappers(cli, monkeypatch, '--client-access-token-audience', 'ncoidc', '--client-access-token-audience', 'xwikioidc')

    audiences = [m['config']['included.client.audience'] for m in audience_mappers(mappers)]
    assert audiences == ['intercom', 'ncoidc', 'xwikioidc']


def claims(mappers: list[dict[str, Any]]) -> dict[str, str]:
    """
    Map the LDAP attribute of each user attribute mapper to its claim name.

    Args:
        mappers: Protocol mapper payloads.

    Returns:
        The claim name for each mapped user attribute.
    """
    return {m['config']['user.attribute']: m['config']['claim.name'] for m in mappers if 'user.attribute' in m['config']}


def test_ics_mappers_default_claims(cli: ModuleType, monkeypatch: pytest.MonkeyPatch) -> None:
    assert claims(created_mappers(cli, monkeypatch)) == {'uid': 'phoenixusername', 'entryUUID': 'entryuuid'}


def test_ics_mappers_custom_claims(cli: ModuleType, monkeypatch: pytest.MonkeyPatch) -> None:
    mappers = created_mappers(cli, monkeypatch, '--ics-username-claim', 'username', '--ics-unique-claim', 'useruuid')

    assert claims(mappers) == {'uid': 'username', 'entryUUID': 'useruuid'}
