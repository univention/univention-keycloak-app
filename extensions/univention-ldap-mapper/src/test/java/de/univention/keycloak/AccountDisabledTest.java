package de.univention.keycloak;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static de.univention.keycloak.UniventionUserAccountControlStorageMapper.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AccountDisabledTest {

    @Test
    public void shadowExpireSetTo0() {
        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SHADOW_EXPIRE, Collections.singleton("0"));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);

        assertFalse(helper.isAccountDisabled());
    }

    @Test
    public void shadowExpireSetTo1() {
        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SHADOW_EXPIRE, Collections.singleton("1"));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);

        assertTrue(helper.isAccountDisabled());
    }
}
