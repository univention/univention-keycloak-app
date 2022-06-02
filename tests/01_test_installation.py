#!/usr/share/ucs-test/runner python3
## desc:
## tags:
##
## roles: []
## bugs: []
## exposure:

from keycloak import KeycloakAdmin, KeycloakOpenID
from univention.config_registry import ConfigRegistry
from keycloak.connection import ConnectionManager
from keycloak.exceptions import KeycloakError
from univention.admin import localization
from univention.udm import UDM
from univention.config_registry import handler_set
from univention.testing import selenium
import univention.testing.utils as utils
from selenium.webdriver.support import expected_conditions
from selenium import webdriver
import selenium.common.exceptions as selenium_exceptions
from selenium.webdriver import FirefoxOptions

import logging
import os
import subprocess

logger = logging.getLogger(__name__)
translator = localization.translation('ucs-test-framework')
_ = translator.translate


class UMCSeleniumTestSSO(selenium.UMCSeleniumTest):
	def do_sso_login(self, username=None, password=None, without_navigation=False, check_successful_login=True):
		# type: (Optional[str], Optional[str], bool, Optional[str], bool) -> None

		if username is None:
			username = self.umcLoginUsername
		if password is None:
			password = self.umcLoginPassword

		# FIXME: selenium.common.exceptions.InvalidCookieDomainException: Message: invalid cookie domain
		# for year in set([2020, datetime.date.today().year, datetime.date.today().year + 1, datetime.date.today().year - 1]):
		# 	self.driver.add_cookie({'name': 'hideSummit%sDialog' % (year,), 'value': 'true'})
		# 	self.driver.add_cookie({'name': 'hideSummit%sNotification' % (year,), 'value': 'true'})
		if not without_navigation:
			self.driver.get(self.base_url + 'univention/saml/?location=/univention/portal/')

		self.wait_until(
			expected_conditions.presence_of_element_located(
				(webdriver.common.by.By.ID, "username")
			)
		)
		self.enter_input('username', username)
		self.enter_input('password', password)
		self.submit_input('password')

		# for testing 'change password on next login'
		# don't check for available modules etc.
		if not check_successful_login:
			return

		self.wait_for_any_text_in_list([
			_('Users'),
			_('Devices'),
			_('Domain'),
			_('System'),
			_('Software'),
			_('Installed Applications'),
			_('no module available'),
			_('Univention Blog')
		])
		try:
			self.wait_for_text(_('no module available'), timeout=1)
			self.click_button(_('Ok'))
			self.wait_until_all_dialogues_closed()
		except selenium_exceptions.TimeoutException:
			pass
		self.show_notifications(False)
		logger.info('Successful login')

	def __enter__(self):
		# type: () -> UMCSeleniumTest
		self.restart_umc()
		self._ucr.__enter__()
		if self.selenium_grid:
			self.driver = webdriver.Remote(
				command_executor=os.environ.get('SELENIUM_HUB', 'http://jenkins.knut.univention.de:4444/wd/hub'),
				desired_capabilities={
					'browserName': self.browser
				})
		else:
			if self.browser == 'chrome':
				chrome_options = webdriver.ChromeOptions()
				chrome_options.add_argument('--no-sandbox')  # chrome complains about being executed as root
				chrome_options.add_argument('ignore-certificate-errors')
				self.driver = webdriver.Chrome(options=chrome_options)
			else:
				opts = FirefoxOptions()
				opts.add_argument("--headless")
				self.driver = webdriver.Firefox(options=opts)

		self.ldap_base = self._ucr.get('ldap/base')
		if self.suppress_notifications:
			handler_set(['umc/web/hooks/suppress_notifications=suppress_notifications'])

		self.account = utils.UCSTestDomainAdminCredentials()
		self.umcLoginUsername = self.account.username
		self.umcLoginPassword = self.account.bindpw

		if not os.path.exists(self.screenshot_path):
			os.makedirs(self.screenshot_path)

		self.set_viewport_size(1200, 800)
		return self


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
		server_fqdn_with_subdomain = "https://keycloak.{}.{}".format(hostname, domain)
	else:
		server_fqdn_with_subdomain = "https://keycloak.{}".format(domain)

	kc_admin_auth_url = "{}".format(server_fqdn_with_subdomain)

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
		server_fqdn_with_subdomain = "https://keycloak.{}.{}".format(hostname, domain)
	else:
		server_fqdn_with_subdomain = "https://keycloak.{}".format(domain)

	kc_admin_auth_url = "{}".format(server_fqdn_with_subdomain)

	# log into default realm in case UCS realm doesn't exist yet #
	kc_client = KeycloakOpenID(server_url=kc_admin_auth_url, client_id="admin-cli", realm_name="ucs", client_secret_key="secret")
	return kc_client


