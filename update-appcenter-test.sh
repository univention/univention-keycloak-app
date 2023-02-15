#!/bin/bash
#
# Also see https://docs.software-univention.de/app-center/5.0/en/configurations.html#installation-scripts
#

set -e
set -x

UCS_VERSION="5.0"
APP_ID="keycloak"
VERSION="$(sed -n 's/^Version\s*=\s*//p' app/ini)"
APP_VERSION="${UCS_VERSION}/${APP_ID}=${VERSION}"

selfservice () {
	local uri="https://provider-portal.software-univention.de/appcenter-selfservice/univention-appcenter-control"
	local first=$1
	shift

	USERNAME="$USER"
	[ -e "$HOME/.univention-appcenter-user" ] && USERNAME="$(< $HOME/.univention-appcenter-user)"

	PWDFILE="~/.selfservicepwd"
	[ -e "$HOME/.univention-appcenter-pwd" ] && PWDFILE="$HOME/.univention-appcenter-pwd"

	curl -sSfL "$uri" | python3 - "$first" --username=${USERNAME} --pwdfile=${PWDFILE} "$@"
}

die () {
	echo "$@"
	exit 0
}

[ "$IGN_GIT" != "true" ] && test -n "$(git status -s)" && die "Changes in repo, do not upload app! (to override: IGN_GIT=true)"

## Here we could e.g. generate the scripts from templates and adjust things, see e.g.:
## * https://git.knut.univention.de/univention/components/dashboard/admin-dashboard
## * https://git.knut.univention.de/univention/components/oidc-provider

## Some apps use templates to install additional files, e.g.:
cp app/preinst.tmpl app/preinst

## Then they simply teplace the some keywords by the actual files like:
tar cjf - -C files/themes UCS | base64 >> files/tmp_base64
sed -i -e "/%ARCHIVE_CONTENT%/r files/tmp_base64" -e "/%ARCHIVE_CONTENT%/d" app/preinst

sed -i -e "/%KEYCLOAK-ACL%/r files/67keycloak.acl" -e "/%KEYCLOAK-ACL%/d" app/preinst

sed -i -e "/%KEYCLOAK-TEMPLATE-APACHE%/r files/univention-keycloak.conf" -e "/%KEYCLOAK-TEMPLATE-APACHE%/d" app/preinst
sed -i -e "/%KEYCLOAK-INFO-APACHE%/r files/univention-keycloak.info" -e "/%KEYCLOAK-INFO-APACHE%/d" app/preinst
base64 files/keycloak.conf >> files/tmp_kconf_b64
sed -i -e "/%KEYCLOAK-TEMPLATE-CONF%/r files/tmp_kconf_b64" -e "/%KEYCLOAK-TEMPLATE-CONF%/d" app/preinst

sed -i -e "/%POSTGRESQL-KEYCLOAK-TEMPLATE%/r files/50-keycloak" -e "/%POSTGRESQL-KEYCLOAK-TEMPLATE%/d" app/preinst
sed -i -e "/%POSTGRESQL-KEYCLOAK-INFO%/r files/50-keycloak.info" -e "/%POSTGRESQL-KEYCLOAK-INFO%/d" app/preinst

base64 files/cache-ispn-jdbc-ping.xml >> files/tmp_ispn_kconf_b64
sed -i -e "/%KEYCLOAK-ISPN-TEMPLATE-CONF%/r files/tmp_ispn_kconf_b64" -e "/%KEYCLOAK-ISPN-TEMPLATE-CONF%/d" app/preinst

sed -i -e "/%KEYCLOAK-LOGIN-TRANSLATION-DE%/r files/messages_de.properties" -e "/KEYCLOAK-LOGIN-TRANSLATION-DE%/d" app/preinst
sed -i -e "/%KEYCLOAK-LOGIN-TRANSLATION-EN%/r files/messages_en.properties" -e "/%KEYCLOAK-LOGIN-TRANSLATION-EN%/d" app/preinst
sed -i -e "/%KEYCLOAK-LOGIN-TRANSLATION-INFO-DE%/r files/messages_de.properties.info" -e "/KEYCLOAK-LOGIN-TRANSLATION-INFO-DE%/d" app/preinst
sed -i -e "/%KEYCLOAK-LOGIN-TRANSLATION-INFO-EN%/r files/messages_en.properties.info" -e "/%KEYCLOAK-LOGIN-TRANSLATION-INFO-EN%/d" app/preinst
## Now we can upload the files for the app to the provider-portal:
## The order of the arguments doesn't matter, the univention-appcenter-control script recongnizes the filenames and file extensions.
selfservice upload "$APP_VERSION" \
    app/compose \
    app/settings \
    app/preinst \
    app/configure_host \
    app/inst \
    app/env \
    app/test \
    app/uinst \
    app/README_UPDATE_DE \
    app/README_UPDATE_EN

## There are more "magic" files that can be uploaded for specific purposes:
# selfservice upload "$APP_VERSION" app/compose app/settings app/preinst app/configure_host app/inst app/uinst app/env app/test app/setup README_*

## And finally they clean the working directory after upload
rm -f app/preinst files/tmp_base64 files/tmp_kconf_b64 files/tmp_ispn_kconf_b64
