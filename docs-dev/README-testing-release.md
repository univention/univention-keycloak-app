
[TOC]

Some internal notes about testing/app release and test setups.

# Tests

All tests with browsers should happen in a new private browser window. This ensures that no cookies or old sessions are present.

All tests have to happen using actual hostnames with correct and verfifyable certificates. The involved services rely on and test for signed and trusted certificates.

All tests have to pass before releasing a new version of the keycloak app.


## Unit test in the container

We have unit test for the univention-ldap-mapper. These are executed during the image build.

## ucs-test-keycloak and test jobs

The core of the product test should be done in ucs-test-keycloak and our
jenkins jobs. We should try to automate as much as possible. Currently we have
jobs for (https://jenkins2022.knut.univention.de/job/UCS-5.0/job/UCS-5.0-3/view/Keycloak/)
* Login Performance
* Product Tests
  * Maria DB setup
  * Setup with two backup's
  * Update scenario
  * keycloak external FQDN

## Manuall testing

* add here what we have to test manually

# Pipelines

## image_build

Builds the keycloak image and pushes it in our local gitlabe registry.
The name of the image if `gitregistry.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/keycloak:$NAME_OF_BRANCH`
or `gitregistry.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/keycloak:latest` for the `main` branch.

The idea is that every MR has its own image and QA can use this
to setup an environment for testing.

The app in the test appcenter always uses the image from the `main` branch.

Before releasing a new version the image has to be transferred to our external
registry (see docker-job). In this process the image of the app is changed to
e.g. `docker.software-univention.de/keycloak-keycloak:19.0.2-ucs1`.

# Test Environments

## Manual
The latest version on the test appcenter will always point to the "main" branch
image. If you want to setup keycloak with an image of your merge request:
* `univention-app update`
* change the image name in the latest compose file in the local cache, e.g. `/var/cache/univention-appcenter/appcenter-test.software-univention.de/5.0/keycloak_20230201094428.compose`
* install the app
* if the app is already installed, run `univention-app configure keycloak`

## Configure system to use keycloak
```
# activate SSO login portal tile
udm portals/entry modify --dn "cn=login-saml,cn=entry,cn=portals,cn=univention,$(ucr get ldap/base)" --set activated=TRUE

# set umc idp server to keycloak (default is still simplesamlphp)
ucr set umc/saml/idp-server="https://ucs-sso-ng.$(ucr get domainname)/realms/ucs/protocol/saml/descriptor"
service slapd restart
```

## Via jenkins job

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

# Maintenance and release of app and documentation

##### Create new app version
1. [ ] Update *Version* in *app/ini* file. This version string defines to which version of the app the *update-appcenter-test.sh* script pushes the App Center files located in the *app/* directory of this repo. Make sure to push to the correct app version, check if a test version of the app already exists!
1. [ ] Execute the *update-appcenter-test.sh* script

##### Make your changes
1. [ ] Implement changes and write documentation on feature branch with MR against main
1. [ ] Prepare version bump / changelog.rst commit like it was done here: 64a206fa32bfa490cee4ff85778aec2d6b9d5d8d
1. [ ] If the changes to the app require special action from the user, document that in the *README_UPDATE_EN* and *README_UPDATE_DE* files


##### Test your changes
1. [ ] Use the [keycloak branch tests](https://jenkins2022.knut.univention.de/job/UCS-5.2/job/UCS-5.2-5/view/Branch%20tests/job/Keycloak%20Branch%20Tests/) to test your changes.
    * for manual testing [create personal keycloak env job](https://jenkins2022.knut.univention.de/job/UCS-5.2/job/UCS-5.2-5/view/Personal%20environments/job/UcsKeycloakEnvironment/).
1. [ ] If the tests look OK and the QA approved your merge request -> merge your branch into main and wait for pipeline completion
1. [ ] Execute the *update-appcenter-test.sh* script from main
1. [ ] Run or wait for [keycloak product tests](https://jenkins2022.knut.univention.de/job/UCS-5.2/job/UCS-5.2-5/job/Keycloak%20Product%20Tests/)


##### Release the app
1. [ ] Check [keycloak product tests](https://jenkins2022.knut.univention.de/job/UCS-5.2/job/UCS-5.2-5/job/Keycloak%20Product%20Tests/)
1. [ ] Verify that correct app version is used in the Provider Portal
1. [ ] Execute the *update-appcenter-test.sh* script from main
1. [ ] Run `docker-update` of [this Jenkins job](https://jenkins2022.knut.univention.de/job/UCS-5.0/job/Apps/job/keycloak/job/App%20Autotest%20MultiEnv/). (Uncheck all other parts of that job before running)
1. [ ] Run or wait for [keycloak product tests](https://jenkins2022.knut.univention.de/job/UCS-5.2/job/UCS-5.2-5/job/Keycloak%20Product%20Tests/)
1. [ ] Release:
   * SSH to **omar**
   * `cd /var/univention/buildsystem2/mirror/appcenter`
   * `./copy_from_appcenter.test.sh 5.0 <Component ID>`. The component ID looks like this: `keycloak_20240815142626` and can be found in the Provider Portal or [here](http://appcenter-test.software-univention.de/meta-inf/5.0/keycloak/), in the name of the ini file of your app version.
   * `sudo update_mirror.sh --verbose appcenter`
1. [ ] Write mail to `app-announcement@univenton.de`. (use previous Keycloak release mails as guideline)
1. [ ] Update the keycloak version strings in the [security scan job](https://jenkins2022.knut.univention.de/job/UCS-5.2/job/Apps/job/keycloak/job/AppAutotestSecurityMonitoring/configure). (Version number in General -> String Parameter -> STARTING_VERSION -> Default Value and in the description box below)


##### Release the documentation

###### Minor and patch releases

1. [ ] Generate MR for [docs.univention.de](https://git.knut.univention.de/univention/dev/docs/docs.univention.de) via manual trigger in main pipeline in Keycloak repo.
   > In GitLab (Keycloak repo, main branch), navigate to the pipeline of your commit. Open the `doc-pipeline` stage. Execute `docs-merge-to-one-artifact` job manually. This action will automatically create a merge request in [docs.univention.de](https://git.knut.univention.de/univention/dev/docs/docs.univention.de).

###### Major releases

1. [ ] Generate MR for [docs.univention.de](https://git.knut.univention.de/univention/dev/docs/docs.univention.de) via manual trigger in main pipeline in Keycloak repo. (see above)
1. [ ] Find your MR [here](https://git.knut.univention.de/univention/dev/docs/docs.univention.de/-/merge_requests) and **cancel the auto-merge**. Manually update the `latest` symlink in the `keycloak-app/` directory to link to the new Keycloak version -> `ln -s 26.3.1 latest`. Add that to your MR, merge, done.
1. [ ] Update [dev/docs/docs-overview-pages](https://git.knut.univention.de/univention/dev/docs/docs-overview-pages). -> [guideline](https://git.knut.univention.de/univention/dev/docs/docs-overview-pages/-/merge_requests/100/diffs). Then `cd navigation` and update translation files via `make docker-gettext` and `make docker-update-po`.

# Documentation

For local documentation builds, see [Build Sphinx documents locally](https://hutten.knut.univention.de/mediawiki/index.php/Build_Sphinx_documents_locally).

## Local build

```
docker run -ti --rm -v "$PWD:/project" -w /project -u $UID --network=host --pull=always docker-registry.knut.univention.de/knut/sphinx-base:latest make -C docs/keycloak-app clean livehtml
```

# AD hoc

* Presentation `Keycloak and SPI extensions in Phoenix ` from Ferenc Géczi on
  https://hutten.knut.univention.de/mediawiki/index.php/Uttuusch

# Configuration tricks

## Import SimpleSAMLPHP signing certificate pair to Keycloak
It is possible to import the signing key and certificate from `SimpleSAMLPHP` into `Keycloak`.
* Copy the private key and the certificate from the UCS *Primary Directory* (Private key: `/etc/simplesamlphp/ucs-sso.ucs test-idp-certificate.key`, Certificate: `/etc/simplesamlphp/ucs-sso.ucs.test-idp-certificate.crt`)
* Import the copied private key and the certificate to `Keycloak` via the `Keycloak Admin Console` as described in https://www.keycloak.org/docs/latest/server_admin/#adding-an-existing-keypair-and-certificate
* Make sure to *enable* and *activate* the private key and set the priority to a value greater than ``100``.
* *Disable* and *deactivate* the standard key `rsa-generated`.
* Verify that `Keycloak` uses the imported key for signatures. Check the `SAML IDP` metadata in
`https://{$KEYCLOAK_FQDN}/realms/ucs/protocol/saml/descriptor` and verify that the `<ds:KeyName>` is the key ID (`kid`) of the imported key in the `Keycloak Admin Console`.

## Change app settings file on-the-fly

Goal is to test new app settings on an existing UCS, by just changing the
`*.settings` file in the app cache.

```
ucr set appcenter/umc/update/always=false
ucr set update/check/cron/enabled='no'
ucr set update/check/boot/enabled='no'

edit/vi /var/cache/univention-appcenter/appcenter-test.software-univention.de/5.0/keycloak_20230705184402.settings

rm /var/cache/univention-appcenter/appcenter-test.software-univention.de/5.0/.apps.en.json
rm /var/cache/univention-appcenter/appcenter-test.software-univention.de/5.0/.apps.de.json
rm /var/cache/univention-appcenter/umc-query.json
service univention-management-console-server restart

-> login to UMC and check app settings (disable js cache)
```
