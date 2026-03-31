# Changelog

## [0.24.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.24.1...v0.24.2) (2026-03-31)


### Bug Fixes

* **keycloak-bootstrap:** Change from bitnami common to nubus-common to avoid subPath: "%!s(<nil>)" error ([5d0d851](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/5d0d8512bf2b148d0286a6d1e5b869ced2acafb5)), closes [univention/dev/ucs#3458](https://git.knut.univention.de/univention/dev/ucs/issues/3458)

## [0.24.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.24.0...v0.24.1) (2026-03-30)


### Bug Fixes

* **deps:** Update Base Image ([2b95b53](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/2b95b538a1dd66885cbb7275271c87b891907bb2)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **docker:** switch from ucs-base-542 to new versioning scheme ([18b9229](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/18b92293e7b9aecfb54b3d32aa507dfbcc41d9bc)), closes [univention/dev/internal/team-horizon#27](https://git.knut.univention.de/univention/dev/internal/team-horizon/issues/27)

## [0.24.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.23.3...v0.24.0) (2026-03-26)


### Features

* **app:** Update Keycloak to 26.5.6 ([d2fceed](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/d2fceed1dbbb4e2ba653fc30f0fb5f9bdd2f1ef3)), closes [univention/dev/projects/keycloak/keycloak-app#259](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/259)

## [0.23.3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.23.2...v0.23.3) (2026-03-26)


### Bug Fixes

* **deps:** Update Base Image ([bd66523](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/bd6652396182e6240acf5ad5ba4bf857efc6f5af)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)

## [0.23.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.23.1...v0.23.2) (2026-03-25)


### Bug Fixes

* **docs:** Correct order for release action items ([549b615](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/549b615cecd8fce04e177d50995ffe208e9011ca)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)

## [0.23.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.23.0...v0.23.1) (2026-03-25)


### Bug Fixes

* Bump Keycloak to 26.5.6 ([64a206f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/64a206fa32bfa490cee4ff85778aec2d6b9d5d8d)), closes [univention/dev/projects/keycloak/keycloak-app#259](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/259) [univention/dev/projects/keycloak/keycloak-app#259](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/259)
* **helm:** do not fix image version so common-ci can find it and set it during packaging ([850a281](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/850a28131f5fc951dad4ce538c93ec2c3f175e27)), closes [univention/dev/projects/keycloak/keycloak-app#259](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/259)

## [0.23.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.22.0...v0.23.0) (2026-03-12)


### Features

* add new version of image tag ([612351f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/612351f1f747b44d0da39183b72575ae3e936af2))
* add new version of image tag with correct number versioning ([f71ada1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/f71ada1d6925593cc7a6cc3cffac44c749ac3e6b))
* add toggle for enabling importing users from LDAP into Keycloak ([9193b20](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/9193b20282edb3954d554f81f2757819ac500503)), closes [univention/dev/internal/team-nubus#1448](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1448)
* Add UMC gateway proxy to Keycloak ingress to avoid CORS issues ([eecc834](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/eecc834b6f9b0a812ed1db4667a6b84faa624c92)), closes [univention/dev/internal/team-nubus#1555](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1555)
* added portal to the keycloak realm CSP ([0ef4d23](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/0ef4d23689aed29fbd3fe6c96adcacea21e33b41))
* adhoc provisioning and keycloak upgrades. ([c92cbf1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/c92cbf17136ae130039f817acc849f64274a5837))
* adjust to common behavior / add tests for annotations, labels and image configuration ([37f928c](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/37f928cc9d9a8ad178c7052c606bb90c0cf1e91e)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* bump keycloak version to 26.2.5 ([098b7c9](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/098b7c9c8fa8eebd9434d8cd87aa7a75f1767928)), closes [univention/dev/internal/team-nubus#1312](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1312)
* Bump ucs-base-image to 5.2-1 ([a1fca7e](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/a1fca7eb2619f32a8548d5aa913ab0ca98de9cad)), closes [univention/dev/internal/team-nubus#1155](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1155)
* Bump ucs-base-image to use released apt sources ([1d17059](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/1d1705922b625338ddf392ea9f986966228bc9c8))
* changes to support the refactored umbrella values in a nubus deployment ([f838dfd](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/f838dfd8ecaf9e2930814e143230a4a674de701e))
* **ci:** enable malware scanning, disable sbom generation ([a40c97c](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/a40c97c75ca4071dc12483e582d763e4aa061b27))
* cleanup values ([824899c](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/824899c8cbca39247cc4e6de374869676f53c7de))
* enable frontchannel logout for the UMC SAML client ([fffc4b5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/fffc4b527a3cd0ef8bb59bfe8cdeafde69f88252)), closes [univention/dev/internal/team-nubus#1238](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1238)
* enable realm user events on initialization ([b461708](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/b4617089a668cea31884b9db0d85885ef576e743))
* enabled ingress configuration for keycloak by default ([20befe3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/20befe37ddbcbfa650c7227edd0160be421c3c48))
* generate keycloak secret / add tests on secrets ([8d600c8](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/8d600c8f82951cdffaf016303f4da503afbbe2ad)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* generate keycloak secret / add tests on secrets ([b7409e7](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/b7409e7d8c4c5a5ba52de544607bbc5098ccd725)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* generate ldap secret / add tests on secrets ([3008056](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/3008056cca83345c9901a4f9520b78bd34c546b4)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* generate oidc secret / add tests on secrets ([a50f1a6](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/a50f1a6ba1d410c96f350d725965d39c0f6cdb37)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* generate postgresql secret / add tests on secrets ([c6df93b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/c6df93be56075d3010b46bc626cd912967158de3)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* HA Keycloak ([8179915](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/8179915ae4d39ad0211321951851b8db5426947b))
* **helm:** Add component-specific extraEnvVars support ([d68f9dd](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/d68f9dd0e6e1cb84b1c87c4cfb17218df130783b)), closes [univention/dev/internal/team-nubus#977](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/977)
* **helm:** Refactor helm chart to allign with common behaviour ([62994b6](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/62994b64188915ff923d3cefd4ddbb65dbce23f6)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* initial commit (migration from souvap repos) ([76d456d](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/76d456dc4593ce4f2fe3bee14b25bad318016d4f))
* Keycloak 26 ([fb431e2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/fb431e24133497e313e4cf70c940fb959eba88e6)), closes [univention/dev/internal/team-nubus#1043](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1043)
* Keycloak 26 ([262c6ce](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/262c6ce974d7711c02ef04981880b47b9d8b0684))
* **keycloak-bootstrap:** add OIDC Relying Party client for UMC ([3746579](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/374657937d3c8c961d2fccacac5429310beb29b7)), closes [univention/dev/internal/dev-issues/dev-incidents#138](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/138)
* **keycloak:** updated keycloak image to 26.3.5 ([22a99d5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/22a99d58e15d52b8f38848b9bf7a62250ddf0b9f)), closes [univention/dev/internal/team-nubus#1464](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1464)
* migrate components secrets ([4e63786](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/4e6378622502fca95f334cf639afe925eaab2508))
* migrate components secrets ([8a9ae4d](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/8a9ae4de1fc301ed41807ffffdbeabb204ff0545))
* migration to gitregistry.knut.univention.de ([a1d6b02](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/a1d6b023f7df43dd6d7797b4118a5b87dbff8915))
* move and upgrade ucs-base-image to 0.17.3-build-2025-05-11 ([45b0d4e](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/45b0d4e61f7be8f76b10853bfd80504883617be9))
* rely on initContainers instead of helm hooks ([7f1777a](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/7f1777a5cb5b21afea5a6070856192a5eb792294))
* Support fetching mapped LDAP attributes in Keycloak ([c363fde](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/c363fde2cfdc07b5b3939852c529f92c8a307897)), closes [univention/dev/internal/team-nubus#1549](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1549)
* support global postgres ([7e41b94](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/7e41b94a77a9889cbb09887fa0fa9c253d3a2bc0))
* template postgresql connection settings ([2ee49b7](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/2ee49b726fac8f55fa5f4530d8ada526f8d2a31b))
* Trigger release ([f88e643](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/f88e643c6c59520a1ab8f03eba4d1022c24ee2c9)), closes [univention/dev/internal/team-nubus#1486](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1486)
* update UCS base image to 2024-09-09 ([c5a17e3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/c5a17e3ced096fdd354246201e66900b119b7b32))
* update ucs-base to 5.2.2-build.20250714 ([8ecd23c](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/8ecd23c85543c2f295ea0fdbd5f806fe71b7e5b6)), closes [univention/dev/internal/team-nubus#1320](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1320)
* update wait-for-dependency to 0.35.0 ([7f811f2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/7f811f20d01c7c5aa59b8ecf5e7ac678ab33d5ae)), closes [univention/dev/internal/team-nubus#1320](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1320)
* upgrade bitnami charts ([8ac37b3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/8ac37b3e6a311c288847a6ce0bc31ad1c01bd3cb)), closes [univention/dev/internal/team-nubus#1406](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1406)
* upgrade bitnami charts ([90ad013](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/90ad0131178694e6fc236b3d1ea7356c5b55c0fa)), closes [univention/dev/internal/team-nubus#1406](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1406)
* upgrade to keycloak 25 ([bc9b92d](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/bc9b92de6b86f626d4d647c4f2d68949f29ac3ba))
* upgrade UCS base image to 2024-12-12 ([4e27d98](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/4e27d982c73cee9561a0f686bca19c773d6b3780))
* upgrade univention-keycloak for Keycloak 25 ([384fa52](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/384fa52bd4292dbdb773c1e758ff5cb611ac563a))


### Bug Fixes

* add --use-refresh-tokens to UMC OAuth 2.0 client ([f64c6b8](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/f64c6b8dba5d208dc32ffc52e20a792f3a6f31bc)), closes [univention/dev/internal/team-nubus#1435](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1435)
* add .kyverno to helmignore ([2c7e099](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/2c7e0991c6c155a6bef9c3e65a501d2b087a2553))
* add .kyverno to helmignore ([23e6b08](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/23e6b085540b97c07e2c14df1acf819039b565e1))
* add assertions to ansible code to catch errors early ([866d8ed](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/866d8eda5a85220a98e82bf9b7ee78293c82148b))
* add bitnami to package-helm-charts ([47776d1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/47776d1f506f5d3ef697d9471067cf67a9af7a2a))
* add pre-commit service to docker-compose ([0176e7d](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/0176e7d48c567e9f855881ec4fd58d1a93a3bd5c)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* added flag to resolve backchannel connections ([edc369a](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/edc369afdd0d2b9d1cf7647795fd2663d30dcdcd))
* Added login error messages ([f0b3998](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/f0b3998bc84cb2a856b553d6692e40b031226190)), closes [univention/dev/internal/team-nubus#996](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/996)
* adjust ansible variable naming, fix assertions ([e9387a3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/e9387a3e3575d2b830153603e5b936b1afd8c5b9))
* Automatic kyverno tests ([f11ee80](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/f11ee805ffd8acd7142695b00cfae58a996244a1)), closes [univention/dev/internal/team-nubus#1426](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1426)
* avoid using --force ([faabb4d](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/faabb4d0e2f176b29717edf00e12af11f4574bee)), closes [univention/dev/internal/dev-issues/dev-incidents#158](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/158)
* Bump base-image ([7d61a8f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/7d61a8f3d7a680ab4da66c3804fae9e26f92935a)), closes [univention/dev/internal/team-nubus#1477](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1477)
* bump image to errata 298 ([6528792](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/652879239eb2f9049b6819de4547692310a16032)), closes [univention/dev/internal/team-nubus#1543](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1543)
* Bump image to errata 299 ([a107974](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/a107974dcb37dc9790d101fd8a81766015d794c4)), closes [univention/dev/internal/team-nubus#1518](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1518)
* bump ucs-base-image ([3b25d8a](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/3b25d8a8dba27f6f9afb2c02efc1ef9a7e10994b)), closes [univention/dev/internal/team-nubus#1448](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1448)
* bump umc-base-image version ([4b1ba8e](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/4b1ba8ec787a68490ed8ae68693dbec9c02d6c06)), closes [univention/dev/internal/team-nubus#1263](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1263)
* bump univention-keycloak version ([bfce7e1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/bfce7e1d64f11bc7fef8babb89aae201a309cd21))
* bump wait-for-dependency ([c51bbb7](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/c51bbb7607494b9390e96f3305850bdbbade9520)), closes [univention/dev/internal/team-nubus#1476](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1476)
* Bump wait-for-dependency image ([b86b0fb](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/b86b0fb5b7898fb3a237f82d7deb4c5c06293631))
* change deployment to a statefulset ([9de0a54](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/9de0a54b858dd5454cbbef7bb8d6a202b9e0e5f2))
* Correct dockerfile to be in line with our base image approach ([9f13bf9](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/9f13bf9fb80fa50c0059cf9944cb53823c532860)), closes [univention/dev/internal/team-nubus#1385](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1385)
* default registry and harbor ci ([1857b2b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/1857b2bd0a5339324adde2bcdfb3583dc87bc62d))
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.0 ([9d125fe](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/9d125fef9535ad9a5cb0c6af654f25ce1db8ea4d)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.1 ([5f66ce8](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/5f66ce87d864ebddcf45fc16aab9d7d8bee98633)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.1 ([2779395](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/277939530448a58602b0fd2f8262769926bca183)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.11 ([ad84cdc](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/ad84cdc021ee8301ad4494d984a1766e21b45848)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.2 ([53d4f7a](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/53d4f7a438f34e12270893050fa6731d8b292c87)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.2 ([fc8a64e](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/fc8a64e8abf9996cdc3e1018d7d5916a66e9cace)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.4 ([9c7a423](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/9c7a42375dd355e5cdb6581cd81285a1a79e9901)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.6 ([816c4f3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/816c4f38e391fc6cae50f64105120b16c05e7d8e)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.7 ([21453c8](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/21453c804d959d805ba203de96111a9a8ce4876f)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.9 ([e0b93d3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/e0b93d33644816370623386227f090164a467f0d)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.45.0 ([f0216dd](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/f0216dd23383fc52c2fcbe23ea37c2670543a71f)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.45.1 ([f5558cc](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/f5558cce057cb5d57828ee3f54ceb46a0ff2c6de)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.45.2 ([3dfc7df](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/3dfc7df62a1d870b62499dc724b63d2a17428dca)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.46.0 ([d88f743](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/d88f743e1012567865a09b0d6fb36b62cc1afac6)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.46.1 ([669b242](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/669b2422c994d4d0b3adc639c24eedce7b5ab383)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.47.0 ([caae561](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/caae56183387800d4e2a4230ec69849facea9ecc)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.48.0 ([359ab3f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/359ab3fac46139f690d51ff3559101b43f511d2a)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.48.0 ([b184803](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/b18480397439dea03609b26cf5d52a68be5a7adf)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.50.0 ([1aa0bf5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/1aa0bf574377b8711a0649ede446384ef6e45899)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.50.0 ([c9055da](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/c9055da0e694b6155423476f72b169e056e3e9e8)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.50.2 ([d392fa2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/d392fa2c55cc769d3366f536852ade03d482f71f)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.50.2 ([80fd5bc](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/80fd5bc783b365264f51608b20e77799fe834ee7)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.50.3 ([c2e0efc](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/c2e0efcebfeb3a1d4d94b57f40cd7f38557a5177)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.50.3 ([62192fe](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/62192fea3484c478598754a423270cae9598d87d)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.51.0 ([cae86f5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/cae86f5d72950f9bd4088cfd149157a4652df94f)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.51.0 ([793ca71](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/793ca7150888ae90ecc9ff91cbc3e08a43ae3fd7)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.52.0 ([442d851](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/442d85122cdd8c74c491915d65afba15ce56fb08)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.52.0 ([4b238b1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/4b238b134611708bde7daa2c9d2e642bb453373a)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.52.1 ([8871549](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/887154914bd691f96fa28a513820e7798f7f1000)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.52.1 ([eced5a8](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/eced5a8cca33629089c62cf55bec524efba4dc37)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.53.0 ([5c5cee7](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/5c5cee7fdc1bc7f58233b5bd0ca43f52460745ea)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.53.0 ([2c2291c](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/2c2291c6a8c75ab674733be80a0f741d6e54862f)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.53.1 ([cf049f9](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/cf049f99d54280e22ff51e6fcd9acd44e523a23e)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.53.1 ([ff656a8](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/ff656a8284263d11af4a6e880c2125d344655c0f)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.54.0 ([955fe92](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/955fe9283fbac9a1c4376f50283e5f79aad3b512)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.54.0 ([6d1c512](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/6d1c512b0013f9a3448dc1da958028f96933a699)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.54.1 ([e1e2a48](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/e1e2a487cdb0a858f2bc401c3d3783c3f120df3d)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.54.1 ([4ea5098](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/4ea509859dac67f2ee7cef34b8c4c7902bf706a8)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.54.2 ([f17c9f0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/f17c9f02edb4a4ef2f1fccba075c4fbbae2dda7e)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.54.2 ([21f6a39](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/21f6a39e436c79ea6f73de4061872ba6a3251229)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.55.1 ([6714c96](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/6714c96d251a35fb73e5cdf3b0444791eb172316)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.55.1 ([9d59527](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/9d59527bfa76bba10582d7734a8307594ae3582a)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.55.2 ([d8ad534](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/d8ad5341eb90b3f06799156474b5115579407f38)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.55.2 ([afa8699](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/afa86996c248b30ac790df733bd798fa467e0969)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.55.3 ([b70986a](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/b70986a6ee307c7d1e8ad3fcb7fc0d81a98a2776)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.55.3 ([5cb0bb6](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/5cb0bb621ed8dda8aeffc3f2894985f4a4207bc7)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base Docker tag to v5.2.2-build.20250814 ([a459a71](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/a459a711ce1fb4b03b14da5e80412279a76edce4)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base Docker tag to v5.2.2-build.20250821 ([38359a9](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/38359a93002fb889abf2c01b10232dab6b88688f)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base Docker tag to v5.2.3-build.20251009 ([7fa5988](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/7fa5988f4e99b79762d997b92d5547813a1f5462)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base Docker tag to v5.2.3-build.20251023 ([26232ef](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/26232effe95953b46b0cf5b7476275a28a9dd090)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base Docker tag to v5.2.3-build.20251024 ([ca08c24](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/ca08c24d4634261094f7bb4c70357e17963eee26)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base Docker tag to v5.2.3-build.20251030 ([6163b22](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/6163b22ac95f90108db13db8e05355a03b6c734c)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base Docker tag to v5.2.4-build.20260223 ([38ff851](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/38ff8512df45401c56b3e6944e7ffdfa88ac993a)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* **deps:** Update nubus/images/wait-for-dependency Docker tag to v0.36.6 ([a91314d](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/a91314d0096cbc7f8f8019afe87b73c560f8fa7c)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* disable logging of credentials ([06ef1bd](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/06ef1bdbcc2d82224d9b1b28312b0aca50965ac4))
* **docker:** Bump version of keycloak client, old version is gone ([c072a5d](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/c072a5daca020fc42ef9fdb87a96fe8b3d4e1bb9)), closes [univention/dev/internal/dev-issues/dev-incidents#138](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/138)
* **Dockerfile:** update base image version ([05228ed](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/05228ed02c149c957cfdb3cefb28e39d06a1bc80)), closes [univention/dev/internal/team-nubus#1549](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1549)
* ensure bootstrap job name stays same as before ([0e94e8f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/0e94e8fed3ea373012b9df1011eaf29f22db7d28)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* **envvar:** Remove UNIVENTION_META_JSON as it is addressed using Ingress in Keycloak-Extension's Proxy ([5213f94](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/5213f94b30637c7bf2c2c01cd2a1e5cefd65a5b5))
* **envvar:** Set UNIVENTION_META_JSON ([57ffcc4](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/57ffcc4f8bfff3a3d5792933d6ce563d753bdbb3))
* final version of wait-for-dependency ([cf7012a](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/cf7012abf20cfd1ef187b2998ea05a2e20ef162e)), closes [univention/dev/internal/team-nubus#1155](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1155)
* fix namespace template in serviceacount ([2181aa8](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/2181aa8602fd27a53f3a90710ebcd6deefc5e0a0)), closes [univention/dev/internal/team-nubus#1075](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1075)
* fix the keycloak container image version ([6f41998](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/6f419984e20f08d7a4dc1b2f11e23bdc3518e79f)), closes [univention/dev/nubus-for-k8s/nubus-helm#18](https://git.knut.univention.de/univention/dev/nubus-for-k8s/nubus-helm/issues/18)
* **helm-unittests:** Move keycloak-bootstrap unittests into subdirectory ([2c4bd52](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/2c4bd52909a663c2ca577f5f482c5ab4873a19d3)), closes [univention/dev/projects/keycloak/keycloak-app#257](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/257)
* **helm:** Add component-specific extraEnvVars support ([fa56635](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/fa56635619b98a15ddcd5212b18118b412e5f387)), closes [univention/dev/internal/team-nubus#977](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/977)
* **helm:** Blank lines after | are not valid yaml. Make the helm output parseable by ruamel ([b7735b9](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/b7735b9e631155c427a450e1fbc42f2768f1aee5)), closes [univention/dev/internal/team-nubus#989](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/989)
* **helm:** Mark root filesystem as read-only ([1818186](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/1818186e8709d7a0f38791744f1e19b9e82fde43)), closes [univention/dev/internal/team-nubus#1325](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1325)
* **helm:** Rename some valaues/variable to make their meaning clearer ([20fc2e2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/20fc2e282f5ba4ff6f2ad12ea7219bfe8b77e73f)), closes [univention/dev/internal/dev-issues/dev-incidents#138](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/138)
* initContainer resources templating ([e7a22a1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/e7a22a1507c221ca9e033b72df80f70cbc357ddf)), closes [univention/dev/internal/team-nubus#1356](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1356)
* **keycloak-bootstrap:** Cleanup and use nubus-common as base ([7db153c](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/7db153c628e9a72ba651c0bf635a0a6050695a25)), closes [univention/dev/internal/dev-issues/dev-incidents#138](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/138)
* **keycloak-bootstrap:** Handle secrets correctly ([ef6ee7e](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/ef6ee7ee6e84006c1057a4bad54436c394f2b89f)), closes [univention/dev/internal/dev-issues/dev-incidents#138](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/138)
* **keycloak:** Add ENV variables to support password renewal and setting the logLevel ([6f2f685](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/6f2f68585ff18a2ab0697a2024bc58f07788a759))
* **keycloak:** Enable metrics by default, template ports and extend optional ingress (which is by default disabled) ([769f6df](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/769f6df075431fd7764b2d68a95ab69f21683b22))
* **keycloak:** Fix ENV var name for setting the log level ([37d0357](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/37d035736bd0f7fbbb710475b1348eb8af36b925))
* **keycloak:** Initial commit ([e57a593](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/e57a593fb3cca8e4f4897830881295cd2f96796c))
* **keycloak:** Semantic-release trigger ([94b45e4](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/94b45e42f10a428b32e50c7eb7e63b29cece48d9))
* **keycloak:** Update Keycloak image to version 26.4.6 ([990208c](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/990208cf8b8188c651f8f06754ffe5eb90882027)), closes [univention/dev/projects/keycloak/keycloak-app#249](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/249)
* **keycloak:** upgrade to keycloak 26.3.1 ([e4e22db](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/e4e22db2514d987c58fbb28b123b462d67d3b60b)), closes [univention/dev/internal/team-nubus#1355](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1355)
* kyverno lint ([3ac1990](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/3ac19906a4da6a704527fd6a456fc9e10e6d2182))
* kyverno lint ([a3b27c7](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/a3b27c7687dcfe23cfdd154affd18e88e1679b2b))
* Kyverno lint ([d5470a3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/d5470a3618c3dbe65fcd2b1d763087ccca950e89))
* lint ([683f1c6](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/683f1c6d1af23506460eb3f6c27a5bed43ebcd12))
* Make the Keycloak client name static and not dynamic ([1bdde1c](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/1bdde1cede9a3a13c8c8ad1fdc801f3032a337ed)), closes [univention/dev/internal/team-nubus#1435](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1435)
* Merge conflicting files after cherry-picking the keycloak-bootstrap repo ([9f151af](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/9f151af220b7299275c9f28d28991f398a107c83)), closes [univention/dev/projects/keycloak/keycloak-app#257](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/257)
* Merge conflicting files after combining this repo with the keycloak-helm-chart repo ([1b49929](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/1b49929a800d6b8864150603ce581675072b13df)), closes [univention/dev/projects/keycloak/keycloak-app#257](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/257)
* meta.json ([94f446b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/94f446bd7b3c54c9fab800a85fe2d805145fda62))
* missing configuration for Headless service ([8cb6d10](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/8cb6d10d38c83e14f24dd46b9bc2ca628429c71c))
* move addlicense pre-commit hook ([dd6c2b6](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/dd6c2b6bd6580e16bc6aa401cdc6b85da58bade9))
* move addlicense pre-commit hook ([9ed0cdd](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/9ed0cdda8a1147d8ed0eeb4b18219f91f58da261))
* namespace template in serviceacount ([81f92f2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/81f92f23aa70cd1f1e364eb5d832958de49a1446)), closes [univention/dev/internal/team-nubus#1075](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1075)
* pin the new version ([6a2cca9](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/6a2cca9689caa73def49982d829becf8a22ddcea)), closes [univention/dev/internal/team-nubus#1435](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1435)
* ping right version ([55cd695](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/55cd6956d3b022a61b3b1c36043295079ed19081)), closes [univention/dev/internal/dev-issues/dev-incidents#158](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/158)
* QA changes ([07ab67e](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/07ab67e6af5d0141dc4c627e5a6288b790cb86e8))
* QA changes ([16431d5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/16431d55684c8643b750eb32f7a9a67c140f07e4))
* remove pined version of univention-keycloak-client ([96b73e8](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/96b73e81ce7fd2e74161737ba635679a0e785c64)), closes [univention/dev/internal/team-nubus#1424](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1424)
* set ro filesystem ([13b13c5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/13b13c57e7a6a2fcb0023dc85cac1b3fe0135f5f))
* set the cache stack to jdbc-ping ([0d42f84](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/0d42f842456aaf739d1307734c97718a5c62b67a)), closes [univention/dev/internal/team-nubus#1374](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1374)
* switch to image v0.22.0 for Keycloak 26.5.5 ([85f15a7](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/85f15a7b2ee0b3fa5307ca2c1c3f63c4bc478231)), closes [univention/dev/projects/keycloak/keycloak-app#258](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/258)
* test keycloak 26.4.4 ([5d522c3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/5d522c37d328ab967dfffae9544618cb5a122f8a)), closes [univention/dev/internal/dev-issues/dev-incidents#186](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/186)
* trigger release, use wait-for-keycloak script ([b59ff71](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/b59ff714e1c1cb8b05265c080b339a53fa6a0c39)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* unfork leftovers ([826c159](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/826c159adccfc6febc686bdce473e7f96776f220))
* **univention-keycloak:** added `univention-keycloak` from an ucs branch. ([24e9be2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/24e9be25eb4f73d0042b0640a99b306d0f7c865f)), closes [univention/dev/internal/team-nubus#1355](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1355)
* **univention-keycloak:** remove local `univention-keycloak` copy. ([d6a2699](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/d6a26995ffe39db9b4e2b86deac4be45ead11602)), closes [univention/dev/internal/team-nubus#1355](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1355)
* update common-ci ([9f67bf1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/9f67bf1cae580edcf52f8fe19c848657583b0c23))
* update common-ci to main ([0b48389](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/0b483892482348f8bd8660d227393756bdf931e3))
* update common-ci to main ([4a60064](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/4a60064cd5d50f812018881412d12ef15fadf15b))
* Update helm_docs ([dfed091](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/dfed0912de66b61133a2c3d0a65d608c9e22a122)), closes [univention/dev/internal/team-nubus#1080](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1080)
* Update Keycloak to 26.5.4 ([8b7231b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/8b7231b88ef9e7bbd5ee962f9ac24a0aac0638ae)), closes [univention/dev/nubus-for-k8s/nubus-helm#18](https://git.knut.univention.de/univention/dev/nubus-for-k8s/nubus-helm/issues/18)
* Update Keycloak to 26.5.5 ([de2fe37](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/de2fe37e84a1022050ad858a612b05986b22d95a)), closes [univention/dev/projects/keycloak/keycloak-app#258](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/258)
* Update Keycloak to 26.5.5 ([fae6129](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/fae61299d354aa3bff79eacdc27788da27e7184b)), closes [univention/dev/projects/keycloak/keycloak-app#258](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/258)
* Update Keycloak to 26.5.5 ([496ab55](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/496ab55d10e4b4259926c27652623fea66b650ca)), closes [univention/dev/projects/keycloak/keycloak-app#258](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/258)
* Updated Keycloak image path ([ae76c5f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/ae76c5ff0aaa23cfba2cd28150348a2901262da9)), closes [univention/dev/internal/team-nubus#1080](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1080)
* upgrade ansible package ([eddc540](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/eddc5404b679af25f3688ed84e21f7c8e2446b1b))
* Upgrade base image to 5.2-3 and added new custom package ([c641bda](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/c641bdae0e65e499a27cee626f2e68ff2e568b38)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)
* upgrade univention-keycloak-client version ([2f3fb63](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/2f3fb63c9ccae1b47d5a83d58e829970bf5b3c95))
* Use a customized version of univention-keycloak ([2cbc874](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/2cbc874adbeb2433fdc53ed96126b0616f3149eb)), closes [univention/dev/internal/team-nubus#1385](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1385)
* use default cluster ingress class if not defined ([1fa6c4d](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/1fa6c4d01942caedcca42e216a093ba21e78c3f1)), closes [univention/dev/internal/team-nubus#1134](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1134)
* use normal ucs-base base image which includes a correct version of univention-keycloak now ([e844b8c](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/e844b8cb9acccdeb123c9d13ab81bce057417f52)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* use the right wait-for-dependency image ([32752fb](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/32752fb943d401ce9e311ae22e3470e823f774b9)), closes [univention/dev/internal/team-nubus#1263](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1263)
* workaround to avoid bugged univention-keycloak version ([d4ac76c](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/d4ac76cbe630736b0a7cfc8303543c139ca8d8ad))


### Reverts

* Revert "build: add kyverno test pre-commit" ([d5ac352](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/d5ac3521ada55dde0d2c5f786308d5c43eb6af62))
* Revert "fix: kyverno lint" ([a7776a3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/a7776a356fd9e0e201c10d533c8dff5fdc4d365b))
* Revert "ci: update common-ci version" ([8a0f9a2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/8a0f9a2e16ed4bc73e2ccb3d49ae1a69cafb6b62))
* Revert "feat: Keycloak 26" ([25965cc](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/25965ccd9c5e94e979067d3ff8598ff51f93f9ca))

## [0.21.3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.21.2...v0.21.3) (2026-03-11)


### Bug Fixes

* Bump base-image ([4c7bbc5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/4c7bbc5cf10750981f6ea7ef5a386fd3a64a8d7d)), closes [univention/dev/internal/team-nubus#1477](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1477)

## [0.21.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.21.1...v0.21.2) (2026-02-25)


### Bug Fixes

* **deps:** Update nubus/images/wait-for-dependency Docker tag to v0.36.6 ([5080a69](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/5080a69c5e351b5824b470d578c22d1a42f4327b)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.21.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.21.0...v0.21.1) (2026-02-24)


### Bug Fixes

* **deps:** Update gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base Docker tag to v5.2.4-build.20260223 ([83b6275](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/83b62756c8cfc939545ee1af1e5d2e9fd4c0fc52)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.21.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.20.6...v0.21.0) (2026-02-23)


### Features

* Support fetching mapped LDAP attributes in Keycloak ([aeaac7e](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/aeaac7e21017c1cb2863d43e1d2dbd36bfb6c29a)), closes [univention/dev/internal/team-nubus#1549](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1549)


### Bug Fixes

* **Dockerfile:** update base image version ([842d035](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/842d0355298d0ad27dcf2a66e87534d2733a896c)), closes [univention/dev/internal/team-nubus#1549](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1549)

## [0.20.6](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.20.5...v0.20.6) (2026-02-11)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.55.3 ([a435fcc](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/a435fcca967cdb953be9cdd7bc2aa417e42507ec)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.20.5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.20.4...v0.20.5) (2026-01-24)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.55.2 ([db29ccc](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/db29cccd1872f403f435aa982e9bcdf2e6be8360)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.20.4](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.20.3...v0.20.4) (2026-01-16)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.55.1 ([0b3e2fa](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/0b3e2fab141aa039c88f70241fa0cc8d4d26702f)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.20.3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.20.2...v0.20.3) (2026-01-14)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.54.2 ([699455b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/699455b149ee4a574cf11cf8c327b46fa3e3a46a)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.20.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.20.1...v0.20.2) (2026-01-13)


### Bug Fixes

* Bump image to errata 299 ([6b6cc56](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/6b6cc56d1e60175a8eeb7a4a04d3605cd27b3f30)), closes [univention/dev/internal/team-nubus#1518](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1518)

## [0.20.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.20.0...v0.20.1) (2025-12-23)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.54.1 ([99cf2f5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/99cf2f5ed8a5027d95b7dc873e41891c88bd4f24)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.20.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.20...v0.20.0) (2025-12-10)


### Features

* **helm:** Add component-specific extraEnvVars support ([bff6b4b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/bff6b4bd26aa4ac771930405006b39520c50a7ac)), closes [univention/dev/internal/team-nubus#977](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/977)

## [0.19.20](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.19...v0.19.20) (2025-12-10)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.54.0 ([070555f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/070555f0bd479058c057f664bbeaa0b302f8e952)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.19](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.18...v0.19.19) (2025-12-03)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.53.1 ([bd108ab](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/bd108abb3cd0859d86517e989dd4ff5fd835426a)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.18](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.17...v0.19.18) (2025-12-02)


### Bug Fixes

* bump image to errata 298 ([bb8b812](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/bb8b81288ed52a4c87b329c59019d2be40b75c94)), closes [univention/dev/internal/team-nubus#1543](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1543)

## [0.19.17](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.16...v0.19.17) (2025-11-29)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.53.0 ([a1c249e](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/a1c249e9d7b503569d825f182e725bf45b7dc24d)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.16](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.15...v0.19.16) (2025-11-28)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.52.1 ([db746c1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/db746c1cb4c5405dec1207045bb2e6bdcd740c97)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.15](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.14...v0.19.15) (2025-11-27)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.52.0 ([1de55d9](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/1de55d928df34e3fddca14f609888324b8b06d55)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.14](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.13...v0.19.14) (2025-11-21)


### Bug Fixes

* bump wait-for-dependency ([a2912d5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/a2912d5188f24fde4982547cd3973eba91d44c64)), closes [univention/dev/internal/team-nubus#1476](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1476)

## [0.19.13](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.12...v0.19.13) (2025-11-20)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.50.3 ([31d3a29](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/31d3a29dadce1da54385c3a2d53ca385d0a67d46)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)
* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.51.0 ([82e144c](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/82e144c9015d4ef36e61e36ee9803f9ba0b18525)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.12](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.11...v0.19.12) (2025-11-18)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.50.2 ([43821f8](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/43821f8191fc72cb8ff2caadea39fd79da8a2ed7)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.11](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.10...v0.19.11) (2025-11-12)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.50.0 ([2681b2b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/2681b2b772a7f680e974fbaf1ee4d49bdfbc5843)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.10](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.9...v0.19.10) (2025-11-04)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.48.0 ([6a15b43](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/6a15b4302bb7d7c226535ed926b8f814e2663167)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.9](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.8...v0.19.9) (2025-11-04)


### Bug Fixes

* **deps:** Update gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base Docker tag to v5.2.3-build.20251030 ([98dcf5b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/98dcf5b2304a3fa4381b58dca6d41c72c355f70d)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.8](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.7...v0.19.8) (2025-11-01)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.47.0 ([da5f43e](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/da5f43e949f57f5b0534f67decba01c44542657d)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.7](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.6...v0.19.7) (2025-10-29)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.46.1 ([0d169c0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/0d169c03138228c03726a85555146a993a7ffdbb)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.6](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.5...v0.19.6) (2025-10-29)


### Bug Fixes

* **deps:** Update gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base Docker tag to v5.2.3-build.20251024 ([841b189](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/841b189baf6ef9a3f320d01ee4d5dd4d46e34530)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.4...v0.19.5) (2025-10-29)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.46.0 ([e6779ec](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/e6779ec8e77cf433a647a7614e822ef1651a5b6c)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.4](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.3...v0.19.4) (2025-10-25)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.45.2 ([8c7556e](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/8c7556ea46615948fddeb981caeeabf9f21980f3)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.2...v0.19.3) (2025-10-24)


### Bug Fixes

* **deps:** Update gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base Docker tag to v5.2.3-build.20251023 ([18f37b6](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/18f37b6adf962e91795c873ab56b4ab559a154f5)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.1...v0.19.2) (2025-10-23)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.45.1 ([bcd1235](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/bcd1235ca0989febaf83faf6a0ce842f65456930)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.19.0...v0.19.1) (2025-10-22)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.45.0 ([6dbb20d](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/6dbb20d2f92227f61413e4543e469b984d684628)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.19.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.18.0...v0.19.0) (2025-10-17)


### Features

* add toggle for enabling importing users from LDAP into Keycloak ([25e5054](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/25e505406693005d4aa4486e83af2524c72344cf)), closes [univention/dev/internal/team-nubus#1448](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1448)


### Bug Fixes

* bump ucs-base-image ([346c4cb](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/346c4cb2ed2524ae627422180879907953e52314)), closes [univention/dev/internal/team-nubus#1448](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1448)

## [0.18.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.17.9...v0.18.0) (2025-10-15)


### Features

* adjust to common behavior / add tests for annotations, labels and image configuration ([ad78dd7](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/ad78dd7af15cf9da41b67ab70c2a2ead99fcb193)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* generate keycloak secret / add tests on secrets ([7a4e35d](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/7a4e35d5417e5615ae648f7dbc4731b06e55fc5c)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* generate ldap secret / add tests on secrets ([03dfe00](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/03dfe00cb6fad1bfaaa436c66198e0d3528180a9)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* generate oidc secret / add tests on secrets ([a9cf280](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/a9cf28047c9dcb65cd1a4eee0c99ed00987e6b28)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)


### Bug Fixes

* ensure bootstrap job name stays same as before ([cffa18a](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/cffa18a28cf871a85599c03ff6e33d5d06de1a25)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* use normal ucs-base base image which includes a correct version of univention-keycloak now ([6b06182](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/6b061821337d14c54ed1dbbf40cd461d5e5f8e71)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)

## [0.17.9](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.17.8...v0.17.9) (2025-10-15)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.11 ([aaf4cd5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/aaf4cd5bab42f3d8264c383a5cb14ca81317b4c8)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.17.8](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.17.7...v0.17.8) (2025-10-14)


### Bug Fixes

* **deps:** Update gitregistry.knut.univention.de/univention/dev/projects/ucs-base-image/ucs-base Docker tag to v5.2.3-build.20251009 ([4a1b27f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/4a1b27f0f8010dd5db1f6a64d403f881da878d5e)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.17.7](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.17.6...v0.17.7) (2025-10-02)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.9 ([94ab07e](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/94ab07e857b4196816bb08105c91b265a81ca746)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.17.6](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.17.5...v0.17.6) (2025-10-01)


### Bug Fixes

* remove pined version of univention-keycloak-client ([d526bb2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/d526bb20538012e4e070545d52f777dee6ff1af9)), closes [univention/dev/internal/team-nubus#1424](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1424)

## [0.17.5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.17.4...v0.17.5) (2025-09-18)


### Bug Fixes

* Make the Keycloak client name static and not dynamic ([d5a18cd](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/d5a18cd4e67549622e0a76a03bb4fe59d994290b)), closes [univention/dev/internal/team-nubus#1435](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1435)
* pin the new version ([1b212b0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/1b212b0c373b57e41134ae58b479788ca30dd59e)), closes [univention/dev/internal/team-nubus#1435](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1435)

## [0.17.4](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.17.3...v0.17.4) (2025-09-17)


### Bug Fixes

* add --use-refresh-tokens to UMC OAuth 2.0 client ([a05b6f4](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/a05b6f483e3bc486884b002bd384327037f8df5f)), closes [univention/dev/internal/team-nubus#1435](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1435)

## [0.17.3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.17.2...v0.17.3) (2025-09-16)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.7 ([bafb645](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/bafb6453ea0295a40ad37f582909bdf280539898)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.17.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.17.1...v0.17.2) (2025-09-16)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.6 ([a7f297c](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/a7f297cc8456d92d7d83b8b534b4f5e4fbbb3830)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.17.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.17.0...v0.17.1) (2025-09-15)


### Bug Fixes

* avoid using --force ([8ad41e6](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/8ad41e6d2f781533b9317546b4053abaaea16bfa)), closes [univention/dev/internal/dev-issues/dev-incidents#158](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/158)
* ping right version ([b9ab205](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/b9ab205bc3f9ae915c0b6b1fcf8359f9b3440a7e)), closes [univention/dev/internal/dev-issues/dev-incidents#158](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/158)

## [0.17.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.16.5...v0.17.0) (2025-09-12)


### Features

* **keycloak-bootstrap:** add OIDC Relying Party client for UMC ([efaed2f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/efaed2f938d8eba4ebf5ed1450106dbd58a2e04c)), closes [univention/dev/internal/dev-issues/dev-incidents#138](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/138)


### Bug Fixes

* **docker:** Bump version of keycloak client, old version is gone ([277d133](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/277d133082e220f584e0c7dade11cf84839abc34)), closes [univention/dev/internal/dev-issues/dev-incidents#138](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/138)
* **helm:** Rename some valaues/variable to make their meaning clearer ([f1f2159](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/f1f2159687faddd385bffb1bebc7cb385bd074f5)), closes [univention/dev/internal/dev-issues/dev-incidents#138](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/138)
* **keycloak-bootstrap:** Cleanup and use nubus-common as base ([93a9303](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/93a9303038ef07e0103f319920739dabb8201f82)), closes [univention/dev/internal/dev-issues/dev-incidents#138](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/138)
* **keycloak-bootstrap:** Handle secrets correctly ([9db79f9](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/9db79f93579acf2dd007c98bac3fb9ab91492b40)), closes [univention/dev/internal/dev-issues/dev-incidents#138](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/138)

## [0.16.5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/compare/v0.16.4...v0.16.5) (2025-09-12)


### Bug Fixes

* Upgrade base image to 5.2-3 and added new custom package ([214c848](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/commit/214c84857ab7efdf40e1320c95f36784af996bc4)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-bootstrap/issues/0)

## [0.16.4](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.16.3...v0.16.4) (2026-03-03)


### bug fixes

* fix the keycloak container image version ([bee65c9](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/bee65c90c3591d83e704fda3ce31b2d859bc93a6)), closes [univention/dev/nubus-for-k8s/nubus-helm#18](https://git.knut.univention.de/univention/dev/nubus-for-k8s/nubus-helm/issues/18)

## [0.16.3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.16.2...v0.16.3) (2026-03-02)


### bug fixes

* update keycloak to 26.5.4 ([e60f8c5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/e60f8c5161e63ee374f68585fbf73d4894a36b0f)), closes [univention/dev/nubus-for-k8s/nubus-helm#18](https://git.knut.univention.de/univention/dev/nubus-for-k8s/nubus-helm/issues/18)

## [0.16.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.16.1...v0.16.2) (2026-02-11)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.55.3 ([6c0982f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/6c0982f289a5777147bfe62fa8e73072a5ffefd8)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.16.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.16.0...v0.16.1) (2026-01-24)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.55.2 ([f62a790](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/f62a7909d017e4298906e18a7a1c446810b099b8)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.16.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.15...v0.16.0) (2026-01-19)


### features

* add umc gateway proxy to keycloak ingress to avoid cors issues ([140db6c](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/140db6c345897d753d098eaf0f1a27bfafa320d5)), closes [univention/dev/internal/team-nubus#1555](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1555)

## [0.15.15](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.14...v0.15.15) (2026-01-16)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.55.1 ([8ea85d2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/8ea85d26b28ff150f607e1fa775f8d22538f2374)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.15.14](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.13...v0.15.14) (2026-01-14)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.54.2 ([df8b6be](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/df8b6be184fa94fa8495e2a5aca473a59a36972a)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.15.13](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.12...v0.15.13) (2025-12-23)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.54.1 ([c3e192f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/c3e192f33bc0777f810d619a9ea4167c9a7829ae)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.15.12](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.11...v0.15.12) (2025-12-11)


### bug fixes

* **keycloak:** update keycloak image to version 26.4.6 ([8225c24](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/8225c2453ca42cfe0eb6ca7c0557e58a59bcbec7)), closes [univention/dev/projects/keycloak/keycloak-app#249](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/249)

## [0.15.11](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.10...v0.15.11) (2025-12-10)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.54.0 ([5c01386](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/5c01386bf8df70a795fb00b724fd5ddd392aa65f)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.15.10](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.9...v0.15.10) (2025-12-05)


### bug fixes

* **helm:** add component-specific extraenvvars support ([fba2505](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/fba25058261e843ff6d7120e60523dbf52a4f8fb)), closes [univention/dev/internal/team-nubus#977](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/977)

## [0.15.9](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.8...v0.15.9) (2025-12-03)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.53.1 ([98ed57b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/98ed57bf8a488b456093cf56f7d7eb51b57ce4e5)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.15.8](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.7...v0.15.8) (2025-11-29)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.53.0 ([02276a5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/02276a551821db502875f536e5073e5e39157133)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.15.7](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.6...v0.15.7) (2025-11-28)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.52.1 ([96db7ed](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/96db7ed0f8798a01bc8922bd96d5af43134880ab)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.15.6](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.5...v0.15.6) (2025-11-27)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.52.0 ([4691707](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/4691707f1a93ee90b470a68a338a59cd1dab5e1a)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.15.5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.4...v0.15.5) (2025-11-25)


### bug fixes

* set the cache stack to jdbc-ping ([c999611](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/c999611ef334ae5e4bb9be49c231791ceb8d6790)), closes [univention/dev/internal/team-nubus#1374](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1374)

## [0.15.4](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.3...v0.15.4) (2025-11-20)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.51.0 ([98c0506](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/98c05067b7cab8addd737c96916abac7087d989c)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.15.3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.2...v0.15.3) (2025-11-19)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.50.2 ([55c28f3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/55c28f391d1d07ad0c9ab4135c9060eb087e9a69)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)
* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.50.3 ([34a8c58](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/34a8c5848484ce0f8cb6a1fbcdd8f9b4f13145b1)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.15.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.1...v0.15.2) (2025-11-17)


### bug fixes

* test keycloak 26.4.4 ([2e826b6](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/2e826b6d7c7204037999eef20c4981ec533d7f42)), closes [univention/dev/internal/dev-issues/dev-incidents#186](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/186)

## [0.15.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.15.0...v0.15.1) (2025-11-12)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.50.0 ([7a166ea](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/7a166ead52ddd9c3587f374812bf6a2aaf3d8c22)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.15.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.14.1...v0.15.0) (2025-11-05)


### features

* trigger release ([f579e60](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/f579e606dae1d105ced65a8fea179d435685d52d)), closes [univention/dev/internal/team-nubus#1486](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1486)

## [0.14.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.14.0...v0.14.1) (2025-11-05)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.48.0 ([9835e4e](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/9835e4e54d63a29c42c40cff7d0cd84018febab7)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.14.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.13.0...v0.14.0) (2025-10-20)


### features

* **keycloak:** updated keycloak image to 26.3.5 ([0e7bcd7](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/0e7bcd7b0dd205e844b76d363507e3b1ee54e641)), closes [univention/dev/internal/team-nubus#1464](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1464)

## [0.13.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.12.2...v0.13.0) (2025-10-15)


### features

* generate keycloak secret / add tests on secrets ([86bd966](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/86bd9662326927e72ed9ecd5222f5ede0f105a4d)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* generate postgresql secret / add tests on secrets ([d36ee64](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/d36ee64c8d38af170ab527c7b1fbf4cef66f1647)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)
* **helm:** refactor helm chart to allign with common behaviour ([36b5b75](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/36b5b75eb296004349e4b6c9db47086de113b1d2)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)


### bug fixes

* add pre-commit service to docker-compose ([f1c8e44](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/f1c8e4445c404cfe86a33900b36296ef5229ffea)), closes [univention/dev/internal/team-nubus#1398](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1398)

## [0.12.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.12.1...v0.12.2) (2025-09-24)


### bug fixes

* automatic kyverno tests ([d02e1ab](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/d02e1ab91d7b14be26a2f33ead22c52a8482966b)), closes [univention/dev/internal/team-nubus#1426](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1426)

## [0.12.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.12.0...v0.12.1) (2025-08-28)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.2 ([7e0ce48](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/7e0ce4881d8e83591b75a9fe4dbd52e32df1ba92)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.12.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.11.5...v0.12.0) (2025-08-26)


### features

* upgrade bitnami charts ([1bbaeaa](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/1bbaeaadf36a719ef167adc7baaf36d19877dd48)), closes [univention/dev/internal/team-nubus#1406](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1406)

## [0.11.5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.11.4...v0.11.5) (2025-08-19)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.1 ([1dd7f21](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/1dd7f213eb3a5cf9491014db8bae663086c3f7d7)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.11.4](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.11.3...v0.11.4) (2025-08-08)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.0 ([da15f46](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/da15f46a0dad207ba6e9999c482755f2618821fd)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/issues/0)

## [0.11.3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.11.2...v0.11.3) (2025-08-01)


### bug fixes

* initcontainer resources templating ([e0512de](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/e0512de0dbace799b8b33dd260c65c720efcee8e)), closes [univention/dev/internal/team-nubus#1356](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1356)

## [0.11.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.11.1...v0.11.2) (2025-07-29)


### bug fixes

* **keycloak:** upgrade to keycloak 26.3.1 ([a86df8a](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/a86df8a670d7bf2a3d3ddc6f885900886f7b4080)), closes [univention/dev/internal/team-nubus#1355](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1355)

## [0.11.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.11.0...v0.11.1) (2025-07-25)


### bug fixes

* **helm:** mark root filesystem as read-only ([2745766](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/27457664940e84943003ac79286ed920fb20318c)), closes [univention/dev/internal/team-nubus#1325](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1325)

## [0.11.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.10.2...v0.11.0) (2025-06-30)


### features

* bump keycloak version to 26.2.5 ([752a5f4](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/752a5f4dea044759e5d56993ae2d2e849fdab9a9)), closes [univention/dev/internal/team-nubus#1312](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1312)

## [0.10.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.10.1...v0.10.2) (2025-06-23)


### bug fixes

* use default cluster ingress class if not defined ([00bb6e9](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/00bb6e99a420a3c1569469ce6b31e45926ac367c)), closes [univention/dev/internal/team-nubus#1134](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1134)

## [0.10.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.10.0...v0.10.1) (2025-06-06)


### bug fixes

* move addlicense pre-commit hook ([8f1491a](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/8f1491aaba4aaec9ccedf7cee000a00eceab75aa))
* update common-ci to main ([b667f16](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/b667f1666578d53dd17fc331eea80cbbaa100136))

## [0.10.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/compare/v0.9.3...v0.10.0) (2025-05-05)


### features

* keycloak 26 ([a61e579](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-helm-chart/commit/a61e5791f1f5317ca823575ea426a38c60c0749b)), closes [univention/dev/internal/team-nubus#1043](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1043)

## [0.9.3](https://git.knut.univention.de/univention/components/keycloak/compare/v0.9.2...v0.9.3) (2025-04-09)


### bug fixes

* added login error messages ([7bf240f](https://git.knut.univention.de/univention/components/keycloak/commit/7bf240f8ac97144aff87cefb96a846dc2752061d)), closes [univention/dev/internal/team-nubus#996](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/996)

## [0.9.2](https://git.knut.univention.de/univention/components/keycloak/compare/v0.9.1...v0.9.2) (2025-03-25)


### bug fixes

* update helm_docs ([ef94da0](https://git.knut.univention.de/univention/components/keycloak/commit/ef94da0ebe427aef44bc86cda2199e848299b2ab)), closes [univention/dev/internal/team-nubus#1080](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1080)
* updated keycloak image path ([17057dc](https://git.knut.univention.de/univention/components/keycloak/commit/17057dc23b3b40d233d0282f342f87b594391d8e)), closes [univention/dev/internal/team-nubus#1080](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1080)

## [0.9.1](https://git.knut.univention.de/univention/components/keycloak/compare/v0.9.0...v0.9.1) (2025-03-21)


### bug fixes

* namespace template in serviceacount ([5341b5d](https://git.knut.univention.de/univention/components/keycloak/commit/5341b5dafeeceff3fcc89006ed8a5cea326ab969)), closes [univention/dev/internal/team-nubus#1075](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1075)

## [0.9.0](https://git.knut.univention.de/univention/components/keycloak/compare/v0.8.0...v0.9.0) (2025-03-05)


### features

* add new version of image tag ([fee61f3](https://git.knut.univention.de/univention/components/keycloak/commit/fee61f3ec8b63eea483e096729a089b7e96b58b1))
* add new version of image tag with correct number versioning ([1581c89](https://git.knut.univention.de/univention/components/keycloak/commit/1581c895dc8d35f7fc7a50101fc586737c496e59))

## [0.8.0](https://git.knut.univention.de/univention/components/keycloak/compare/v0.7.2...v0.8.0) (2025-02-26)


### features

* adhoc provisioning and keycloak upgrades. ([a48ce13](https://git.knut.univention.de/univention/components/keycloak/commit/a48ce13a5a3a8d62dee11d8f7fc9cc2f404ac721))

## [0.7.2](https://git.knut.univention.de/univention/components/keycloak/compare/v0.7.1...v0.7.2) (2025-02-10)


### bug fixes

* add .kyverno to helmignore ([abe589c](https://git.knut.univention.de/univention/components/keycloak/commit/abe589c061076bb5f40146751b8ad22e302e0a67))

## [0.7.1](https://git.knut.univention.de/univention/components/keycloak/compare/v0.7.0...v0.7.1) (2024-11-25)


### bug fixes

* kyverno lint ([8312e51](https://git.knut.univention.de/univention/components/keycloak/commit/8312e51a099126b96e5769370d59bdc37a962b59))

## [0.7.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.6.0...v0.7.0) (2026-03-03)


### features

* **app:** update keycloak to 26.5.2 ([4df4a11](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/4df4a1193f41a33fc4ba92c459c2943659a9a79f)), closes [univention/dev/projects/keycloak/keycloak-app#250](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/250)
* **app:** update keycloak to 26.5.3 ([0988829](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/0988829ce050a425b400ede703e052f6a0a53d95)), closes [univention/dev/projects/keycloak/keycloak-app#254](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/254)
* **app:** update keycloak to 26.5.4 ([0a71702](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/0a717021c4e0580cd336df0669e6f5f796678df8)), closes [univention/dev/nubus-for-k8s/nubus-helm#18](https://git.knut.univention.de/univention/dev/nubus-for-k8s/nubus-helm/issues/18)
* **app:** version bump to 26.5.2-ucs2 for re-release of app ([e9abdf9](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/e9abdf9819c464d7d202593137857e7cae7174b5)), closes [univention/dev/projects/keycloak/keycloak-app#250](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/250)


### bug fixes

* **apache-config:** allow acme http-01 challenges on keycloak virtualhost ([3944121](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/39441214d3891250d18d0797e6889d45a54d81d8)), closes [#58836](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/58836) [univention/dev/ucs#3291](https://git.knut.univention.de/univention/dev/ucs/issues/3291)
* trigger release with valid semantic release token ([7158882](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/7158882bb83caa97e03d9d2b02257f15acc896eb)), closes [univention/dev/nubus-for-k8s/nubus-helm#18](https://git.knut.univention.de/univention/dev/nubus-for-k8s/nubus-helm/issues/18)

## [0.6.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.5.0...v0.6.0) (2025-12-09)


### features

* **app:** update to keycloak 26.4.7 ([321928b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/321928b696e236913fb787af89bdba92f49b6f1e)), closes [univention/components/keycloak-app#248](https://git.knut.univention.de/univention/components/keycloak-app/issues/248)

## [0.5.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.4.2...v0.5.0) (2025-12-09)


### features

* **app:** update to keycloak 26.4.6 ([c567821](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/c567821f78780d768646cb357ef679e2c468e4a8)), closes [univention/components/keycloak-app#248](https://git.knut.univention.de/univention/components/keycloak-app/issues/248)

## [0.4.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.4.1...v0.4.2) (2025-11-21)


### bug fixes

* **keycloak-app/changelog:** remove link to 26.1.4 release notes ([0cb5481](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/0cb5481b9061f8f8563b9b20a223575e40b7e7fb)), closes [univention/dev/docs/organization#224](https://git.knut.univention.de/univention/dev/docs/organization/issues/224)
* **keycloak-app:** update redirected links ([a35c942](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/a35c942176b1f4993c09f17aa22485937260128a)), closes [univention/dev/docs/organization#224](https://git.knut.univention.de/univention/dev/docs/organization/issues/224)

## [0.4.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.4.0...v0.4.1) (2025-11-17)


### bug fixes

* fix broken ldap federation after 26.4.1 ([66d7b1a](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/66d7b1aca14784f4ed9b3ac7040d6f94f36b787e)), closes [univention/dev/internal/dev-issues/dev-incidents#186](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/186)

## [0.4.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.3.0...v0.4.0) (2025-11-04)


### features

* **app:** update to keycloak 26.4.2 ([3da5543](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/3da55432a058242ebf01e4c3f6832ba979b9f239)), closes [univention/dev/projects/keycloak/keycloak-app#245](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/245)


### bug fixes

* **deps:** update base image to version 5.2.3-build-20251030 ([d69ce07](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/d69ce07d24c3582b5510f391d804184b6f3923fc)), closes [univention/dev/internal/team-nubus#1486](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1486)

## [0.3.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.8...v0.3.0) (2025-10-13)


### features

* **app:** update to keycloak 26.3.5 ([fb0cc3f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/fb0cc3f4a63ea7dc53c0375286d6e8df3a9add85)), closes [#58249](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/58249) [univention/dev/ucs#2849](https://git.knut.univention.de/univention/dev/ucs/issues/2849)


### bug fixes

* **app:** change in health endpoint - issue univention/dev/ucs[#2849](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/2849) ([eeddb2f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/eeddb2f3cfa7ce70a2ca26f2ff67914d8ddc7ea7))
* **app:** do not replicate db password to replica and memberserver ([1fb9b3b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/1fb9b3b4c96276c43330dcda5393cbe3701ecd20)), closes [#58249](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/58249) [univention/dev/ucs#2849](https://git.knut.univention.de/univention/dev/ucs/issues/2849) [univention/dev/ucs#2849](https://git.knut.univention.de/univention/dev/ucs/issues/2849)
* **app:** session is no longer a class variable - issue univention/dev/ucs[#2849](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/2849) ([7301252](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/730125227a7dcc47661c33c6ff07a8b7f29a8cf8))

## [0.2.8](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.7...v0.2.8) (2025-09-29)


### bug fixes

* **changelog:** wrong versionn in app/ini ([397266e](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/397266e25e75dc3b10c48b7798681fa3b12e0d94)), closes [univention/components/keycloak-app#243](https://git.knut.univention.de/univention/components/keycloak-app/issues/243)

## [0.2.7](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.6...v0.2.7) (2025-09-18)


### bug fixes

* **changelog:** wrong cve was mentioned ([fe3354b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/fe3354bdb317d1b96f0ca7c6925e3da431131581)), closes [univention/components/keycloak-app#243](https://git.knut.univention.de/univention/components/keycloak-app/issues/243)

## [0.2.6](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.5...v0.2.6) (2025-08-29)


### bug fixes

* **deps:** update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.2 ([2698acd](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/2698acde889175b244779170266f1edb10a52c2e)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)

## [0.2.5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.4...v0.2.5) (2025-07-25)


### bug fixes

* trigger release ([55a56eb](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/55a56eb4749da45252fea182059953fdd25b4be0)), closes [univention/dev/internal/team-nubus#1355](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1355)

## [0.2.4](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.3...v0.2.4) (2025-07-14)


### bug fixes

* **pipeline:** fix repository moving ([3ad31dc](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/3ad31dc5ac74b940f344b6ee3826c822b87d96be)), closes [univention/dev/ucs#2808](https://git.knut.univention.de/univention/dev/ucs/issues/2808)

## [0.2.3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.2...v0.2.3) (2025-06-26)


### bug fixes

* **pipeline:** fix repository moving ([b43d789](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/b43d78983116953dca43d7e3ff4a35a6ca06ab0e)), closes [univention/dev/ucs#2808](https://git.knut.univention.de/univention/dev/ucs/issues/2808)

## [0.2.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.1...v0.2.2) (2025-06-23)


### bug fixes

* **docs:** add missing argument for 'samba-tool spn add' ([c2e9970](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/c2e997019c5ee01eb1bbd08852423aa006ed689a))

## [0.2.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.0...v0.2.1) (2025-06-19)


### bug fixes

* container-ldap and udm-rest-api repositories migration ([4cff503](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/4cff503e7e91a71d17796d3a4d4a60e30a80e3d5)), closes [univention/dev/internal/team-nubus#0](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/0)

## [0.2.0](https://git.knut.univention.de/univention/components/keycloak-app/compare/v0.1.0...v0.2.0) (2025-05-11)


### features

* move and upgrade ucs-base-image to 0.17.3-build-2025-05-11 ([385a78c](https://git.knut.univention.de/univention/components/keycloak-app/commit/385a78c418489bb16211a1f66d858193548cf9c7))


### bug fixes

* move docker-services ([a13421d](https://git.knut.univention.de/univention/components/keycloak-app/commit/a13421d94454c22736c84c2dbe8fdea757e9136f))
* update common-ci to main ([feb15fb](https://git.knut.univention.de/univention/components/keycloak-app/commit/feb15fbc923e4a432c91a4e3ddfda718b43972ec))

## [0.1.0](https://git.knut.univention.de/univention/components/keycloak-app/compare/v0.0.1...v0.1.0) (2025-05-08)


### features

* **docs:** add nubus operation manual reference to keycloak app manual ([06b701f](https://git.knut.univention.de/univention/components/keycloak-app/commit/06b701fc83ba11f234320c232f221597962123a2)), closes [univention/dev/docs/nubus-docs#91](https://git.knut.univention.de/univention/dev/docs/nubus-docs/issues/91)
* **docs:** adjust content to nubus narrative ([9f722ad](https://git.knut.univention.de/univention/components/keycloak-app/commit/9f722ad9aed4d413973372ab8ce378615f711478)), closes [univention/dev/docs/nubus-docs#91](https://git.knut.univention.de/univention/dev/docs/nubus-docs/issues/91)
* **docs:** adjust heading levels to structure position ([89b60d0](https://git.knut.univention.de/univention/components/keycloak-app/commit/89b60d00fa834992175b5673d94a217164a2609c)), closes [univention/dev/docs/nubus-docs#91](https://git.knut.univention.de/univention/dev/docs/nubus-docs/issues/91)
* **docs:** move ad hoc provisioning into own section ([c074c76](https://git.knut.univention.de/univention/components/keycloak-app/commit/c074c76ff0992cd67a606c5a30b19c65f9cc117f)), closes [univention/dev/docs/nubus-docs#91](https://git.knut.univention.de/univention/dev/docs/nubus-docs/issues/91)
* **docs:** relocate ad fs configuration after custom authentication flow ([d1e4c26](https://git.knut.univention.de/univention/components/keycloak-app/commit/d1e4c26ea139cd11415ffc78e0a58c6b8284a5d0)), closes [univention/dev/docs/nubus-docs#91](https://git.knut.univention.de/univention/dev/docs/nubus-docs/issues/91)
* **docs:** review feedback ([7ab711b](https://git.knut.univention.de/univention/components/keycloak-app/commit/7ab711b4b2059502fae5243c69069382239d4905)), closes [univention/dev/docs/nubus-docs#91](https://git.knut.univention.de/univention/dev/docs/nubus-docs/issues/91)


### bug fixes

* **docs:** update ucs manual reference to ucs 5.2 ([a61a627](https://git.knut.univention.de/univention/components/keycloak-app/commit/a61a627f7562318add4990019f42267639d49dbb)), closes [univention/dev/docs/nubus-docs#91](https://git.knut.univention.de/univention/dev/docs/nubus-docs/issues/91)

## 0.0.1 (2025-04-30)


### features

* add default group dn configuration ([2b769ce](https://git.knut.univention.de/univention/components/keycloak-app/commit/2b769ce146d00e1fed55cad6f972534d1b3714bd))
* add setup_adhoc_provisioning.py script ([00fbc42](https://git.knut.univention.de/univention/components/keycloak-app/commit/00fbc42f36e63f8e2cbd78567944c538ec866fe3))
* at least use a unicode symbol for language dropdown ([1f0e67d](https://git.knut.univention.de/univention/components/keycloak-app/commit/1f0e67db086edcb7b6685229b590496a555c5682)), closes [#190](https://git.knut.univention.de/univention/components/keycloak-app/issues/190)
* change color of language to primary color of buttons in ucs theme ([7bb87b9](https://git.knut.univention.de/univention/components/keycloak-app/commit/7bb87b92212a8ce31c116e690d91f66165e7d8aa)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* configure umc for saml login with keycloak on app install ([fe6fcc4](https://git.knut.univention.de/univention/components/keycloak-app/commit/fe6fcc42ad1fe7e2f6a3b9eeddbc0a25ccea163e)), closes [#211](https://git.knut.univention.de/univention/components/keycloak-app/issues/211)
* install schema extension univentionremoteidentifier ([f316f59](https://git.knut.univention.de/univention/components/keycloak-app/commit/f316f5944558833b46757ff005f211d1cae5014e))
* **keycloak-app:** increase navigation in section 8.2.1 ([d51e462](https://git.knut.univention.de/univention/components/keycloak-app/commit/d51e462c70827018b086e9bb4668fd4d23952920))
* ldap schema extension install ([e39a1e1](https://git.knut.univention.de/univention/components/keycloak-app/commit/e39a1e13f086f9bc5da4bec3129b72f44b5fdcc4))
* **login:** adjust error message for wrong credentials during login ([e798e8c](https://git.knut.univention.de/univention/components/keycloak-app/commit/e798e8c96e57f00e7504e8ac2b30bb5ca47d8f74)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* re enable ad-hoc federation ([c7ee21d](https://git.knut.univention.de/univention/components/keycloak-app/commit/c7ee21d9f3a75dacb974e68d73067696d1f9302f))
* remove openapi generated univention-directory-manager client ([bb776c5](https://git.knut.univention.de/univention/components/keycloak-app/commit/bb776c53f0f99f41bfbb550f86f3d106d1b07728))
* **udm:** reimplement java udm create, search and delete ([8225a50](https://git.knut.univention.de/univention/components/keycloak-app/commit/8225a50d190669d69eef8c189cdacc214e91cfc7))
* **univention-authenticator:** allow configuring source and remote identifier from the keycloak web ui ([6a4a106](https://git.knut.univention.de/univention/components/keycloak-app/commit/6a4a106c2537bbfeeaa09d7778bec49f1487c124))
* **univention-authenticator:** migrate openapi generated udm to new codebase ([bdabaf8](https://git.knut.univention.de/univention/components/keycloak-app/commit/bdabaf8b6ea2f21f75aba3d26020b94c837ee954))
* use official ucs theme color for language selection ([ee820ba](https://git.knut.univention.de/univention/components/keycloak-app/commit/ee820ba6744c68abb59084f18ba00dc11607f3c9)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)


### bug fixes

* **ad-hoc:** adapt some variable names and properties ([10fb706](https://git.knut.univention.de/univention/components/keycloak-app/commit/10fb7060a1ca0a70c1b5bf47909045e0493b14aa))
* **ad-hoc:** add  license comments ([a6b80ad](https://git.knut.univention.de/univention/components/keycloak-app/commit/a6b80ad8348e9b52956b565d55e8093f3874c657))
* **ad-hoc:** add semicolon to config.java ([e7ac364](https://git.knut.univention.de/univention/components/keycloak-app/commit/e7ac364d1861a34fc7aca7f91598a4c0b31aa396))
* **ad-hoc:** add support for the following behavior ([b3927f6](https://git.knut.univention.de/univention/components/keycloak-app/commit/b3927f68c2169d4e123c115f2feba2da697f3b68))
* **ad-hoc:** code review changes ([1382f25](https://git.knut.univention.de/univention/components/keycloak-app/commit/1382f25083e3e69ac6d0a5d8c1fbccad5effac2b))
* **ad-hoc:** create a new univentionauthenticator inside the univentionauthenticatorfactory for each initial login event to supply the keycloaksession to the univentionauthenticator ([f3662b7](https://git.knut.univention.de/univention/components/keycloak-app/commit/f3662b7fc36190082ead2480717f46e50854aa33))
* **ad-hoc:** delete the keycloak user if a problem occurs while ensuring the udm user. make the authenticator.authenticate better testable ([3bfab97](https://git.knut.univention.de/univention/components/keycloak-app/commit/3bfab9753fb144c836960e9bc7ca4cb4f9916f1f))
* **ad-hoc:** finalize log messages ([e8b957d](https://git.knut.univention.de/univention/components/keycloak-app/commit/e8b957da9203f21e320b7d27ab6709f142f662a8))
* **ad-hoc:** handle and test case where objectguid is not base64 encoded ([0bfdb34](https://git.knut.univention.de/univention/components/keycloak-app/commit/0bfdb340fe3019e1400918aa7e6a3b52761035c0))
* **ad-hoc:** integrate univention-authenticator and udm client into meta pom.xml with all keycloak extensions ([e47f6d2](https://git.knut.univention.de/univention/components/keycloak-app/commit/e47f6d219b57b8f28b25309e0e74ecaed7fb5d94))
* **ad-hoc:** refactor identitymappingconfig and improve test coverage ([2cdf254](https://git.knut.univention.de/univention/components/keycloak-app/commit/2cdf2546010ca9656e24b3f29f90efd8d1ede8e2))
* **ad-hoc:** refactor univentionauthenticator to use identitymappingconfigfactory ([056674c](https://git.knut.univention.de/univention/components/keycloak-app/commit/056674cd585b01360f1703a92bdbeba39258a6e7))
* **ad-hoc:** remove auth failure for case of missing sourceuserprimaryid_udmkey temporarily. e2e tests failing. ([b819864](https://git.knut.univention.de/univention/components/keycloak-app/commit/b819864ad37f4376fd7217367870e087a6a3f6b6))
* **ad-hoc:** remove auth failure for case of missing sourceuserprimaryid_udmkey temporarily. e2e tests failing. ([bf40726](https://git.knut.univention.de/univention/components/keycloak-app/commit/bf407267d4b3fccb34e65634e8da475797f593e4))
* **ad-hoc:** remove unused import ([0432e49](https://git.knut.univention.de/univention/components/keycloak-app/commit/0432e4964c551340619fefa0ed5be2c66ab3a530))
* **ad-hoc:** remove user in case of auth failure if config load fails ([20b36b6](https://git.knut.univention.de/univention/components/keycloak-app/commit/20b36b6644dfe2522b438ff9effbd874e17c908c))
* **ad-hoc:** remove user in case of auth failures ([4215e33](https://git.knut.univention.de/univention/components/keycloak-app/commit/4215e3385a74f7fa8c10187b40f608283f9ab398))
* **ad-hoc:** remove user in case of auth failures ([cdf6e81](https://git.knut.univention.de/univention/components/keycloak-app/commit/cdf6e81d12fb9f038d8a977ce9f8c12fa86b75b7))
* **ad-hoc:** rename config class, remove group from required properties ([01966da](https://git.knut.univention.de/univention/components/keycloak-app/commit/01966dab4b5534dbda2d42ef8d29e4f3e6dbe7b4))
* **ad-hoc:** restructure log message and assign the correct levels ([4f14b81](https://git.knut.univention.de/univention/components/keycloak-app/commit/4f14b8184e6ecae2b272e0a4b6e7943d8d1e1aaf))
* **ad-hoc:** wip more robust error handling ([df2ab64](https://git.knut.univention.de/univention/components/keycloak-app/commit/df2ab6408e5c4cdd3b82e6c97f036b2abada072f))
* add minimal height of login dialog ([9e54ae6](https://git.knut.univention.de/univention/components/keycloak-app/commit/9e54ae6b795e2539d75fe6fb48f0dc018ca177ae)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* add password visibility script from keycloak 23.0 ([3d85d42](https://git.knut.univention.de/univention/components/keycloak-app/commit/3d85d4240e6e33a1ab109ad9fbd4063c57a5fe15)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* add source and remote identifier to setup_adhoc_provisioning script ([7047698](https://git.knut.univention.de/univention/components/keycloak-app/commit/7047698f2949935cae83e17e391227e1825c4186))
* add toggle password visibility button ([7d91ba2](https://git.knut.univention.de/univention/components/keycloak-app/commit/7d91ba22441291765778cc8a53cff1c0b97fa7a8)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* **app:** remove unused ucrv for adhoc provisioning ([0eefb75](https://git.knut.univention.de/univention/components/keycloak-app/commit/0eefb75160bb1d87ea5f9abcbc3334ccce478551))
* **ci:** increased keycloak version in .gitlab-ci.yaml ([8a71ff7](https://git.knut.univention.de/univention/components/keycloak-app/commit/8a71ff71ed6248d8e8721434a8248e4e21ec0fb4))
* **docs-dev:** document release steps ([beb945f](https://git.knut.univention.de/univention/components/keycloak-app/commit/beb945f8642f7c693d1a9fa581f548d03a226254))
* **docs:** add missing necessary parameters for backup and restore ([e8c9a2a](https://git.knut.univention.de/univention/components/keycloak-app/commit/e8c9a2a5d12d9759c670407956471401001ebde1))
* **docs:** document postgres requirement and keycloak/auto-migration ([8e2b22b](https://git.knut.univention.de/univention/components/keycloak-app/commit/8e2b22b8368c9c58fa39f502f1798a437bfc64da)), closes [univention/dev-issues/dev-incidents#55](https://git.knut.univention.de/univention/dev-issues/dev-incidents/issues/55) [univention/dev-issues/dev-incidents#52](https://git.knut.univention.de/univention/dev-issues/dev-incidents/issues/52)
* **docs:** fix keycloak docs url ([22b3486](https://git.knut.univention.de/univention/components/keycloak-app/commit/22b348680307496c6ceea2c28610f4161fda9430))
* fix font size and color of links in general keycloak errors ([2a08905](https://git.knut.univention.de/univention/components/keycloak-app/commit/2a08905b69c0fd200623356b9b0830b65cb10d54)), closes [#192](https://git.knut.univention.de/univention/components/keycloak-app/issues/192)
* fix outline of language selection buttons ([7afcf77](https://git.knut.univention.de/univention/components/keycloak-app/commit/7afcf7752b90d1298200dabc46fa11f6dd28e65e)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* fix styling of error messages ([a389d18](https://git.knut.univention.de/univention/components/keycloak-app/commit/a389d18bcc54a412108de1560ac2c6c96326d71d)), closes [#192](https://git.knut.univention.de/univention/components/keycloak-app/issues/192)
* **inst:** pass proper credentials to 'add_host_record_in_ad' ([cf4129f](https://git.knut.univention.de/univention/components/keycloak-app/commit/cf4129fb6cbf2f51bec9959a77d5994a400bfb0c)), closes [univention/components/keycloak-app#218](https://git.knut.univention.de/univention/components/keycloak-app/issues/218)
* **keycloak-app:** use univention portal ([8df77d0](https://git.knut.univention.de/univention/components/keycloak-app/commit/8df77d0838235ac3b25d0ba3142d693dbba9950c))
* **login-oauth-grant:** fix broken layout ([b417346](https://git.knut.univention.de/univention/components/keycloak-app/commit/b417346fd5eb93875866750756698063e6fe8229)), closes [univention/ucs#2381](https://git.knut.univention.de/univention/ucs/issues/2381)
* **login:** adjust css variables ([47880dd](https://git.knut.univention.de/univention/components/keycloak-app/commit/47880dddc95be3c2f00f97ecb253a8b4e8229262)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* **login:** align keycloak login css with umc ([7669a19](https://git.knut.univention.de/univention/components/keycloak-app/commit/7669a19abf199df395aca95f5abe6d0dad726bf7)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* **login:** change position of language selection button in login dialog ([91137a2](https://git.knut.univention.de/univention/components/keycloak-app/commit/91137a266c9c6d4c4ff430ef3a1fce9f2d6484ea)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* place license headers ([afbda58](https://git.knut.univention.de/univention/components/keycloak-app/commit/afbda58689ef5ec45122efe9fbb9f741d029b03f))
* qa changes ([fba633f](https://git.knut.univention.de/univention/components/keycloak-app/commit/fba633f9c5b1373a91ac3316a487c73985d5fcc6))
* rebase social part of login.ftl to keycloak 26.0 ([8b40af2](https://git.knut.univention.de/univention/components/keycloak-app/commit/8b40af258f8c688c30cecbe05cd9e00cecd78cb9)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* remove duplicate jar from container to avoid split imports warning log ([2422ede](https://git.knut.univention.de/univention/components/keycloak-app/commit/2422ede893a006f03130f8016a0575b87b7f1d0e))
* **udm:** add factory class to allow for better testability ([785580a](https://git.knut.univention.de/univention/components/keycloak-app/commit/785580a823f751181a99ef0d25fea9fd4b355bab))
* use harbor image ([fcf27a2](https://git.knut.univention.de/univention/components/keycloak-app/commit/fcf27a2f3c6d080e8c85fe64c92a4e0d241060da))
* use original tab index of keycloak ([a4981bc](https://git.knut.univention.de/univention/components/keycloak-app/commit/a4981bc0e9ad703ea2c0953fa3b4bb587c54d4d8)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)


### reverts

* revert "fixup! issue #140: automatic redirect to https" ([036cb77](https://git.knut.univention.de/univention/components/keycloak-app/commit/036cb775d3e1f48a83d73e5be50e2bec9703c1cc)), closes [#140](https://git.knut.univention.de/univention/components/keycloak-app/issues/140)
