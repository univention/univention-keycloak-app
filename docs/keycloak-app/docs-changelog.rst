.. SPDX-FileCopyrightText: 2022-2024 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _docs-changelog:

******************
Document changelog
******************

This section shows the history of updates to the *Univention Keycloak app manual 26.x* since its publication on
22. April 2025.

.. _docs-changelog-2026:

Year 2026
=========

.. list-table::
   :header-rows: 1
   :widths: 2 3 7

   * - Date
     - Chapter or topic
     - Change

   * - 22. Apr 2026
     - :ref:`app-changelog`
     - Add changelog entry for version ``26.6.1``.

   * - 10. Mar 2026
     - :ref:`app-settings` and use cases
     - Document audit event log level configuration.
       Add configuration variables
       for successful and error event log levels.
       Add comprehensive use case guide
       for audit logging in Keycloak
       with centralized logging integration.

   * - 06. Mar 2026
     - :ref:`ad-hoc-provisioning`
     - Clarify configuration scope in section headings.
       Explicitly identify which system, Keycloak or ADFS,
       is configured in each step.
       Distinguish external IAM system configuration
       from Keycloak service provider setup.
       Use precise verbs, "Prepare" vs. "Create",
       to indicate workflow progression and roles.

   * - 06. Mar 2026
     - :ref:`ad-hoc-provisioning`
     - Clarify Keycloak's role as service provider
       in ad-hoc provisioning workflow.
       Add transitional text between ADFS
       and Keycloak configuration sections.
       Explicitly distinguish external IAM system
       configuration from Keycloak delegation setup.

.. _docs-changelog-2025:

Year 2025
=========

.. list-table::
   :header-rows: 1
   :widths: 2 3 7

   * - Date
     - Chapter or topic
     - Change

   * - 21. Nov 2025
     - Document configuration
     - Add announcement banner
       to notify readers that documentation
       doesn't yet apply to Nubus for Kubernetes.
       Include feedback collection link
       for missing information.

   * - 18. Jun 2025
     - :ref:`ad-hoc-provisioning`
     - Update ad hoc provisioning documentation. Correct federation metadata URL.
       Simplify mapper attribute names.
       Add important note about Active Directory email address requirements.
       Standardize mapper field terminology to match current Keycloak interface.

   * - 08. May 2025
     - :ref:`ad-hoc-provisioning`
     - Improve section title and clarify federation benefits.
       Simplify authentication flow explanation.
       Refine SPI terminology and user provisioning description.

   * - 08. May 2025
     - :ref:`ad-hoc-provisioning`
     - Relocate Active Directory Federation Services configuration section
       to appear before identity provider setup.
       Improve procedural workflow for operators configuring AD FS claims.

   * - 08. May 2025
     - :ref:`ad-hoc-provisioning`
     - Adjust content to Nubus narrative. Add deployment-specific instructions for UCS appliance and Kubernetes.
       Enhance explanations with examples and metadata references.
       Clarify federation and authentication flow concepts.

   * - 8. May 2025
     - :ref:`ad-hoc-provisioning`
     - Adjust heading levels to match document hierarchy as standalone section.
       Improve section title and introductory content. Apply semantic line breaks and markup consistency.

   * - 8. May 2025
     - :ref:`ad-hoc-provisioning`
     - Move ad-hoc provisioning documentation into separate dedicated section
       for improved document organization and navigation.

   * - 29. Apr 2025
     - :ref:`app-settings`
     - Restore documentation for ad-hoc federation configuration properties
       :envvar:`keycloak/federation/remote/identifier`
       and :envvar:`keycloak/federation/source/identifier`.

   * - 29. Apr 2025
     - :ref:`ad-hoc-provisioning`
     - Apply QA suggestions to ad-hoc provisioning documentation.
       Improve clarity, consistency, and formatting of configuration instructions.

   * - 25. Apr 2025
     - :ref:`ad-hoc-provisioning`
     - Restore and refactor ad-hoc provisioning.
