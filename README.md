## Introduction

This repository contains the components of the Keycloak App for the UCS Appcenter.

## Development
# App base

The app uses [Keycloak](https://www.keycloak.org/docs/17.0/) to provide a SAML and OpenID Connect provider.

The docker image used in the app is [keycloak](https://quay.io/repository/keycloak/keycloak?tab=tags), provided by RedHat.

# Realm Configuration

* Keycloak comes with a Realm `master` by default.
* Additionally the UCS Keycloak App creates a realm `UCS`.

# LDAP User Federation

* This Keycloak App is configured to use "User Federation" in the Keycloak Realm named "UCS".
* The "User Federation" configured in the `UCS` uses `uid=sys-idp-user,cn=users,$ldap_base` to bind to OpenLDAP.
* The "User Federation" is configured to **not** sync user accounts from LDAP to Keycloak.

# SAML Support

* Keycloak automatically acts as SAML IdP. For each SP (SAML or OIDC) a "Client" configuration needs to be created
  in Keycloak.
    * In its initial version, the Keycloak App creates a "Client" for the UMC on the FQDN of the host
      which it is installed on.
* Keycloak can be configured to federate out to other IdPs. If several authentication sources are possible,
  e.g. a "User federation" and two external IdPs then Keycloak will show a login page to the user, where the user
  needs to select the method. There are ways to preselect (either hardcode in Keycloak config or pass `&kc_idp_hint=foo`
  with the login URL). Keycloak will not iterate over possible authentication sources. Names may need to get
  mapped to ensure uniqueness. See Keycloak docs for details.

# OIDC Support

TODO: Anything special to explain here?

# Configuration

The app can be configured with app settings.

To integrate other services, they often require URIs for the identity provider endpoints, they are available at `https://ucs-sso-ng.$(hostname -d)/.well-known/openid-configuration`

TODO: Update the following statement, probably outdated with the change from `keycloak.$(hostname -f)` to `ucs-sso-ng.$(hostname -d)`:

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

## Special use case: Ad-Hoc-Federation using the "univention-authenticator" Keycloak SPI
* The "univention-authenticator" Keycloak SPI is an extension written in Java.
* It is shipped as part of the UCS Keycloak App, but not configured by default.
* If configured properly (TODO: details pending) it allows creating a "shadow user account" in UDM after
  successful authentication against an external IdP (see page 13 of the [Summit presentation](https://www.slideshare.net/Univention/modularisierung-und-containerisierung-von-ucs).

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
