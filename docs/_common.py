# SPDX-FileCopyrightText: 2026 Univention GmbH
#
# SPDX-License-Identifier: AGPL-3.0-only

"""Shared helpers for the Sphinx docs in this directory."""

import yaml


def read_ci_variable(name: str) -> str:
    """
    Read a variable from the pipeline definition (.gitlab-ci.yml).

    :param name: The name of the variable under the CI ``variables`` block.

    :returns: The value of the variable as defined in the CI/CD pipeline.

    :raises KeyError: If the variable is not defined in .gitlab-ci.yml.

    :rtype: str
    """
    with open('../../.gitlab-ci.yml') as f:
        ci = yaml.safe_load(f)
    variables = ci.get('variables', {})
    if name not in variables:
        raise KeyError(f'{name} is not defined in the variables of .gitlab-ci.yml')
    return variables[name]


def render_bibliography() -> None:
    """
    Generate ``bibliography.bib`` from ``bibliography.bib.in``.

    The Keycloak version placeholders are substituted from KEYCLOAK_VERSION in
    the pipeline definition (.gitlab-ci.yml). ``@KEYCLOAK_VERSION@`` becomes the
    full version (e.g. 26.7.2) and ``@KEYCLOAK_VERSION_MINOR@`` the major.minor
    part (e.g. 26.7).
    """
    full = read_ci_variable('KEYCLOAK_VERSION')
    minor = '.'.join(full.split('.')[:2])
    with open('../bibliography.bib.in') as f:
        content = f.read()
    content = content.replace('@KEYCLOAK_VERSION_MINOR@', minor)
    content = content.replace('@KEYCLOAK_VERSION@', full)
    with open('../bibliography.bib', 'w') as f:
        f.write(content)
