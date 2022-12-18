We put some original files from Keycloak here. In case they get updated upstream, we can still compare our originals to the ones we actually shipped, then take that diff and apply it to the new original. At least that is the idea...

## Contents

**login.ftl**

Is the template Keycloak uses to render the login form. We basically just switched `<label>` and `<input>` tags. The result is in `files/themes/UCS/login/login.ftl`

**theme.ftl**

Is the template "above" the login.ftl. We added the div `#umcLoginLinks`
