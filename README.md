# Keycloak app

TODO: Latest documentation. Write here how to setup the app correctly.

# App base

The app uses [Keycloak](https://www.keycloak.org/docs/17.0/) to provide a SAML and OpenID Connect provider.

The docker image used in the app is [keycloak](https://quay.io/repository/keycloak/keycloak?tab=tags), provided by RedHat.

# LDAP User Federation

# SAML Support

# OIDC Support

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
