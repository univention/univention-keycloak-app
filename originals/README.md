We put some original files from Keycloak here. In case they get updated upstream, we can still compare our originals to the ones we actually shipped, then take that diff and apply it to the new original. At least that is the idea...

## Development
For a workflow to do changes on the fly, see [docs-dev/README-themes-template.md](../docs-dev/README-themes-template.md).

## Contents

**login.ftl**

Is the template Keycloak uses to render the login form. We basically just switched `<label>` and `<input>` tags. The result is in `files/themes/UCS/login/login.ftl`

## Comparing changes

Use `vimdiff originals/login.ftl files/themes/UCS/login/login.ftl`.
Make sure, that the diff between original and new one is small.
E.g. by not removing upstream blocks but by inserting them into HTML comments <!-- like this -->.
