#!/usr/share/ucs-test/runner python3
## desc: Check the diagnostic tool for missing schemas
## tags:
##
## roles: []
## bugs: []
## exposure:

import keycloak
from keycloak import KeycloakAdmin, KeycloakOpenID
from univention.config_registry import ConfigRegistry
from univention.config_registry.frontend import ucr_update
import univention.testing.utils as utils
import samltest
from keycloak.connection import ConnectionManager
from keycloak.exceptions import KeycloakAuthenticationError


def __get_samlSession():
	account = utils.UCSTestDomainAdminCredentials()
	return samltest.SamlTest(account.username, account.bindpw)


def __test_umc_sp(samlSession, test_function):
	samlSession.login_with_new_session_at_IdP()
	test_function()
	samlSession.logout_at_IdP()
	samlSession.test_logout_at_IdP()
	samlSession.test_logout()


def __build_keycloak_admin_connection(kc_username, kc_password, is_file=False):
	# load UCR
	ucr = ConfigRegistry()
	ucr.load()
	hostname = ucr.get("hostname")
	domain = ucr.get("domainname")

	kc_user = kc_username
	kc_pass = kc_password
	if is_file:
		pwdfile = kc_password
		with open(pwdfile, 'r') as fd:
			kc_pass = fd.read().strip()

	# derived composite vars #
	if hostname and not hostname == "unassigned-hostname":
		server_fdnq_with_subdomain = "https://keycloak.{}.{}".format(hostname, domain)
	else:
		server_fdnq_with_subdomain = "https://keycloak.{}".format(domain)

	kc_admin_auth_url = "{}".format(server_fdnq_with_subdomain)

	# log into default realm in case UCS realm doesn't exist yet #
	kc_admin = KeycloakAdmin(server_url=kc_admin_auth_url, username=kc_user, password=kc_pass, realm_name="master", user_realm_name="master", verify=True)
	return kc_admin


def __build_keycloak_openID_connection():
	# load UCR
	ucr = ConfigRegistry()
	ucr.load()
	hostname = ucr.get("hostname")
	domain = ucr.get("domainname")

	# derived composite vars #
	if hostname and not hostname == "unassigned-hostname":
		server_fdnq_with_subdomain = "https://keycloak.{}.{}".format(hostname, domain)
	else:
		server_fdnq_with_subdomain = "https://keycloak.{}".format(domain)

	kc_admin_auth_url = "{}".format(server_fdnq_with_subdomain)

	# log into default realm in case UCS realm doesn't exist yet #
	kc_client = KeycloakOpenID(server_url=kc_admin_auth_url, client_id="account", realm_name="ucs", client_secret_key="secret")
	return kc_client

def test_saml_login():
	samlSession = __get_samlSession()
	__test_umc_sp(samlSession, samlSession.test_login)


def test_keycloak_admin_login(new_users=False):
	print("test keycloak admin login")
	kc_admin = __build_keycloak_admin_connection("Administrator", "univention")

	assert kc_admin.realm_name == "master", kc_admin.realm_name
	assert isinstance(kc_admin.connection, ConnectionManager), type(kc_admin.connection)
	assert kc_admin.client_id == "admin-cli", kc_admin.client_id
	assert kc_admin.client_secret_key is None, kc_admin.client_secret_key

	if new_users:
		kc_admin = __build_keycloak_admin_connection("userTest", "univention")
		assert kc_admin.realm_name == "master", kc_admin.realm_name
		assert isinstance(kc_admin.connection, ConnectionManager), type(kc_admin.connection)
		assert kc_admin.client_id == "admin-cli", kc_admin.client_id
		assert kc_admin.client_secret_key is None, kc_admin.client_secret_key
	else:
		try:
			kc_admin = __build_keycloak_admin_connection("userTest", "univention")
		except KeycloakAuthenticationError as e:
			assert str(e) == "401: b'{\"error\":\"invalid_grant\",\"error_description\":\"Invalid user credentials\"}'", "Wrong admin login"


def test_keycloak_user_login(new_users=False):
	print("test keycloak user login")



def create_users():
	print("Creating users ...")
	# Create ldap users -> 1 domain admin, 1 normal user


def delete_users():
	print("Deleting users ...")
	# Delete previous created ldap users -> 1 domain admin, 1 normal user


def main():
	test_keycloak_admin_login()
	test_keycloak_user_login()
	create_users()
	test_keycloak_admin_login(new_users=True)
	test_keycloak_user_login(new_users=True)

	test_saml_login()


if __name__ == '__main__':
	main()

