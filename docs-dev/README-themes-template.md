_[TOC]_

# Handling of templates and themes

By default keycloak caches the theme. To disable the caching set
`keycloak/disable/theme/caching` and run
`univention-app configure keycloak`.

## Templates

## Themes, css and other resoures

We tried to reuse as many files from the appliance as possible to not
duplicate css files, logo files etc. For that we just use whatever we need
from `/univention/` from the appliances. SWP has to provide an additional
service for those resources.

Currently we use:
| Web resource | Env variable in container to overwrite default location| |
| ------ | ------ | ------ |
| /univention/theme.css     |     UNIVENTION_THEME   | the current univention theme |
| /univention/login/css/custom.css      |   UNIVENTION_CUSTOM_THEME     | a custome css theme |
| /univention/js/dijit/themes/umc/images/login_logo.svg| no, instead the logo can be changed with `--login-logo` in css| login logo|
| /favicon.ico |UNIVENTION_FAVICON| |
| /univention/meta.json | UNIVENTION_META_JSON | additional configuration, currently used for the cookie banner |

### Changing the theme

As keycloak now uses the same css a files as UMC/Portal in the appliance, chaning the theme is just `ucr set ucs/web/theme=light`.

### Changing login logo

Add
```css
:root {
  --login-logo: url("/univention/mylogo.svg") no-repeat center;
}
```
to `/var/www/univention/login/css/custom.css`.

## Making changes on the fly

1. Disable theme caching: `ucr set keycloak/disable/theme/caching=true`
2. Apply UCR change: `univention-app reinitialize keycloak`
3. Get the directory base of the container: `base=$(docker inspect keycloak --format '{{ .GraphDriver.Data.MergedDir }}')`
4. Copy changed files to e.g. "$base/opt/keycloak/themes/UCS/login/login.ftl"
```
IP=10.207.186.178
base=$(ssh "root@$IP" 'docker inspect keycloak --format "{{ .GraphDriver.Data.MergedDir }}"')
scp ./files/themes/UCS/login/resources/css/*.css "$IP":"$base"/opt/keycloak/themes/UCS/login/resources/css/
scp files/themes/UCS/login/login.ftl "$IP":"$base"/opt/keycloak/themes/UCS/login/
```
