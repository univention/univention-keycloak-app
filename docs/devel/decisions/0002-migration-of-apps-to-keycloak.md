
# App vendors must know how to use Keycloak instead of simpleSAMLphp

---

## Context and Problem Statement

We should define a mechanism that we can communicate to ISVs how they can discover the preferred SSO solution,
Keycloak or simpleSAMLphp. This is relevant as long as installing Keycloak is optional.

Example nextcloud:
* Shall new installations of nextcloud use Keycloak? How can it decide and how can it do it?
* Shall existing installations of nextcloud switch to Keycloak when the Keycloak app gets installed?

Technical Background:
* In its initial version, the Keycloak App can only be installed on a UCS Primary Node
* Currently the DNS alias `ucs-sso.<domainname>` is used by apps to connect to simpleSAMLphp.
* There is a DNS alias `ucs-sso-ng.<domainname>` where Keycloak can be reached.
* Some customers reconfigure their simpleSAMLphp IdP to run on the FQDN of the UCS Primary Node instead,
  or to use an externally reachable public FQDN that is reverse-proxied to the UCS Primary Node.


There is also the question if Keycloak shall be used for all apps or not. Probably all or nothing.

## Considered Options

- Apps could use LDAP search for `univentionService=keycloak` to see if the App is installed on the Primary Node
- Apps could use a UCR variable of sorts to allow manual choice of the IdP solution
- ...

## Decision Outcome

Chosen option: TODO

