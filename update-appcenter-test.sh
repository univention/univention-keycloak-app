#!/bin/bash
#
# Also see https://docs.software-univention.de/app-center/5.0/en/configurations.html#installation-scripts
#

set -e

UCS_VERSION="5.0"
APP_ID="keycloak"
VERSION="$(sed -n 's/^Version\s*=\s*//p' app/ini)"
APP_VERSION="${UCS_VERSION}/${APP_ID}=${VERSION}"
FILES_TO_COPY=(
	"app/compose"
	"app/settings"
	"app/preinst"
	"app/configure_host"
	"app/inst"
#	"app/env"
	"app/uinst"
	"app/README_UPDATE_DE"
	"app/README_UPDATE_EN"
	"app/LICENSE_AGREEMENT_DE"
	"app/LICENSE_AGREEMENT_EN"
)

selfservice () {
	local uri="https://provider-portal.software-univention.de/appcenter-selfservice/univention-appcenter-control"
	local first=$1
	shift

	USERNAME="$USER"
	[ -e "$HOME/.univention-appcenter-user" ] && USERNAME="$(< "$HOME"/.univention-appcenter-user)"

	PWDFILE="$HOME/.selfservicepwd"
	[ -e "$HOME/.univention-appcenter-pwd" ] && PWDFILE="$HOME/.univention-appcenter-pwd"

	curl -sSfL "$uri" | python3 - "$first" --username="${USERNAME}" --pwdfile="${PWDFILE}" "$@"
}

die () {
	echo "$@"
	exit 0
}

get_cache_file () {
	local file="$1"
	python3 <<-EOF
		from univention.appcenter.app_cache import Apps
		apps_cache = Apps()
		candidate = apps_cache.find("keycloak", latest=True)
		print(candidate.get_cache_file("$file"))
	EOF
}

usage () {
	echo "${0##*/} [options]"
	echo ""
	echo "copy keycloak app files to test appcenter or to local cache (latest keycloak version)"
	echo ""
	echo "Options:"
	echo "  -h, --help      show this help message and exit"
	echo "  -l, --local    copy files to local cache (latest keycloak version)"
	echo "  -d, --dryn-run  dry-run, just print don't copy"
}


[ "$IGN_GIT" != "true" ] && test -n "$(git status -s)" && die "Changes in repo, do not upload app! (to override: IGN_GIT=true)"

# read arguments
opts=$(getopt \
	--longoptions "help,local,dry-run" \
	--name "$(basename "$0")" \
	--options "hld" \
	-- "$@"
) || die "see -h|--help"
eval set -- "$opts"
local_copy=false
dry_run=false
while true
do
	case "$1" in
		-h|--help)
			usage
			exit 0
			;;
		-l|--local)
			local_copy=true
			shift
			;;
		-d|--dry-run)
			dry_run=true
			shift
			;;
		--)
			shift
			break
			;;
	esac
done

## Here we could e.g. generate the scripts from templates and adjust things, see e.g.:
## * https://git.knut.univention.de/univention/components/dashboard/admin-dashboard
## * https://git.knut.univention.de/univention/components/oidc-provider

## Some apps use templates to install additional files, e.g.:
cp app/preinst.tmpl app/preinst

## Then they simply teplace the some keywords by the actual files like:
sed -i -e "/%KEYCLOAK-ACL%/r files/conffiles/67keycloak.acl" -e "/%KEYCLOAK-ACL%/d" app/preinst

sed -i -e "/%KEYCLOAK-TEMPLATE-APACHE%/r files/conffiles/univention-keycloak.conf" -e "/%KEYCLOAK-TEMPLATE-APACHE%/d" app/preinst
sed -i -e "/%KEYCLOAK-INFO-APACHE%/r files/conffiles/univention-keycloak.info" -e "/%KEYCLOAK-INFO-APACHE%/d" app/preinst
base64 files/keycloak.conf >> files/tmp_kconf_b64
sed -i -e "/%KEYCLOAK-TEMPLATE-CONF%/r files/tmp_kconf_b64" -e "/%KEYCLOAK-TEMPLATE-CONF%/d" app/preinst

sed -i -e "/%POSTGRESQL-KEYCLOAK-TEMPLATE%/r files/conffiles/50-keycloak" -e "/%POSTGRESQL-KEYCLOAK-TEMPLATE%/d" app/preinst
sed -i -e "/%POSTGRESQL-KEYCLOAK-INFO%/r files/conffiles/50-keycloak.info" -e "/%POSTGRESQL-KEYCLOAK-INFO%/d" app/preinst

base64 files/cache-ispn-jdbc-ping.xml >> files/tmp_ispn_kconf_b64
sed -i -e "/%KEYCLOAK-ISPN-TEMPLATE-CONF%/r files/tmp_ispn_kconf_b64" -e "/%KEYCLOAK-ISPN-TEMPLATE-CONF%/d" app/preinst

sed -i -e "/%KEYCLOAK-LOGIN-TRANSLATION-DE%/r files/conffiles/messages_de.properties" -e "/KEYCLOAK-LOGIN-TRANSLATION-DE%/d" app/preinst
sed -i -e "/%KEYCLOAK-LOGIN-TRANSLATION-EN%/r files/conffiles/messages_en.properties" -e "/%KEYCLOAK-LOGIN-TRANSLATION-EN%/d" app/preinst
sed -i -e "/%KEYCLOAK-LOGIN-TRANSLATION-INFO-DE%/r files/conffiles/messages_de.properties.info" -e "/KEYCLOAK-LOGIN-TRANSLATION-INFO-DE%/d" app/preinst
sed -i -e "/%KEYCLOAK-LOGIN-TRANSLATION-INFO-EN%/r files/conffiles/messages_en.properties.info" -e "/%KEYCLOAK-LOGIN-TRANSLATION-INFO-EN%/d" app/preinst

if "$local_copy"; then
	for file in "${FILES_TO_COPY[@]}"; do
		cache_file="$(get_cache_file "$(basename "$file")")"
		echo "$file -> $cache_file"
		if ! "$dry_run"; then
			cp "$file" "$cache_file"
		fi
	done
else
	### Now we can upload the files for the app to the provider-portal:
	### The order of the arguments doesn't matter, the univention-appcenter-control script recongnizes the filenames and file extensions.
	echo "selfservice upload $APP_VERSION ${FILES_TO_COPY[*]}"
	if ! "$dry_run"; then
		selfservice upload "$APP_VERSION" "${FILES_TO_COPY[@]}"
	fi
fi

## There are more "magic" files that can be uploaded for specific purposes:
# selfservice upload "$APP_VERSION" app/compose app/settings app/preinst app/configure_host app/inst app/uinst app/env app/test app/setup README_*

## And finally they clean the working directory after upload
rm -f app/preinst files/tmp_base64 files/tmp_kconf_b64 files/tmp_ispn_kconf_b64
