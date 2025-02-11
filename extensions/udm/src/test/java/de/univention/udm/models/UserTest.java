package de.univention.udm.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class UserTest {
    private User user;
    private static final String TEST_DN = "uid=testuser,dc=domain,dc=test";

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testGetSetDn() {
        assertNull(user.getDn(), "DN should be null by default");
        user.setDn(TEST_DN);
        assertEquals(TEST_DN, user.getDn(), "DN should match the set value");
    }

    @Test
    void testGetSetProperties() {
        assertNull(user.getProperties(), "Properties should be null by default");
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", "testuser");
        properties.put("firstname", "Test");
        properties.put("lastname", "User");
        user.setProperties(properties);

        assertNotNull(user.getProperties(), "Properties should not be null after setting");
        assertEquals(3, user.getProperties().size(), "Properties map should contain 3 entries");
        assertEquals("testuser", user.getProperties().get("username"));
        assertEquals("Test", user.getProperties().get("firstname"));
        assertEquals("User", user.getProperties().get("lastname"));
    }

    @Test
    void testPropertiesImmutability() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", "testuser");
        user.setProperties(properties);

        properties.put("newkey", "newvalue");

        assertFalse(user.getProperties().containsKey("newkey"),
                "Changes to original map should not affect the user's properties");
    }

    @Test
    void testNullProperties() {
        user.setProperties(null);

        assertNull(user.getProperties(), "Properties should allow null value");
    }

    @Test
    void testEmptyProperties() {
        Map<String, Object> emptyProperties = new HashMap<>();

        user.setProperties(emptyProperties);

        assertNotNull(user.getProperties(), "Properties should not be null when empty map is set");
        assertTrue(user.getProperties().isEmpty(), "Properties should be empty");
    }

    @Test
    void testComplexProperties() {
        Map<String, Object> complexProperties = new HashMap<>();
        Map<String, String> address = new HashMap<>();
        address.put("street", "123 Test St");
        address.put("city", "Test City");

        complexProperties.put("username", "testuser");
        complexProperties.put("address", address);
        user.setProperties(complexProperties);

        // Then
        assertEquals(2, user.getProperties().size(), "Should have 2 properties");
        assertTrue(user.getProperties().get("address") instanceof Map,
                "Address should be stored as Map");
        @SuppressWarnings("unchecked")
        Map<String, String> retrievedAddress = (Map<String, String>) user.getProperties().get("address");
        assertEquals("123 Test St", retrievedAddress.get("street"));
        assertEquals("Test City", retrievedAddress.get("city"));
    }

    @Test
    void testPropertiesDeepCopy() {
        Map<String, Object> properties = new HashMap<>();
        Map<String, String> nestedMap = new HashMap<>();
        nestedMap.put("key", "value");
        properties.put("nested", nestedMap);
        user.setProperties(properties);

        nestedMap.put("key2", "value2");

        @SuppressWarnings("unchecked")
        Map<String, String> userNestedMap = (Map<String, String>) user.getProperties().get("nested");
        assertFalse(userNestedMap.containsKey("key2"),
                "Changes to original nested map should not affect the user's properties");
    }
}
