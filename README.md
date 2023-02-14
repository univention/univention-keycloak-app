_[TOC]_

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

## Pipelines

### image_build

Builds the keycloak image and pushes it in our local gitlabe registry.
The name of the image if `gitregistry.knut.univention.de/univention/components/keycloak-app:$NAME_OF_BRANCH`
or `gitregistry.knut.univention.de/univention/components/keycloak-app:latest` for the `main` branch.

The idea is that every MR has its own image and QA can use this
to setup an environemt for testing.

The app in the test appcenter always uses the image from the `main` branch.

Before releasing a new version the image has to be transfered to our external
registry (see docker-job). In this process the image of the app is changed to
e.g. `docker.software-univention.de/keycloak-keycloak:19.0.2-ucs1`.

## Test Environments

The latest version on the test appcenter will always point to the "main" branch image. If
you want to setup keycloak with an image of your MergeRequest, do:
* `univention-app update`
* change the image name in the latest compose file in the local cache, e.g.  `/var/cache/univention-appcenter/appcenter-test.software-univention.de/5.0/keycloak_20230201094428.compose`
* install the app
* if the app is already installed, run `univention-app configure keycloak`

Or use the https://jenkins2022.knut.univention.de/job/UCS-5.0/job/UCS-5.0-3/view/Keycloak/job/UcsKeycloakEnvironment/ Jenkins job
to create a test environment (primary + keycloak and backup + keycloak). The docker image for the keycloak app can be changed via
the `KEYCLOAK_IMAGE` parameter.

To find out the IPs of your two systems, you have to consult the console output of the Jenkins job and find lines like
```sh
10:01:12 [primary] Requesting IPv4 address..
10:01:12 [primary] Requesting IPv4 address: done (MAC=52:54:00:5d:8a:29  IPv4=10.207.183.251)
[...]
10:01:15 [backup1] Requesting IPv4 address..
10:01:16 [backup1] Requesting IPv4 address: done (MAC=52:54:00:cb:d4:2b  IPv4=10.207.183.252)
```

Then add the following to your `/etc/hosts`:
```
10.207.63.20 master.ucs.test
10.207.63.21 backup.ucs.test
10.207.63.20 ucs-sso-ng.ucs.test
# 10.207.63.21 ucs-sso-ng.ucs.test # if we want to test keycloak on the backup
```


## App updates

The script update-appcenter-test.sh can be used to build and upload the files from the repo for the latest test app center app version

## Special use case: Ad-Hoc-Federation using the "univention-authenticator" Keycloak SPI
* The "univention-authenticator" Keycloak SPI is an extension written in Java.
* It is shipped as part of the UCS Keycloak App, but not configured by default.
* If configured properly (TODO: details pending) it allows creating a "shadow user account" in UDM after
  successful authentication against an external IdP (see page 13 of the [Summit presentation](https://www.slideshare.net/Univention/modularisierung-und-containerisierung-von-ucs).

# Documentation

For local documentation builds, see [Build Sphinx documents
locally](https://hutten.knut.univention.de/mediawiki/index.php/Build_Sphinx_documents_locally).

# Checklist for new app version

Besides the necessary steps for an app update, make sure to apply the following
steps **before** release of a new app version for the Keycloak app.

1. [ ] - Update the `DOC_TARGET_VERSION` variable in
   [.gitlab-ci.yml](.gitlab-ci.yml) to the new app version. The variable makes
   sure that the new app version has a dedicated documentation.

2. [ ] - Add an appropriate changelog entry to
   [docs/changelog.rst](docs/changelog.rst) and follow the recommendation at
   https://keepachangelog.com/en/1.0.0/.

3. If documentation for a new feature or for a change is part of the regular
   text in the documentation, highlight it with the
   [versionadded](https://www.sphinx-doc.org/en/master/usage/restructuredtext/directives.html#directive-versionadded),
   [versionchanged](https://www.sphinx-doc.org/en/master/usage/restructuredtext/directives.html#directive-versionchanged)
   or
   [deprecated](https://www.sphinx-doc.org/en/master/usage/restructuredtext/directives.html#directive-deprecated)
   directive.

4. [ ] - After running the *production* job for the documentation in the
   pipeline, update the symlink `latest` the new version in the [keycloak-app
   directory of the docs.univention.de
   repository](https://git.knut.univention.de/univention/docs.univention.de/-/tree/master/keycloak-app).
