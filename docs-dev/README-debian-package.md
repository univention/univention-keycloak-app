[TOC]

# Building the univention-keycloak Debian Package

The `univention-keycloak` Debian package
lives in the `univention-keycloak/` directory of this repository.
It was moved here from the UCS mono-repo
to allow faster iteration without waiting for the weekly errata release cycle.

## CI Pipelines

Two CI components from [`ci-components`](https://git.knut.univention.de/univention/dev/tooling/ci-components) handle the Debian package builds:

### Feature Branches (Aptly branch builds)

On every push to a **non-protected branch** or **merge request**, the pipeline:

1. **`prepare-aptly`** (prepare stage) - Creates a per-branch Aptly repository (idempotent).
2. **`build-deb`** (build stage) - Builds the package with a pre-release version suffix (`A~5.2.5.<timestamp>`) and uploads it to the branch Aptly repo.

The pre-release version sorts **below** the real release version in apt, so when the real errata lands, apt upgrades automatically.

To test on a VM, add the branch repo:

```bash
echo "deb [trusted=yes] http://omar.knut.univention.de/build2/git/keycloak-app <branch-slug> main" \
  > /etc/apt/sources.list.d/branch.list
apt-get update
apt-get install univention-keycloak-client
```

The branch slug is the GitLab-slugified branch name (e.g., `feat/my-change` becomes `feat-my-change`).

To clean up the Aptly repo, trigger the **`aptly-remove`** job manually in the pipeline (`.post` stage). Repos auto-expire after 6 months.

### Protected Branches (Errata release builds)

On pushes to **protected branches** (e.g., `main`) where `univention-keycloak/debian/changelog` was modified, the pipeline:

1. **`errata-import`** (publish stage) - Imports the package metadata to the build system via `repo_admin.py` (runs on `omar`).
2. **`errata-build`** (publish stage) - Builds and publishes the package via `build-package-ng` (runs on `ladda`).

The UCS version for the errata build is defined by the `UCS_VERSION` variable in `.gitlab-ci.yml`.

## How to Release a New Version

1. Make your changes to the scripts in `univention-keycloak/scripts/`.
2. Update `univention-keycloak/debian/changelog` with `dch` or manually:
   ```bash
   cd univention-keycloak
   dch -v <new-version> "Description of changes"
   ```
3. Commit and push. On a feature branch, the aptly pipeline builds a dev package. On `main`, the errata pipeline publishes to the UCS repository.

## Bumping the UCS Version

When a new UCS release starts (e.g., moving from 5.2-5 to 5.3-0):

1. Update `UCS_VERSION` in `.gitlab-ci.yml`:
   ```yaml
   UCS_VERSION: "5.3-0"
   ```
2. Commit and push to the new release branch.

## Relationship to keycloak-bootstrap container

The `keycloak-bootstrap` container (`docker/keycloak-bootstrap/Dockerfile`) installs the `univention-keycloak` scripts directly from source via `COPY` instructions. This means:

- Changes to `univention-keycloak/scripts/` are picked up immediately when the container is rebuilt.
- No need to wait for a Debian package build to update the container.
- The Debian package is still built and published for UCS systems that install `univention-keycloak-client` via apt.
