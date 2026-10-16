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

    @Test
    public void krb5KdcFlagsBit7IsNotSet() {
        // bit 7 clear: value has all bits set except bit 7 → 0b11111111_11111111_11111111_01111111 = 4294967167L
        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(KRB5_KDC_FLAGS, Collections.singleton(String.valueOf(~(1 << 7) & 0xFFFFFFFFL)));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);

        assertFalse(helper.isAccountDisabled());
    }

    @Test
    public void krb5KdcFlagsBit7IsSet() {
        // bit 7 set: value = 128 (only bit 7)
        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(KRB5_KDC_FLAGS, Collections.singleton(String.valueOf(1 << 7)));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);

        assertTrue(helper.isAccountDisabled());
    }
}
