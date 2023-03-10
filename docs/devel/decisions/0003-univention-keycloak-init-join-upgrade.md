
# How do we maintain the app with univention-keycloak

---

## Context and Problem Statement

Currently **init** may overwrite customer settings as it is executed for every
installation. We also need some kind of **upgrade** mechanism to provide a way
to change the configuration on updates with running init again.

## Decision Outcome 2023-03-09

See https://git.knut.univention.de/univention/ucs/-/issues/1482

### init

Only once per domain, everything is configured, the configuration is
up-to-date.

**init** will just exit(0) if it finds the initial configuration (that is: the
the ucs realm) being present. We will document, that you can manually remove
the `ucs` realm and run init again. But make clear, what the implications are:
manual configuration and connected clients/services will be removed.
We will currently not implement `--reinitialize` parameter for init,
if the keycloak config is misconfigured and an admin wants to put it back to
"factory settings".

### join

On installation and join script update, only configurations that are specific
to the server's FQDN.

Currently not needed and not implemented.

### upgrade-config

On join script updates, steps that are required to bring the current
configuration up-to-date (currently only register extensions). Only once
per update and per domain. The information what upgrades steps have already
been executed is stored in a "settings/data" object.
