package de.univention.keycloak;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static de.univention.keycloak.UniventionUserAccountControlStorageMapper.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountExpiredTest {

    private Field nowField;
    private static final SimpleDateFormat krb5Format = new SimpleDateFormat("yyyyMMddHHmmss'Z'");

    @BeforeAll
    public void setUp() throws NoSuchFieldException {
        nowField = AccountAttributesHelper.class.getDeclaredField("now");
        nowField.setAccessible(true);
    }

    private static long getEndDaySeconds() {
        final ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        final ZonedDateTime dateHead = ZonedDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(),
                23, 59, 59, 1000000000 - 1, ZoneOffset.UTC);

        return dateHead.toEpochSecond();
    }


    @Test
    public void shadowExpireSetToPastTime() {
        final Long expireAt = Instant.now().plus(-1, ChronoUnit.SECONDS).getEpochSecond()/86400;

        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SHADOW_EXPIRE, Collections.singleton(expireAt.toString()));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);

        assertTrue(helper.isAccountExpired());
    }

    @Test
    public void shadowExpireLastSecondOfToday() {
        final Long expireAt = getEndDaySeconds() / 86400;

        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SHADOW_EXPIRE, Collections.singleton(expireAt.toString()));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);

        assertTrue(helper.isAccountExpired());
    }

    @Test
    public void shadowExpireFirstSecondNextDay() {
        final Long expireAt = 1 + getEndDaySeconds() / 86400;

        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SHADOW_EXPIRE, Collections.singleton(expireAt.toString()));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);

        assertFalse(helper.isAccountExpired());
    }

    @Test
    public void krb5ValidEndSetToPastTime() {
        final Instant expireAt = Instant.now().plus(-1, ChronoUnit.SECONDS);

        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(KRB5_VALID_END, Collections.singleton(krb5Format.format(Date.from(expireAt))));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);

        assertTrue(helper.isAccountExpired());
    }

    @Test
    public void krb5ValidEndSetToCurrentTime() throws IllegalAccessException {
        final Instant expireAt = Instant.now();

        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(KRB5_VALID_END, Collections.singleton(krb5Format.format(Date.from(expireAt))));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);
        nowField.set(helper, expireAt);

        assertTrue(helper.isAccountExpired());
    }

    @Test
    public void krb5ValidEndSetToFutureTime() throws IllegalAccessException {
        final Instant expireAt = Instant.now();

        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(KRB5_VALID_END, Collections.singleton(krb5Format.format(Date.from(expireAt))));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);
        nowField.set(helper, expireAt.plus(-1, ChronoUnit.SECONDS));

        assertFalse(helper.isAccountExpired());
    }

    @Test
    public void sambaKickOffSetToPastTime() {
        final Long expireAt = Instant.now().plus(-1, ChronoUnit.SECONDS).getEpochSecond()/86400;

        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SAMBA_KICKOFF_TIME, Collections.singleton(expireAt.toString()));
        final UniventionUserAccountControlStorageMapper.AccountAttributesHelper helper = new UniventionUserAccountControlStorageMapper.AccountAttributesHelper(attributes);

        assertTrue(helper.isAccountExpired());
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

        assertFalse(helper.isAccountExpired());
    }
}
