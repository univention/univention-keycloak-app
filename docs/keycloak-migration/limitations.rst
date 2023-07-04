.. SPDX-FileCopyrightText: 2023 Univention GmbH
..
.. SPDX-License-Identifier: AGPL-3.0-only

.. _limitations:

*******************************
Limitations of the Keycloak app
*******************************


The :program:`Keycloak` app has not the same feature set as
the :program:`SimpleSAMLphp` integration at the time of writing.
All of the following points are currently not supported by the :program:`Keycloak`
app, but will be implemented in the next versions.

For more information about limitations of the :program:`Keycloak` app, refer to
:ref:`uv-keycloak-app:app-limitations` in :cite:t:`ucs-keycloak-doc`.

.. _limit-app-authorization:

App authorization
=================

In :program:`SimpleSAMLphp` it's possible to restrict the access to certain
clients through a checkbox on the user object in the |UMC|. To restrict the
access of users to certain clients isn't possible with :program:`Keycloak` at
the moment.

.. _limit-custom login page:

Customization of the login page
===============================

With :program:`SimpleSAMLphp` it's possible to adjust the login page through
various UCR variables. You can use these UCR variables to add links to the
sign-in dialog, for example to redirect the user to the
:ref:`user-management-password-changes-by-users` in case they forgot their
password.

With :program:`Keycloak` you can adjust the theming of the sign-in dialog,
but adding custom links isn't supported for the moment.
