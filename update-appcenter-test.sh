#!/bin/bash
#
# Also see https://docs.software-univention.de/app-center/5.0/en/configurations.html#installation-scripts
#

set -e
set -x

APP_VERSION="4.4/keycloak=17.0.0"

selfservice () {
	local uri="https://provider-portal.software-univention.de/appcenter-selfservice/univention-appcenter-control"
	local first=$1
	shift

	USERNAME="$USER"
	[ -e "$HOME/.univention-appcenter-user" ] && USERNAME="$(< $HOME/.univention-appcenter-user)"

	PWDFILE="~/.selfservicepwd"
	[ -e "$HOME/.univention-appcenter-pwd" ] && PWDFILE="$HOME/.univention-appcenter-pwd"

	curl -sSfL "$uri" | python - "$first" --username=${USERNAME} --pwdfile=${PWDFILE} "$@"
}

die () {
	echo "$@"
	exit 0
}

[ "$IGN_GIT" != "true" ] && test -n "$(git status -s)" && die "Changes in repo, do not upload app! (to override: IGN_GIT=true)"

## Here we could e.g. generate the scripts from templates and adjust things, see e.g.:
## * https://git.knut.univention.de/univention/components/dashboard/admin-dashboard
## * https://git.knut.univention.de/univention/components/oidc-provider

## Some apps like to use some "generic" templates for frequently used components, e.g.:
# cp app/configure_host.tmpl configure_host
# cp app/setup.tmpl setup
# cp app/preinst.tmpl preinst

## Then they simply teplace the some keywords by the actual files like:
# sed -i -e "/%UNIVENTION-DASHBOARD-MANAGEMENT%/r files/univention-dashboard-management" -e "/%UNIVENTION-DASHBOARD-MANAGEMENT%/d" preinst

## Now we can upload the files for the app to the provider-portal:
## The order of the arguments doesn't matter, the univention-appcenter-control script recongnizes the filenames and file extensions.
selfservice upload "$APP_VERSION" app/compose app/settings app/preinst app/configure_host app/inst app/env app/test

## There are more "magic" files that can be uploaded for specific purposes:
# selfservice upload "$APP_VERSION" app/compose app/settings app/preinst app/configure_host app/inst app/uinst app/env app/test app/setup README_*

## And finally they clean the working directory after upload
# rm -f configure_host
# rm -f setup
# rm -f preinst
