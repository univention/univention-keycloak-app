# SPIKE: Automating Keycloak App Center releases

Investigation of the `ci-components` `app-release` tooling and a PoC for keycloak-app.

Issue: `univention/dev/projects/keycloak/keycloak-app#263`
PoC: keycloak-app MR **!344** (branch `jconde/tagged-release`)

---

## TL;DR

- The release can be automated with two pieces:
  1. **`app-release`** component (ci-components): the App Center jobs (create version, upload, promote, announce, GitLab release).
  2. **`tagged-release`** (new common-ci template, added in `v1.64.0`): the version stamp, releasing only from an explicit tag instead of semantic-release auto-releasing from `main`.
- A release is now one action: push a **protected `vX.Y.Z` (or `vX.Y.Z-nubusN`) tag**.
  CI builds and publishes the image, creates the App Center version, and after a manual confirmation promotes it to production and announces it.
- The manual `update-appcenter-test.sh` script, the `docker-update` Jenkins job and the manual `omar` steps are no longer _needed_.

---

## Q1. How does the tool work, and what can it do?

`app-release` is a CI **component** (`include: component: .../ci-components/app-release@2.5.2`). It generates a set of jobs:

- `create_app_version`: creates the App Center version (and a GitLab environment).
- `update_appcenter`: renders the templated App Center files and uploads them with `univention-appcenter-control`.
- `do_release` (manual): promotes the version from the test to the **production** App Center (runs `copy_from_appcenter.test.sh` + `update_mirror.sh` on `omar`).
- `check_release` (manual): verifies the app is reachable in production.
- `create_gitlab_release`, `send_mail`, `send_chat_message`: GitLab release + announcements.
- `delete_app_version`: removes a temporary version when its environment stops.

### Tagging, triggers, rules

Every job is gated by the same rule, `$CI_COMMIT_TAG =~ /^<release_tag_prefix><release_tag_constraint>$/`,
plus `$CI_MERGE_REQUEST_ID` and the default branch. The App Center version is derived from the tag: `APP_VERSION = ${CI_COMMIT_TAG#<prefix>}`.

| Pipeline | App Center version | Where |
| --- | --- | --- |
| protected tag `v26.6.2` | `26.6.2` | test, then production via manual `do_release` |
| protected tag `v26.6.2-nubus1` | `26.6.2-nubus1` | test, then production via manual `do_release` |
| feature branch / MR | `0.0.0-MR-<iid>` | test (throwaway, 3 days max) |
| `main` | `999.0.0-staging` | test (persistent) |


### Feature-branch docker images

On an MR, `create_app_version` makes a `0.0.0-MR-<iid>` test version pointing at the branch image so QA can install it.

### Does it mirror images to `docker.software-univention.de` on release?

We are now automatically using the `artifacts.software-univention.de` harbor instead of the old `docker.software-univention.de` mirror,
but nothing has been done about the mirroring Jenkins job. That is for ticket univention/dev/projects/keycloak/keycloak-app#264

---

## Q2. Which manual steps does it cover?

| Manual step today | Automated by |
| --- | --- |
| Edit `Version` in `app/ini`, run `update-appcenter-test.sh` | `create_app_version` (`Version` injected from the tag) |
| Upload App Center files | `update_appcenter` (jinja render + `univention-appcenter-control upload`) |
| `docker-update` Jenkins job (image to external registry) | `push-image-external` (to `nubus`) |
| SSH to `omar`, `copy_from_appcenter.test.sh`, `update_mirror.sh` | `do_release` (manual, runs on `omar`) |
| Announcement mail / chat | `send_mail` / `send_chat_message` |
| GitLab release | `create_gitlab_release` |

Still manual / out of scope:
- bumping `KEYCLOAK_VERSION` in `.gitlab-ci.yml` (needed to prepare the bump),
- the Jenkins product tests,
- the security-scan job version bump,
- and deciding the version to release.

---

## Q3. What modifications were needed?

