# Changelog

## [0.6.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.5.0...v0.6.0) (2025-12-09)


### Features

* **app:** Update to Keycloak 26.4.7 ([321928b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/321928b696e236913fb787af89bdba92f49b6f1e)), closes [univention/components/keycloak-app#248](https://git.knut.univention.de/univention/components/keycloak-app/issues/248)

## [0.5.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.4.2...v0.5.0) (2025-12-09)


### Features

* **app:** Update to Keycloak 26.4.6 ([c567821](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/c567821f78780d768646cb357ef679e2c468e4a8)), closes [univention/components/keycloak-app#248](https://git.knut.univention.de/univention/components/keycloak-app/issues/248)

## [0.4.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.4.1...v0.4.2) (2025-11-21)


### Bug Fixes

* **keycloak-app/changelog:** Remove link to 26.1.4 release notes ([0cb5481](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/0cb5481b9061f8f8563b9b20a223575e40b7e7fb)), closes [univention/dev/docs/organization#224](https://git.knut.univention.de/univention/dev/docs/organization/issues/224)
* **keycloak-app:** Update redirected links ([a35c942](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/a35c942176b1f4993c09f17aa22485937260128a)), closes [univention/dev/docs/organization#224](https://git.knut.univention.de/univention/dev/docs/organization/issues/224)

## [0.4.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.4.0...v0.4.1) (2025-11-17)


### Bug Fixes

* fix broken ldap federation after 26.4.1 ([66d7b1a](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/66d7b1aca14784f4ed9b3ac7040d6f94f36b787e)), closes [univention/dev/internal/dev-issues/dev-incidents#186](https://git.knut.univention.de/univention/dev/internal/dev-issues/dev-incidents/issues/186)

## [0.4.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.3.0...v0.4.0) (2025-11-04)


### Features

* **app:** update to Keycloak 26.4.2 ([3da5543](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/3da55432a058242ebf01e4c3f6832ba979b9f239)), closes [univention/dev/projects/keycloak/keycloak-app#245](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/245)


### Bug Fixes

* **deps:** Update base image to version 5.2.3-build-20251030 ([d69ce07](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/d69ce07d24c3582b5510f391d804184b6f3923fc)), closes [univention/dev/internal/team-nubus#1486](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1486)

## [0.3.0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.8...v0.3.0) (2025-10-13)


### Features

* **app:** update to Keycloak 26.3.5 ([fb0cc3f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/fb0cc3f4a63ea7dc53c0375286d6e8df3a9add85)), closes [#58249](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/58249) [univention/dev/ucs#2849](https://git.knut.univention.de/univention/dev/ucs/issues/2849)


### Bug Fixes

* **app:** Change in health endpoint - Issue univention/dev/ucs[#2849](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/2849) ([eeddb2f](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/eeddb2f3cfa7ce70a2ca26f2ff67914d8ddc7ea7))
* **app:** do not replicate DB password to replica and memberserver ([1fb9b3b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/1fb9b3b4c96276c43330dcda5393cbe3701ecd20)), closes [#58249](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/58249) [univention/dev/ucs#2849](https://git.knut.univention.de/univention/dev/ucs/issues/2849) [univention/dev/ucs#2849](https://git.knut.univention.de/univention/dev/ucs/issues/2849)
* **app:** session is no longer a class variable - Issue univention/dev/ucs[#2849](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/2849) ([7301252](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/730125227a7dcc47661c33c6ff07a8b7f29a8cf8))

## [0.2.8](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.7...v0.2.8) (2025-09-29)


### Bug Fixes

* **changelog:** wrong versionn in app/ini ([397266e](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/397266e25e75dc3b10c48b7798681fa3b12e0d94)), closes [univention/components/keycloak-app#243](https://git.knut.univention.de/univention/components/keycloak-app/issues/243)

## [0.2.7](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.6...v0.2.7) (2025-09-18)


### Bug Fixes

* **changelog:** wrong CVE was mentioned ([fe3354b](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/fe3354bdb317d1b96f0ca7c6925e3da431131581)), closes [univention/components/keycloak-app#243](https://git.knut.univention.de/univention/components/keycloak-app/issues/243)

## [0.2.6](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.5...v0.2.6) (2025-08-29)


### Bug Fixes

* **deps:** Update dependency univention/dev/nubus-for-k8s/common-ci to v1.44.2 ([2698acd](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/2698acde889175b244779170266f1edb10a52c2e)), closes [#0](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/issues/0)

## [0.2.5](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.4...v0.2.5) (2025-07-25)


### Bug Fixes

* trigger release ([55a56eb](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/55a56eb4749da45252fea182059953fdd25b4be0)), closes [univention/dev/internal/team-nubus#1355](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/1355)

## [0.2.4](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.3...v0.2.4) (2025-07-14)


### Bug Fixes

* **pipeline:** fix repository moving ([3ad31dc](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/3ad31dc5ac74b940f344b6ee3826c822b87d96be)), closes [univention/dev/ucs#2808](https://git.knut.univention.de/univention/dev/ucs/issues/2808)

## [0.2.3](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.2...v0.2.3) (2025-06-26)


### Bug Fixes

* **pipeline:** fix repository moving ([b43d789](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/b43d78983116953dca43d7e3ff4a35a6ca06ab0e)), closes [univention/dev/ucs#2808](https://git.knut.univention.de/univention/dev/ucs/issues/2808)

## [0.2.2](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.1...v0.2.2) (2025-06-23)


### Bug Fixes

* **docs:** add missing argument for 'samba-tool spn add' ([c2e9970](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/c2e997019c5ee01eb1bbd08852423aa006ed689a))

## [0.2.1](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/compare/v0.2.0...v0.2.1) (2025-06-19)


### Bug Fixes

* container-ldap and udm-rest-api repositories migration ([4cff503](https://git.knut.univention.de/univention/dev/projects/keycloak/keycloak-app/commit/4cff503e7e91a71d17796d3a4d4a60e30a80e3d5)), closes [univention/dev/internal/team-nubus#0](https://git.knut.univention.de/univention/dev/internal/team-nubus/issues/0)

## [0.2.0](https://git.knut.univention.de/univention/components/keycloak-app/compare/v0.1.0...v0.2.0) (2025-05-11)


### Features

* move and upgrade ucs-base-image to 0.17.3-build-2025-05-11 ([385a78c](https://git.knut.univention.de/univention/components/keycloak-app/commit/385a78c418489bb16211a1f66d858193548cf9c7))


### Bug Fixes

* move docker-services ([a13421d](https://git.knut.univention.de/univention/components/keycloak-app/commit/a13421d94454c22736c84c2dbe8fdea757e9136f))
* update common-ci to main ([feb15fb](https://git.knut.univention.de/univention/components/keycloak-app/commit/feb15fbc923e4a432c91a4e3ddfda718b43972ec))

## [0.1.0](https://git.knut.univention.de/univention/components/keycloak-app/compare/v0.0.1...v0.1.0) (2025-05-08)


### Features

* **docs:** Add Nubus Operation Manual reference to Keycloak App Manual ([06b701f](https://git.knut.univention.de/univention/components/keycloak-app/commit/06b701fc83ba11f234320c232f221597962123a2)), closes [univention/dev/docs/nubus-docs#91](https://git.knut.univention.de/univention/dev/docs/nubus-docs/issues/91)
* **docs:** Adjust content to Nubus narrative ([9f722ad](https://git.knut.univention.de/univention/components/keycloak-app/commit/9f722ad9aed4d413973372ab8ce378615f711478)), closes [univention/dev/docs/nubus-docs#91](https://git.knut.univention.de/univention/dev/docs/nubus-docs/issues/91)
* **docs:** Adjust heading levels to structure position ([89b60d0](https://git.knut.univention.de/univention/components/keycloak-app/commit/89b60d00fa834992175b5673d94a217164a2609c)), closes [univention/dev/docs/nubus-docs#91](https://git.knut.univention.de/univention/dev/docs/nubus-docs/issues/91)
* **docs:** Move ad hoc provisioning into own section ([c074c76](https://git.knut.univention.de/univention/components/keycloak-app/commit/c074c76ff0992cd67a606c5a30b19c65f9cc117f)), closes [univention/dev/docs/nubus-docs#91](https://git.knut.univention.de/univention/dev/docs/nubus-docs/issues/91)
* **docs:** Relocate AD FS configuration after custom authentication flow ([d1e4c26](https://git.knut.univention.de/univention/components/keycloak-app/commit/d1e4c26ea139cd11415ffc78e0a58c6b8284a5d0)), closes [univention/dev/docs/nubus-docs#91](https://git.knut.univention.de/univention/dev/docs/nubus-docs/issues/91)
* **docs:** Review feedback ([7ab711b](https://git.knut.univention.de/univention/components/keycloak-app/commit/7ab711b4b2059502fae5243c69069382239d4905)), closes [univention/dev/docs/nubus-docs#91](https://git.knut.univention.de/univention/dev/docs/nubus-docs/issues/91)


### Bug Fixes

* **docs:** Update UCS Manual reference to UCS 5.2 ([a61a627](https://git.knut.univention.de/univention/components/keycloak-app/commit/a61a627f7562318add4990019f42267639d49dbb)), closes [univention/dev/docs/nubus-docs#91](https://git.knut.univention.de/univention/dev/docs/nubus-docs/issues/91)

## 0.0.1 (2025-04-30)


### Features

* Add default group DN configuration ([2b769ce](https://git.knut.univention.de/univention/components/keycloak-app/commit/2b769ce146d00e1fed55cad6f972534d1b3714bd))
* add setup_adhoc_provisioning.py script ([00fbc42](https://git.knut.univention.de/univention/components/keycloak-app/commit/00fbc42f36e63f8e2cbd78567944c538ec866fe3))
* at least use a unicode symbol for language dropdown ([1f0e67d](https://git.knut.univention.de/univention/components/keycloak-app/commit/1f0e67db086edcb7b6685229b590496a555c5682)), closes [#190](https://git.knut.univention.de/univention/components/keycloak-app/issues/190)
* change color of language to primary color of buttons in UCS theme ([7bb87b9](https://git.knut.univention.de/univention/components/keycloak-app/commit/7bb87b92212a8ce31c116e690d91f66165e7d8aa)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* configure UMC for SAML login with keycloak on app install ([fe6fcc4](https://git.knut.univention.de/univention/components/keycloak-app/commit/fe6fcc42ad1fe7e2f6a3b9eeddbc0a25ccea163e)), closes [#211](https://git.knut.univention.de/univention/components/keycloak-app/issues/211)
* install schema extension univentionRemoteIdentifier ([f316f59](https://git.knut.univention.de/univention/components/keycloak-app/commit/f316f5944558833b46757ff005f211d1cae5014e))
* **keycloak-app:** Increase navigation in section 8.2.1 ([d51e462](https://git.knut.univention.de/univention/components/keycloak-app/commit/d51e462c70827018b086e9bb4668fd4d23952920))
* ldap schema extension install ([e39a1e1](https://git.knut.univention.de/univention/components/keycloak-app/commit/e39a1e13f086f9bc5da4bec3129b72f44b5fdcc4))
* **login:** adjust error message for wrong credentials during login ([e798e8c](https://git.knut.univention.de/univention/components/keycloak-app/commit/e798e8c96e57f00e7504e8ac2b30bb5ca47d8f74)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* re enable ad-hoc federation ([c7ee21d](https://git.knut.univention.de/univention/components/keycloak-app/commit/c7ee21d9f3a75dacb974e68d73067696d1f9302f))
* Remove OpenAPI generated univention-directory-manager client ([bb776c5](https://git.knut.univention.de/univention/components/keycloak-app/commit/bb776c53f0f99f41bfbb550f86f3d106d1b07728))
* **udm:** Reimplement Java UDM create, search and delete ([8225a50](https://git.knut.univention.de/univention/components/keycloak-app/commit/8225a50d190669d69eef8c189cdacc214e91cfc7))
* **univention-authenticator:** Allow configuring source and remote identifier from the Keycloak Web UI ([6a4a106](https://git.knut.univention.de/univention/components/keycloak-app/commit/6a4a106c2537bbfeeaa09d7778bec49f1487c124))
* **univention-authenticator:** Migrate OpenAPI generated UDM to new codebase ([bdabaf8](https://git.knut.univention.de/univention/components/keycloak-app/commit/bdabaf8b6ea2f21f75aba3d26020b94c837ee954))
* use official UCS theme color for language selection ([ee820ba](https://git.knut.univention.de/univention/components/keycloak-app/commit/ee820ba6744c68abb59084f18ba00dc11607f3c9)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)


### Bug Fixes

* **ad-hoc:** Adapt some variable names and properties ([10fb706](https://git.knut.univention.de/univention/components/keycloak-app/commit/10fb7060a1ca0a70c1b5bf47909045e0493b14aa))
* **ad-hoc:** Add  license comments ([a6b80ad](https://git.knut.univention.de/univention/components/keycloak-app/commit/a6b80ad8348e9b52956b565d55e8093f3874c657))
* **ad-hoc:** Add semicolon to Config.java ([e7ac364](https://git.knut.univention.de/univention/components/keycloak-app/commit/e7ac364d1861a34fc7aca7f91598a4c0b31aa396))
* **ad-hoc:** Add support for the following behavior ([b3927f6](https://git.knut.univention.de/univention/components/keycloak-app/commit/b3927f68c2169d4e123c115f2feba2da697f3b68))
* **ad-hoc:** Code Review changes ([1382f25](https://git.knut.univention.de/univention/components/keycloak-app/commit/1382f25083e3e69ac6d0a5d8c1fbccad5effac2b))
* **ad-hoc:** Create a new UniventionAuthenticator inside the UniventionAuthenticatorFactory for each initial login event to supply the keycloakSession to the UniventionAuthenticator ([f3662b7](https://git.knut.univention.de/univention/components/keycloak-app/commit/f3662b7fc36190082ead2480717f46e50854aa33))
* **ad-hoc:** delete the Keycloak user if a problem occurs while ensuring the udm user. Make the Authenticator.authenticate better testable ([3bfab97](https://git.knut.univention.de/univention/components/keycloak-app/commit/3bfab9753fb144c836960e9bc7ca4cb4f9916f1f))
* **ad-hoc:** Finalize log messages ([e8b957d](https://git.knut.univention.de/univention/components/keycloak-app/commit/e8b957da9203f21e320b7d27ab6709f142f662a8))
* **ad-hoc:** Handle and test case where objectGUID is not base64 encoded ([0bfdb34](https://git.knut.univention.de/univention/components/keycloak-app/commit/0bfdb340fe3019e1400918aa7e6a3b52761035c0))
* **ad-hoc:** integrate univention-authenticator and udm client into meta pom.xml with all keycloak extensions ([e47f6d2](https://git.knut.univention.de/univention/components/keycloak-app/commit/e47f6d219b57b8f28b25309e0e74ecaed7fb5d94))
* **ad-hoc:** Refactor IdentityMappingConfig and improve test coverage ([2cdf254](https://git.knut.univention.de/univention/components/keycloak-app/commit/2cdf2546010ca9656e24b3f29f90efd8d1ede8e2))
* **ad-hoc:** Refactor UniventionAuthenticator to use IdentityMappingConfigFactory ([056674c](https://git.knut.univention.de/univention/components/keycloak-app/commit/056674cd585b01360f1703a92bdbeba39258a6e7))
* **ad-hoc:** Remove auth failure for case of missing SourceUserPrimaryID_UDMKey temporarily. e2e tests failing. ([b819864](https://git.knut.univention.de/univention/components/keycloak-app/commit/b819864ad37f4376fd7217367870e087a6a3f6b6))
* **ad-hoc:** Remove auth failure for case of missing SourceUserPrimaryID_UDMKey temporarily. e2e tests failing. ([bf40726](https://git.knut.univention.de/univention/components/keycloak-app/commit/bf407267d4b3fccb34e65634e8da475797f593e4))
* **ad-hoc:** Remove unused import ([0432e49](https://git.knut.univention.de/univention/components/keycloak-app/commit/0432e4964c551340619fefa0ed5be2c66ab3a530))
* **ad-hoc:** Remove user in case of auth failure if config load fails ([20b36b6](https://git.knut.univention.de/univention/components/keycloak-app/commit/20b36b6644dfe2522b438ff9effbd874e17c908c))
* **ad-hoc:** Remove user in case of auth failures ([4215e33](https://git.knut.univention.de/univention/components/keycloak-app/commit/4215e3385a74f7fa8c10187b40f608283f9ab398))
* **ad-hoc:** Remove user in case of auth failures ([cdf6e81](https://git.knut.univention.de/univention/components/keycloak-app/commit/cdf6e81d12fb9f038d8a977ce9f8c12fa86b75b7))
* **ad-hoc:** Rename config class, remove group from required properties ([01966da](https://git.knut.univention.de/univention/components/keycloak-app/commit/01966dab4b5534dbda2d42ef8d29e4f3e6dbe7b4))
* **ad-hoc:** Restructure log message and assign the correct levels ([4f14b81](https://git.knut.univention.de/univention/components/keycloak-app/commit/4f14b8184e6ecae2b272e0a4b6e7943d8d1e1aaf))
* **ad-hoc:** Wip more robust error handling ([df2ab64](https://git.knut.univention.de/univention/components/keycloak-app/commit/df2ab6408e5c4cdd3b82e6c97f036b2abada072f))
* add minimal height of login dialog ([9e54ae6](https://git.knut.univention.de/univention/components/keycloak-app/commit/9e54ae6b795e2539d75fe6fb48f0dc018ca177ae)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* add password visibility script from Keycloak 23.0 ([3d85d42](https://git.knut.univention.de/univention/components/keycloak-app/commit/3d85d4240e6e33a1ab109ad9fbd4063c57a5fe15)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* Add source and remote identifier to setup_adhoc_provisioning script ([7047698](https://git.knut.univention.de/univention/components/keycloak-app/commit/7047698f2949935cae83e17e391227e1825c4186))
* add toggle password visibility button ([7d91ba2](https://git.knut.univention.de/univention/components/keycloak-app/commit/7d91ba22441291765778cc8a53cff1c0b97fa7a8)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* **app:** remove unused UCRv for adhoc provisioning ([0eefb75](https://git.knut.univention.de/univention/components/keycloak-app/commit/0eefb75160bb1d87ea5f9abcbc3334ccce478551))
* **ci:** Increased Keycloak version in .gitlab-ci.yaml ([8a71ff7](https://git.knut.univention.de/univention/components/keycloak-app/commit/8a71ff71ed6248d8e8721434a8248e4e21ec0fb4))
* **docs-dev:** document release steps ([beb945f](https://git.knut.univention.de/univention/components/keycloak-app/commit/beb945f8642f7c693d1a9fa581f548d03a226254))
* **docs:** Add missing necessary parameters for backup and restore ([e8c9a2a](https://git.knut.univention.de/univention/components/keycloak-app/commit/e8c9a2a5d12d9759c670407956471401001ebde1))
* **docs:** document postgres requirement and keycloak/auto-migration ([8e2b22b](https://git.knut.univention.de/univention/components/keycloak-app/commit/8e2b22b8368c9c58fa39f502f1798a437bfc64da)), closes [univention/dev-issues/dev-incidents#55](https://git.knut.univention.de/univention/dev-issues/dev-incidents/issues/55) [univention/dev-issues/dev-incidents#52](https://git.knut.univention.de/univention/dev-issues/dev-incidents/issues/52)
* **docs:** Fix Keycloak docs url ([22b3486](https://git.knut.univention.de/univention/components/keycloak-app/commit/22b348680307496c6ceea2c28610f4161fda9430))
* fix font size and color of links in general keycloak errors ([2a08905](https://git.knut.univention.de/univention/components/keycloak-app/commit/2a08905b69c0fd200623356b9b0830b65cb10d54)), closes [#192](https://git.knut.univention.de/univention/components/keycloak-app/issues/192)
* fix outline of language selection buttons ([7afcf77](https://git.knut.univention.de/univention/components/keycloak-app/commit/7afcf7752b90d1298200dabc46fa11f6dd28e65e)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* fix styling of error messages ([a389d18](https://git.knut.univention.de/univention/components/keycloak-app/commit/a389d18bcc54a412108de1560ac2c6c96326d71d)), closes [#192](https://git.knut.univention.de/univention/components/keycloak-app/issues/192)
* **inst:** pass proper credentials to 'add_host_record_in_ad' ([cf4129f](https://git.knut.univention.de/univention/components/keycloak-app/commit/cf4129fb6cbf2f51bec9959a77d5994a400bfb0c)), closes [univention/components/keycloak-app#218](https://git.knut.univention.de/univention/components/keycloak-app/issues/218)
* **keycloak-app:** Use Univention Portal ([8df77d0](https://git.knut.univention.de/univention/components/keycloak-app/commit/8df77d0838235ac3b25d0ba3142d693dbba9950c))
* **login-oauth-grant:** Fix broken layout ([b417346](https://git.knut.univention.de/univention/components/keycloak-app/commit/b417346fd5eb93875866750756698063e6fe8229)), closes [univention/ucs#2381](https://git.knut.univention.de/univention/ucs/issues/2381)
* **login:** adjust CSS variables ([47880dd](https://git.knut.univention.de/univention/components/keycloak-app/commit/47880dddc95be3c2f00f97ecb253a8b4e8229262)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* **login:** align keycloak login CSS with UMC ([7669a19](https://git.knut.univention.de/univention/components/keycloak-app/commit/7669a19abf199df395aca95f5abe6d0dad726bf7)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* **login:** change position of language selection button in login dialog ([91137a2](https://git.knut.univention.de/univention/components/keycloak-app/commit/91137a266c9c6d4c4ff430ef3a1fce9f2d6484ea)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* Place license headers ([afbda58](https://git.knut.univention.de/univention/components/keycloak-app/commit/afbda58689ef5ec45122efe9fbb9f741d029b03f))
* QA changes ([fba633f](https://git.knut.univention.de/univention/components/keycloak-app/commit/fba633f9c5b1373a91ac3316a487c73985d5fcc6))
* rebase social part of login.ftl to Keycloak 26.0 ([8b40af2](https://git.knut.univention.de/univention/components/keycloak-app/commit/8b40af258f8c688c30cecbe05cd9e00cecd78cb9)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)
* Remove duplicate jar from container to avoid split imports warning log ([2422ede](https://git.knut.univention.de/univention/components/keycloak-app/commit/2422ede893a006f03130f8016a0575b87b7f1d0e))
* **udm:** Add factory class to allow for better testability ([785580a](https://git.knut.univention.de/univention/components/keycloak-app/commit/785580a823f751181a99ef0d25fea9fd4b355bab))
* use harbor image ([fcf27a2](https://git.knut.univention.de/univention/components/keycloak-app/commit/fcf27a2f3c6d080e8c85fe64c92a4e0d241060da))
* use original tab index of keycloak ([a4981bc](https://git.knut.univention.de/univention/components/keycloak-app/commit/a4981bc0e9ad703ea2c0953fa3b4bb587c54d4d8)), closes [#239](https://git.knut.univention.de/univention/components/keycloak-app/issues/239)


### Reverts

* Revert "fixup! Issue #140: automatic redirect to https" ([036cb77](https://git.knut.univention.de/univention/components/keycloak-app/commit/036cb775d3e1f48a83d73e5be50e2bec9703c1cc)), closes [#140](https://git.knut.univention.de/univention/components/keycloak-app/issues/140)
