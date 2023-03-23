.. _app-use-cases:

*********
Use cases
*********

.. highlight:: console

This section describes some uses cases for the app :program:`Keycloak` to give a
deeper insight of the app's capability.

.. _use-case-expired-password:

Expired password and change password on next sign-in
====================================================

In some situations, administrators create a user account with a temporary
password that requires the account owner to change their password during their
first sign-in. The procedure can be company policy or just considered a good
practice. Also, if for any other reason like a lost or compromised user
password, the account owner can contact the administrator and request a password
change.

.. seealso::

   :ref:`users-management-table-account`
      For user account expire and set password upon first login, refer to
      :cite:t:`ucs-manual`.

To enable these capabilities with :program:`Keycloak`, the app offers the
following extensions. The extensions *only* provide the capabilities in the
*UCS* realm with the :program:`Keycloak` app installed.

Univention LDAP mapper
   In :ref:`Keycloak Admin Console <keycloak-admin-console>` follow
   :menuselection:`UCS realm --> User Federation --> ldap-provider --> Mappers`

   The LDAP mapper reads necessary attributes from the LDAP directory and
   triggers a password update when needed.

Univention update password
   In :ref:`Keycloak Admin Console <keycloak-admin-console>` follow
   :menuselection:`UCS realm --> Authentication --> Required Actions`

   Univention update password provides dialogs and forms in the Keycloak login
   flow.
