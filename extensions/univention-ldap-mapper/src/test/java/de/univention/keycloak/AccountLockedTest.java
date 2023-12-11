package de.univention.keycloak;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static de.univention.keycloak.UniventionUserAccountControlStorageMapper.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountLockedTest {
    private Field nowField;

    @BeforeAll
    public void setUp() throws NoSuchFieldException {
        nowField = UniventionUserAccountControlStorageMapper.AccountAttributesHelper.class.getDeclaredField("now");
        nowField.setAccessible(true);
    }

    @Test
    public void sambaAcctFlagNotContainL() {
        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SAMBA_ACCT_FLAGS, Collections.singleton("[U          ]"));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);

        assertFalse(helper.isAccountLocked());
    }

    @Test
    public void sambaAcctFlagContainL() {
        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SAMBA_ACCT_FLAGS, Collections.singleton("[L          ]"));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);

        assertTrue(helper.isAccountLocked());
    }

    @Test
    public void krb5KdcFlagsIsNot254() {
        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(KRB5_KDC_FLAGS, Collections.singleton("255"));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);

        assertFalse(helper.isAccountLocked());
    }

    @Test
    public void krb5KdcFlagsIs254() {
        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(KRB5_KDC_FLAGS, Collections.singleton("254"));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);

        assertTrue(helper.isAccountLocked());
    }
}
