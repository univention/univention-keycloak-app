We put some original files from Keycloak here. In case they get updated upstream, we can still compare our originals to the ones we actually shipped, then take that diff and apply it to the new original. At least that is the idea...

## Development
For a workflow to do changes on the fly, see [docs-dev/README-themes-template.md](../docs-dev/README-themes-template.md).

## Contents

**login.ftl**

Is the template Keycloak uses to render the login form. We basically just switched `<label>` and `<input>` tags. The result is in `files/themes/UCS/login/login.ftl`

**ldap/LDAPContextManager.java**

A temporary backport of an upstream LDAP-federation fix onto Keycloak 26.6.3.
Upstream commit `128384ca15` (26.6.0) made the admin/service-account connection
bind anonymously and re-bind per operation, flooding LDAP with BINDs, the fix
binds in the initial connection env for the non-StartTLS case so the JNDI pool
reuses one authenticated connection. The patched class is
`files/ldap-federation/LDAPContextManager.java` (delta in
`LDAPContextManager.java.patch`); the `federation` stage in
`docker/keycloak/Dockerfile` compiles it against the shipped jars and swaps it
into the federation provider jar.

The fix is merged upstream (commit `b9fb0f0b89`, PR keycloak/keycloak#50218) but
not yet released; 26.6.3 does not contain it. On a Keycloak version bump: if the
new release includes the fix, drop this overlay entirely (the `federation` stage,
the `COPY --from=federation`, and the `files/ldap-federation/` and `originals/ldap/`
files), otherwise re-diff this original against the new upstream file and re-apply
the patch.

## Comparing changes

Use `vimdiff originals/login.ftl files/themes/UCS/login/login.ftl`.
Make sure, that the diff between original and new one is small.
E.g. by not removing upstream blocks but by inserting them into HTML comments <!-- like this -->.