def test_keycloak_admin_login(username="Administrator", password="univention"):
	print("test keycloak admin login")
	kc_admin = __build_keycloak_admin_connection(username, password)

	assert kc_admin.realm_name == "master", kc_admin.realm_name
	assert isinstance(kc_admin.connection, ConnectionManager), type(kc_admin.connection)
	assert kc_admin.client_id == "admin-cli", kc_admin.client_id
	assert kc_admin.client_secret_key is None, kc_admin.client_secret_key
	print("Admin login successfully")


def test_keycloak_admin_login_fail(username="nonExistUser", password="univention"):
	print("test keycloak admin login")
	try:
		kc_admin = __build_keycloak_admin_connection(username, password)
		assert True, "Non existing user should not be able to log in."
	except KeycloakError:
		print("Login as a non Admin/existing user fail.")


def test_keycloak_user_login(username="Administrator", password="univention"):
	print("test keycloak user login")
	oID_client = __build_keycloak_openID_connection()

	token = oID_client.token(username, password)
	userinfo = oID_client.userinfo(token['access_token'])
	assert userinfo["preferred_username"] == username.lower(), "Wrong user login"
	oID_client.logout(token['refresh_token'])
	print("User OpenID log in successfully")


def test_keycloak_user_login_fail(username="nonExistUser", password="univention"):
	print("test keycloak user login fail")
	oID_client = __build_keycloak_openID_connection()
	try:
		token = oID_client.token(username, password)
		assert True, "Login shouldn't be possible"
	except KeycloakError:
		print("Login as a non existing user fail.")


def create_users():
	print("Creating users ...")
	# Create ldap users -> 1 domain admin, 1 normal user
	user_client = UDM.admin().version(2).get("users/user")

	# load UCR
	ucr = ConfigRegistry()
	ucr.load()
	domain = ucr.get("domainname")
	domain_split = domain.split(".")
	dn = "dc={},dc={}".format(domain_split[0], domain_split[1])

	obj = user_client.new()
	obj.props.username = 'userAdminTest'
	obj.props.lastname = 'test test'
	obj.props.password = 'univention'
	obj.props.primaryGroup = 'cn=Domain Admins,cn=groups,{}'.format(dn)
	obj.props.unixhome = '/home/userAdminTest'
	obj.save()

	obj = user_client.new()
	obj.props.username = 'userUserTest'
	obj.props.lastname = 'test test'
	obj.props.password = 'univention'
	obj.props.unixhome = '/home/userUserTest'
	obj.save()


def delete_users():
	print("Deleting users ...")
	# Delete previous created ldap users -> 1 domain admin, 1 normal user
	user_client = UDM.admin().version(2).get("users/user")

	# load UCR
	ucr = ConfigRegistry()
	ucr.load()
	domain = ucr.get("domainname")
	domain_split = domain.split(".")
	dn = "cn=users,dc={},dc={}".format(domain_split[0], domain_split[1])

	obj = user_client.get("uid=userAdminTest,{}".format(dn))
	obj.delete()

	obj = user_client.get("uid=userUserTest,{}".format(dn))
	obj.delete()


def activate_idp_keycloak():
	# load UCR
	ucr = ConfigRegistry()
	ucr.load()
	hostname = ucr.get("hostname")
	domain = ucr.get("domainname")

	subprocess.call(["ucr", "set", "umc/saml/idp-server=https://keycloak.{}.{}/realms/ucs/protocol/saml/descriptor".format(hostname, domain)])
	subprocess.call(["udm", "portals/entry", "modify", "--dn", "cn=login-saml,cn=entry,cn=portals,cn=univention, {}".format(ucr.get("ldap/base")), "--set", "activated=TRUE"])

def test_sso_selenium():
	activate_idp_keycloak()

	with UMCSeleniumTestSSO() as t:
		t.do_sso_login("administrator", "univention")
		t.end_umc_session()
		t.do_sso_login("userUserTest", "univention")
		t.end_umc_session()

		try:
			t.do_sso_login("nonExistUser", "Password")
			assert True, "Login should not be possible"
		except selenium_exceptions.TimeoutException:
			print("Login as a non existing user is not possible")


def main():
	test_keycloak_admin_login()
	test_keycloak_user_login()
	create_users()
	test_keycloak_admin_login("userAdminTest", "univention")
	test_keycloak_user_login("userUserTest", "univention")

	test_keycloak_admin_login_fail()
	test_keycloak_user_login_fail()
	test_sso_selenium()

	delete_users()


if __name__ == '__main__':
	main()
