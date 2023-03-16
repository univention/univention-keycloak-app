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

import static de.univention.keycloak.UniventionUserAccountControlStorageMapper.AccountAttributesHelper;
import static de.univention.keycloak.UniventionUserAccountControlStorageMapper.SAMBA_KICKOFF_TIME;
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
    public void sambaKickOffSetToPastTime() {
        final Long expireAt = Instant.now().plus(-1, ChronoUnit.SECONDS).getEpochSecond()/86400;

        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SAMBA_KICKOFF_TIME, Collections.singleton(expireAt.toString()));
        final UniventionUserAccountControlStorageMapper.AccountAttributesHelper helper = new UniventionUserAccountControlStorageMapper.AccountAttributesHelper(attributes);

        assertTrue(helper.isAccountLocked());
    }

    @Test
    public void sambaKickOffSetToCurrentTime() throws IllegalAccessException {
        final Long expireAt = Instant.now().toEpochMilli();

        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SAMBA_KICKOFF_TIME, Collections.singleton(expireAt.toString()));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);
        nowField.set(helper, Instant.ofEpochMilli(expireAt));

        assertFalse(helper.isAccountExpired());
    }

    @Test
    public void sambaKickOffSetToFutureTime() throws IllegalAccessException {
        final Long expireAt = Instant.now().toEpochMilli();

        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SAMBA_KICKOFF_TIME, Collections.singleton(expireAt.toString()));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);
        nowField.set(helper, Instant.ofEpochMilli(expireAt).plus(-1, ChronoUnit.SECONDS));

        assertFalse(helper.isAccountLocked());
    }
}