- Replace `semantic-release` with **`tagged-release`** (new common-ci template) for versioning.
- Port `app/` to a templated **`appcenter/`** dir: `ini.jinja` (`Version = {{ APP_VERSION }}`), `compose.jinja`, and `preinst.jinja` using the component's `| source` filter (no `sed`/`base64` pre-job).
- Point the App Center `compose` at the **published** image (`artifacts.software-univention.de/{nubus,nubus-dev}/images/keycloak`) instead of `gitregistry...:latest`, **pinned by content digest** (`@sha256:...`) exported from the build.
- Make `create_app_version` **manual on MRs** (per the acceptance criteria).
- Add a protected-tag **workflow rule** so tag pipelines start.
- Support **`vX.Y.Z-nubusN`** tags (re-releasing the same Keycloak version), kept as a keycloak-local exception.
- Guard the release: a release tag's `X.Y.Z` must match `KEYCLOAK_VERSION` in `.gitlab-ci.yml`, else the pipeline fails fast.

---

## Q4. Compatible with `semantic-release` and tagging? How would both work?

- The component is **tag-driven and independent** of semantic-release.
- But semantic-release **auto-releases from `main`** (it cuts `vX.Y.Z` tags), which conflicts with "release only from an explicit, manually pushed tag." Running both is fragile: semantic-release's tags would themselves trigger the app-release jobs.
- **Decision: replace semantic-release with `tagged-release`.** It derives the version deterministically from the last `vX.Y.Z` tag; `main` and feature branches produce `"<base>-post-<slug>"` dev builds published only to `nubus-dev`; a real release happens only on a manual protected tag.
- Side note: semantic-release puts `[skip ci]` on its release commit, and `[skip ci]` takes precedence over `workflow:rules`, so its tag would not start a pipeline anyway. We still removed it for a clean, single model.

---

## Q5. Test App Center versions from feature branches?

Yes. On an MR, the **manual** `create_app_version` job creates a temporary `0.0.0-MR-<iid>` version in the **test** App Center (pointing at the branch's `nubus-dev` image), and `update_appcenter` uploads the files. The version is removed automatically when the MR is closed or merged, and we added `auto_stop_in: "3 days"` so stale ones expire on their own.

---

## Q6. Important things to consider

- **`univention-appcenter-control` version sort is fragile.**
  It sorts App Center versions with `distutils LooseVersion`, which throws `'<' not supported between 'int' and 'str'` on some strings.
  I had to remove `0-19.0.1-ucs5-recaptcha` because it crashed `create_app_version` once a `0.0.0-MR-...` version was added.
- The per-MR version string embeds the **slash project path** (`0.0.0-MR-<id>-univention/dev/.../keycloak-app!<iid>`), which is ugly and feeds the sort fragility.
- App Center changes take about **30 minutes to propagate**, so cleanups and retries lag.
- The App Center version scheme moves from the Keycloak version (`26.6.x-ucs1`) to the git tag, mitigated by tagging `vX.Y.Z-nubusN`.
- Requires a one-time **protected-tag** setup, and a tag pipeline uses the tagged commit's config (so the tag must be on a post-merge `main` commit).
- Coupled versioning for:
  - Keycloak App
  - Keycloak Helm Chart (now corresponding to the App Center version, but previously independent)
- Uncoupled / independent versions for:
  - univention-keycloak debian package

---

## Q7. Does it work with `artifacts.software-univention.de`?

Yes. common-ci's harbor jobs publish to `artifacts.software-univention.de/nubus{-dev}/images`, chosen by `PUBLIC_RELEASE`.
The App Center `compose` in the PoC references that published image. This replaces the old `docker.software-univention.de` mirror.

---

## Acceptance criteria

- [x] Questions answered (this document).
- [x] PoC with the new pipeline jobs: MR **!344**.
- [x] Test App Center version on a branch is triggered **manually** (`create_app_version` is `when: manual`).
- [x] Pipeline jobs restricted: container build/sign/push run only on tag/main/relevant changes; external publish + signing gated on `PUBLIC_RELEASE`; releases gated to **protected** `vX.Y.Z(-nubusN)` tags.
- [ ] Present results to the team (this presentation).
- [ ] Create the follow-up ticket.

---

## Follow-ups

- Run a full release end to end on a real protected tag.
- Finish the doc cleanups (`Pipelines > image_build` and `Test Environments` sections still describe the old flow).

## Open Questions

- In N4K, do we need to mention anything on the release-notes regarding the chart's huge version bump?
- Decide whether to upstream the `vX.Y.Z-nubusN` tag support into common-ci or keep it as a keycloak-local exception.
