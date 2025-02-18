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

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import org.apache.http.client.utils.URIBuilder;

import de.univention.udm.models.User;
import de.univention.udm.models.UserSearchParams;
import de.univention.udm.models.UserSearchResult;

public class UniventionDirectoryManagerClient {
    private final HttpClient client;
    private final String baseUrl;
    private final String credentials;
    private final ObjectMapper objectMapper;

    public UniventionDirectoryManagerClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        this.credentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Searches for users in the Univention Directory Manager.
     * 
     * @param params UserSearchParams object containing the following optional
     *               parameters:
     *               - filter: LDAP filter which may contain UDM property names
     *               instead of LDAP attribute names
     *               e.g. "(|(uid=example1)(username=example2*))" or
     *               "(objectClass=*)"
     *               - position: DN of LDAP node to use as search base
     *               - scope: LDAP search scope ("sub", "base", "one"). Defaults to
     *               "sub"
     *               - query: Map of property names to search filter values
     *               - hidden: Whether to include hidden/system objects. Defaults to
     *               true
     *               - page: The search page number, starting at 1
     *               - limit: Maximum number of results per page
     * 
     * @return UserSearchResult containing the search results with HAL-formatted
     *         data including:
     *         - _embedded.udm:object: Array of User objects
     *         - _links: HAL links (which we ignore)
     *         Each User object contains:
     *         - dn: LDAP Distinguished Name
     *         - objectType: Object type (e.g. "users/user")
     *         - id: Object ID
     *         - position: DN of the parent object
     *         - properties: Map of user properties (username, firstname, lastname,
     *         etc.)
     *         - options: Object type specific options
     *         - policies: Applied policies
     *         - uri: Full URI to the user object
     *         - uuid: LDAP Entry-UUID
     * 
     * @throws IOException          If there's an error communicating with the
     *                              server or parsing the response
     * @throws InterruptedException If the operation is interrupted
     */
    public UserSearchResult searchUsers(UserSearchParams params) throws IOException, InterruptedException {
        try {
            URIBuilder uri = new URIBuilder(String.format("%susers/user/", baseUrl));
            uri = params.applyToUri(uri);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri.build())
                    .header("Authorization", "Basic " + credentials)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new IOException("Failed to search users: " + response.statusCode() + " " + response.body());
            }

            return objectMapper.readValue(response.body(), UserSearchResult.class);
        } catch (URISyntaxException e) {
            throw new IOException("Failed to build URI for user search: " + e.getMessage());
        }
    }

    /**
     * Creates a new user in the Univention Directory Manager.
     * 
     * @param user User object containing the following fields:
     *             Required properties in user.properties:
     *             - username: User's login name
     *             - password: User's password (write-only)
     *
     *             Optional properties in user.properties:
     *             - firstname: User's first name
     *             - lastname: User's last name
     *             - displayName: Display name
     *             - description: Description
     *             - e-mail: Array of email addresses
     *             - phone: Array of phone numbers
     *             - groups: Array of group DNs
     *             - primaryGroup: Primary group DN
     *             - shell: Login shell
     *             - disabled: Account disabled flag
     *             - locked: Account locked flag
     *             - passwordexpiry: Password expiration date
     *             - userexpiry: Account expiration date
     *
     *             Other optional fields:
     *             - position: DN where the user should be created
     *             - options: Object type specific options (e.g. pki)
     *             - policies: Applied policies (desktop, password history, UMC)
     * 
     * @return The created user following properties set by the server:
     *         - dn: The assigned Distinguished Name
     *         - uuid: LDAP Entry-UUID
     * 
     * @throws IOException          If there's an error communicating with the
     *                              server,
     *                              the user creation fails, or there's an error
     *                              parsing the response
     * @throws InterruptedException If the operation is interrupted
     */
    public User createUser(User user) throws IOException, InterruptedException {
        String userJson = objectMapper.writeValueAsString(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "users/user/"))
                .header("Authorization", "Basic " + credentials)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(userJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            throw new IOException("Failed to create user: " + response.statusCode() + " " + response.body());
        }

        return objectMapper.readValue(response.body(), User.class);
    }

    /**
     * Deletes a user by their DN.
     * 
     * @param dn        The distinguished name of the user to delete
     * @param cleanup   Whether to perform cleanup (e.g., of temporary objects,
     *                  locks)
     * @param recursive Whether to remove referring objects (e.g., DNS or DHCP
     *                  references)
     * @throws IOException          If there's an error during the deletion
     * @throws InterruptedException If the operation is interrupted
     */
    public void deleteUser(String dn, boolean cleanup, boolean recursive) throws IOException, InterruptedException {
        try {
            URIBuilder uri = new URIBuilder(String.format("%susers/user/%s", baseUrl, dn));
            if (cleanup) {
                uri.addParameter("cleanup", "true");
            }
            if (recursive) {
                uri.addParameter("recursive", "true");
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri.build())
                    .header("Authorization", "Basic " + credentials)
                    .header("Accept", "application/json")
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            switch (response.statusCode()) {
                case 204:
                    return;
                case 404:
                    throw new IOException("User not found: " + dn);
                default:
                    throw new IOException("Failed to delete user: " + response.statusCode() + " " + response.body());
            }
        } catch (URISyntaxException e) {
            throw new IOException("Failed to build URI for user deletion: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes a user by their DN.
     * 
     * @param dn The distinguished name of the user to delete
     * @throws IOException          If there's an error during the deletion
     * @throws InterruptedException If the operation is interrupted
     */
    public void deleteUser(String dn) throws IOException, InterruptedException {
        deleteUser(dn, true, true);
    }

}
