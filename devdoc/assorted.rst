.. _keycloak-assorted:

********
Patching
********

.. highlight:: console

Some components of :program:`Keycloak` have to be patched.

.. _patching-login-template:

Login template
==============

Customizing login's `template.ftl` in UCS theme requires original file
obtained from upstream :program:`Keycloak` image used as base image:

.. code-block::

   $ KEYCLOAK_VERSION=22.0.1
   $ docker cp $(docker create --name kctmp quay.io/keycloak/keycloak:${KEYCLOAK_VERSION}):/opt/keycloak/lib/lib/main/org.keycloak.keycloak-themes-${KEYCLOAK_VERSION}.jar . && docker rm kctmp
   $ unzip -p org.keycloak.keycloak-themes-${KEYCLOAK_VERSION}.jar theme/base/login/template.ftl > template.ftl.orig

Next we need a current, already patched file:

.. code-block::

   $ KEYCLOAK_IMAGE=docker.software-univention.de/keycloak-keycloak:22.0.1-ucs1
   $ docker pull ${KEYCLOAK_IMAGE}
   $ docker cp $(docker create --name kctmp ${KEYCLOAK_IMAGE}):/opt/keycloak/themes/UCS/login/template.ftl . && docker rm kctmp

After you finish altering `template.ftl`, make a patch to `template.ftl.patch`
and commit a change:

.. code-block::

   $ diff -Naur template.ftl.orig template.ftl > files/themes/UCS/login/template.ftl.patch
   $ git commit -am "Describe your changes"
