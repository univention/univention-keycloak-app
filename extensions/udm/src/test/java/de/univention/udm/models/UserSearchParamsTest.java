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

package de.univention.udm.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class UserSearchParamsTest {

    @Test
    void testBuilderWithFilter() {
        String filter = "(uid=testuser)";

        UserSearchParams params = UserSearchParams.builder()
                .filter(filter)
                .build();

        assertEquals(filter, params.getFilter());
        assertNull(params.getPosition());
        assertNull(params.getScope());
        assertNull(params.getQuery());
    }

    @Test
    void testBuilderWithAllParameters() {
        String filter = "(uid=testuser)";
        String position = "dc=domain,dc=test";
        String scope = "sub";
        Map<String, Object> query = new HashMap<>();
        query.put("username", "testuser");

        UserSearchParams params = UserSearchParams.builder()
                .filter(filter)
                .position(position)
                .scope(scope)
                .query(query)
                .build();

        assertEquals(filter, params.getFilter());
        assertEquals(position, params.getPosition());
        assertEquals(scope, params.getScope());
        assertEquals(query, params.getQuery());
    }

    @Test
    void testBuilderWithQueryOnly() {
        Map<String, Object> query = new HashMap<>();
        query.put("username", "testuser");
        query.put("firstname", "Test");

        UserSearchParams params = UserSearchParams.builder()
                .query(query)
                .build();

        assertNull(params.getFilter());
        assertNull(params.getPosition());
        assertNull(params.getScope());
        assertEquals(query, params.getQuery());
        assertEquals("testuser", params.getQuery().get("username"));
        assertEquals("Test", params.getQuery().get("firstname"));
    }

    @Test
    void testSettersAndGetters() {
        UserSearchParams params = new UserSearchParams();
        String filter = "(uid=testuser)";
        String position = "dc=domain,dc=test";
        String scope = "sub";
        Map<String, Object> query = new HashMap<>();
        query.put("username", "testuser");

        params.setFilter(filter);
        params.setPosition(position);
        params.setScope(scope);
        params.setQuery(query);

        assertEquals(filter, params.getFilter());
        assertEquals(position, params.getPosition());
        assertEquals(scope, params.getScope());
        assertEquals(query, params.getQuery());
    }

    @Test
    void testBuilderWithArrayValues() {
        Map<String, Object> query = new HashMap<>();
        String[] emails = new String[] { "test1@domain.test", "test2@domain.test" };
        query.put("e-mail", emails);

        UserSearchParams params = UserSearchParams.builder()
                .query(query)
                .build();

        assertNotNull(params.getQuery());
        assertTrue(params.getQuery().get("e-mail") instanceof String[]);
        String[] resultEmails = (String[]) params.getQuery().get("e-mail");
        assertEquals(2, resultEmails.length);
        assertEquals("test1@domain.test", resultEmails[0]);
        assertEquals("test2@domain.test", resultEmails[1]);
    }

    @Test
    void testEmptyBuilder() {
        UserSearchParams params = UserSearchParams.builder().build();

        assertNull(params.getFilter());
        assertNull(params.getPosition());
        assertNull(params.getScope());
        assertNull(params.getQuery());
    }

    @Test
    void testQueryWithMultipleTypes() {
        Map<String, Object> query = new HashMap<>();
        query.put("username", "testuser");
        query.put("mailPrimaryAddress", new String[] { "test@domain.test" });

        UserSearchParams params = UserSearchParams.builder()
                .query(query)
                .build();

        assertEquals("testuser", params.getQuery().get("username"));
        assertTrue(params.getQuery().get("mailPrimaryAddress") instanceof String[]);
    }

    @Test
    void testBuilderWithScopeOnly() {
        String scope = "one";

        UserSearchParams params = UserSearchParams.builder()
                .scope(scope)
                .build();

        assertEquals(scope, params.getScope());
        assertNull(params.getFilter());
        assertNull(params.getPosition());
        assertNull(params.getQuery());
    }

    @Test
    void testBuilderChaining() {
        UserSearchParams params = UserSearchParams.builder()
                .filter("(uid=test)")
                .scope("sub")
                .position("dc=domain,dc=test")
                .query(new HashMap<>())
                .build();

        assertNotNull(params.getFilter());
        assertNotNull(params.getScope());
        assertNotNull(params.getPosition());
        assertNotNull(params.getQuery());
    }
}
