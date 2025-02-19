/*
  Copyright 2025 Univention GmbH

  https://www.univention.de/

  All rights reserved.

  The source code of this program is made available
  under the terms of the GNU Affero General Public License version 3
  (GNU AGPL V3) as published by the Free Software Foundation.

  Binary versions of this program provided by Univention to you as
  well as other copyrighted, protected or trademarked materials like
  Logos, graphics, fonts, specific documentations and configurations,
  cryptographic keys etc. are subject to a license agreement between
  you and Univention and not subject to the GNU AGPL V3.

  In the case you use this program under the terms of the GNU AGPL V3,
  the program is provided in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
  GNU Affero General Public License for more details.

  You should have received a copy of the GNU Affero General Public
  License with the Debian GNU/Linux or Univention distribution in file
  /usr/share/common-licenses/AGPL-3; if not, see
  <https://www.gnu.org/licenses/>.
*/

package de.univention.udm;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import net.datafaker.Faker;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import de.univention.udm.models.User;
import de.univention.udm.models.UserSearchParams;
import de.univention.udm.models.UserSearchResult;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UniventionDirectoryManagerClientIT {
    private UniventionDirectoryManagerClient client;
    private String baseUrl;
    private String username;
    private String password;
    private List<String> createdUserDNs;
    private Faker faker = new Faker();

    @BeforeAll
    void setUp() {
        baseUrl = System.getenv("UDM_API_URL");
        username = System.getenv("UDM_USERNAME");
        password = System.getenv("UDM_PASSWORD");

        assertNotNull(baseUrl, "UDM_API_URL environment variable must be set");
        assertNotNull(username, "UDM_USERNAME environment variable must be set");
        assertNotNull(password, "UDM_PASSWORD environment variable must be set");

        client = new UniventionDirectoryManagerClient(baseUrl, username, password);
        createdUserDNs = new ArrayList<>();
    }

    @AfterAll
    void tearDown() {
        for (String dn : createdUserDNs) {
            try {
                client.deleteUser(dn);
            } catch (IOException | InterruptedException e) {
                System.err.println("Failed to delete test user: " + dn);
                e.printStackTrace();
            }
        }
    }

    @Test
    void testCreateAndSearchUserUsingQuery() throws IOException, InterruptedException {
        String testUsername = faker.internet().username();
        User newUser = new User();
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", testUsername);
        properties.put("firstname", "Test");
        properties.put("lastname", "User");
        properties.put("password", "TestPassword123!");
        newUser.setProperties(properties);

        User createdUser = client.createUser(newUser);

        assertNotNull(createdUser.getDn(), "Created user should have a DN");
        createdUserDNs.add(createdUser.getDn()); // Add to cleanup list

        UserSearchParams searchParams = UserSearchParams.builder()
                .query(Map.of("uid", testUsername))
                .build();

        UserSearchResult searchResult = client.searchUsers(searchParams);

        assertNotNull(searchResult);
        assertFalse(searchResult.getUsers().isEmpty(), "Should find the created user");
        assertEquals(1, searchResult.getUsers().size(), "Should find exactly one user");

        User foundUser = searchResult.getUsers().get(0);
        assertEquals(testUsername, foundUser.getProperties().get("username"));
        assertEquals("Test", foundUser.getProperties().get("firstname"));
        assertEquals("User", foundUser.getProperties().get("lastname"));
    }

    @Test
    void testCreateAndSearchUserUsingFilter() throws IOException, InterruptedException {
        String testUsername = faker.internet().username();
        User newUser = new User();
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", testUsername);
        properties.put("firstname", "Test");
        properties.put("lastname", "User");
        properties.put("password", "TestPassword123!");
        newUser.setProperties(properties);

        User createdUser = client.createUser(newUser);
        assertNotNull(createdUser.getDn(), "Created user should have a DN");
        createdUserDNs.add(createdUser.getDn()); // Add to cleanup list

        UserSearchParams searchParams = UserSearchParams.builder()
                .filter(String.format("(uid=%s)", testUsername))
                .scope("sub")
                .build();

        UserSearchResult searchResult = client.searchUsers(searchParams);

        assertNotNull(searchResult, "Search result should not be null");
        assertFalse(searchResult.getUsers().isEmpty(), "Should find the created user");
        assertEquals(1, searchResult.getUsers().size(), "Should find exactly one user");

        User foundUser = searchResult.getUsers().get(0);
        Map<String, Object> foundProperties = foundUser.getProperties();
        assertEquals(testUsername, foundProperties.get("username"), "Username should match");
        assertEquals("Test", foundProperties.get("firstname"), "First name should match");
        assertEquals("User", foundProperties.get("lastname"), "Last name should match");
    }

    @Test
    void testCreateSameUserTwice() throws IOException, InterruptedException {
        String testUsername = faker.internet().username();
        User newUser = new User();
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", testUsername);
        properties.put("firstname", "Test");
        properties.put("lastname", "User");
        properties.put("password", "TestPassword123!");
        newUser.setProperties(properties);

        User createdUser = client.createUser(newUser);
        assertNotNull(createdUser.getDn(), "Created user should have a DN");
        createdUserDNs.add(createdUser.getDn()); // Add to cleanup list
                                                 //
        User sameUser = new User();
        sameUser.setProperties(properties);
        assertThrows(IOException.class, () -> client.createUser(sameUser),
                "Should throw exception when trying to create the same user twice");
    }

    @Test
    void testSearchNonExistentUser() throws IOException, InterruptedException {
        String nonExistentUsername = faker.internet().username();
        UserSearchParams searchParams = UserSearchParams.builder()
                .query(Map.of("username", nonExistentUsername))
                .build();

        UserSearchResult result = client.searchUsers(searchParams);

        assertNotNull(result);
        assertTrue(result.getUsers().isEmpty(), "Should not find any users");
    }

    @Test
    void testCreateUserWithInvalidData() {
        User invalidUser = new User();
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", ""); // Invalid empty username
        invalidUser.setProperties(properties);

        assertThrows(IOException.class, () -> client.createUser(invalidUser),
                "Should throw exception when creating user with invalid data");
    }

    @Test
    void testSearchUsersWithPagination() throws IOException, InterruptedException {
        int numTestUsers = 3;
        List<String> testUsernames = new ArrayList<>();

        for (int i = 0; i < numTestUsers; i++) {
            String testUsername = faker.internet().username();
            testUsernames.add(testUsername);

            User newUser = new User();
            Map<String, Object> properties = new HashMap<>();
            properties.put("username", testUsername);
            properties.put("firstname", "Test" + i);
            properties.put("lastname", "User" + i);
            properties.put("password", "TestPassword123!");
            newUser.setProperties(properties);

            User createdUser = client.createUser(newUser);
            createdUserDNs.add(createdUser.getDn());
        }

        UserSearchParams searchParams = UserSearchParams.builder()
                .filter("(|(uid=" + String.join(")(uid=", testUsernames) + "))")
                .scope("sub")
                .build();

        UserSearchResult result = client.searchUsers(searchParams);

        assertNotNull(result);
        assertEquals(numTestUsers, result.getUsers().size(),
                "Should find all created test users");
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ErrorHandlingTests {

        @Test
        void testInvalidCredentials() {
            UniventionDirectoryManagerClient invalidClient = new UniventionDirectoryManagerClient(baseUrl, "invalid",
                    "invalid");
            UserSearchParams searchParams = UserSearchParams.builder()
                    .filter("(uid=*)").build();

            assertThrows(IOException.class,
                    () -> invalidClient.searchUsers(searchParams),
                    "Should throw exception with invalid credentials");
        }

        @Test
        void testInvalidBaseUrl() {
            UniventionDirectoryManagerClient invalidClient = new UniventionDirectoryManagerClient(
                    "https://invalid.example.com", username, password);
            UserSearchParams searchParams = UserSearchParams.builder()
                    .filter("(uid=*)").build();

            assertThrows(IOException.class,
                    () -> invalidClient.searchUsers(searchParams),
                    "Should throw exception with invalid base URL");
        }

        @Test
        void testMalformedFilter() {
            UserSearchParams searchParams = UserSearchParams.builder()
                    .filter("invalid filter syntax")
                    .build();

            assertThrows(IOException.class,
                    () -> client.searchUsers(searchParams),
                    "Should throw exception with malformed filter");
        }
    }

    @Test
    void testUserAttributes() throws IOException, InterruptedException {
        String testUsername = faker.internet().username();
        User newUser = new User();
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", testUsername);
        properties.put("firstname", "Test");
        properties.put("lastname", "User");
        properties.put("password", "TestPassword123!");
        properties.put("description", "Test user description");
        properties.put("title", "Test Title");
        newUser.setProperties(properties);

        User createdUser = client.createUser(newUser);
        createdUserDNs.add(createdUser.getDn());

        UserSearchParams searchParams = UserSearchParams.builder()
                .filter("(uid=" + testUsername + ")")
                .scope("sub")
                .build();

        UserSearchResult result = client.searchUsers(searchParams);
        User foundUser = result.getUsers().get(0);

        Map<String, Object> retrievedProps = foundUser.getProperties();
        assertEquals(testUsername, retrievedProps.get("username"));
        assertEquals("Test", retrievedProps.get("firstname"));
        assertEquals("User", retrievedProps.get("lastname"));
        assertEquals("Test user description", retrievedProps.get("description"));
        assertEquals("Test Title", retrievedProps.get("title"));
    }
}
