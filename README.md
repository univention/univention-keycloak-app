## Introduction

This repository contains the components of the Keycloak App for the UCS Appcenter.

## Development
# App base

The app uses [Keycloak](https://www.keycloak.org/docs/17.0/) to provide a SAML and OpenID Connect provider.

The docker image used in the app is [keycloak](https://quay.io/repository/keycloak/keycloak?tab=tags), provided by RedHat.

# LDAP User Federation

TODO: Anything to explain here?

# SAML Support

TODO: Anything to explain here?

# OIDC Support

TODO: Anything to explain here?

# Configuration

The app can be configured with app settings.

To integrate other services, they often require URIs for the identity provider endpoints, they are available at `https://keycloak.$(hostname -f)/.well-known/openid-configuration`

The apache2 reverse proxy config is at `/var/lib/univention-appcenter/apps/keycloak/config/vhost.conf` and there are some UCR variables `apache2/vhosts/.*` set autoamtically during join via the joinscript `/usr/lib/univention-install/50keycloak.inst` installed on the host (uploaded to the [provider-portal](https://provider-portal.software-univention.de) as [app/inst](app/inst) ).

# Tests

All tests with browsers should happen in a new private browser window. This ensures that no cookies or old sessions are present.

All tests have to happen using actual hostnames with correct and verfifyable certificates. The involved services rely on and test for signed and trusted certificates.

An app specific test can be added here as [app/test](app/test)

Manual product tests:
- TODO: If it makes sense, then describe steps to do some typical basic checks of functionality
- ...

# Internals

See [app/](app/) for app center integration files and
https://docs.software-univention.de/app-center/5.0/en/configurations.html#installation-scripts

## App updates

The script update-appcenter-test.sh can be used to build and upload the files from the repo for the latest test app center app version



# Documentation

## Local build

Requirements:

* Use Python virtual environment for a separated Python environment decoupled
  from your local Python installation. Install the deb-package on Debian/Ubuntu:
  `apt install virtualenvwrapper`.

  * Create a new virtual environment with `mkvirtualenv $env_name`.
  * Enter a virtual environment with `workon $env_name`.
  * Leave a virtual environment with `deactivate`.

  On other operating systems see the [Virtualenvwrapper
  documentation](https://virtualenvwrapper.readthedocs.io/en/latest/). See
  [Controlling the Active
  Environment](https://virtualenvwrapper.readthedocs.io/en/4.8.4/command_ref.html#controlling-the-active-environment)
  on how to switch environments. For this project and other Sphinx based
  projects from Univention, one such virtual environment is enough.

Prepare local Python environment once:

1. Checkout this repository.

1. Install the dependencies:

   ```
      python -m pip install --upgrade pip
      pip install -r requirements.txt
   ```

With the requirements, [Univention Sphinx Book
theme](https://git.knut.univention.de/univention/documentation/univention_sphinx_book_theme)
and [Univention Sphinx
extension](https://git.knut.univention.de/univention/documentation/univention_sphinx_extension)
are also installed.

Build the documentation:

1. Change into the `docs` directory.
1. Run the static build: `make html` or run a live server: `make livehtml`.
1. Open http://localhost:8000 in your browser.
