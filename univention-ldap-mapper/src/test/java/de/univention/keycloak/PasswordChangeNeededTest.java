package de.univention.keycloak;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static de.univention.keycloak.UniventionUserAccountControlStorageMapper.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PasswordChangeNeededTest {
    private static final SimpleDateFormat krb5Format = new SimpleDateFormat("yyyyMMddHHmmss'Z'");

    private Field nowField;

    @BeforeAll
    public void setUp() throws NoSuchFieldException {
        nowField = UniventionUserAccountControlStorageMapper.AccountAttributesHelper.class.getDeclaredField("now");
        nowField.setAccessible(true);
    }

    @Test
    public void shadowLastChangeExpired() {
        final Instant expireAt = Instant.now().plus(-1, ChronoUnit.DAYS);
        final Long shadowLastChange = expireAt.getEpochSecond() / 86400 - 1;
        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SHADOW_MAX, Collections.singleton("1"));
        attributes.put(SHADOW_LAST_CHANGE, Collections.singleton(shadowLastChange.toString()));

        final UniventionUserAccountControlStorageMapper.AccountAttributesHelper helper = new UniventionUserAccountControlStorageMapper.AccountAttributesHelper(attributes);
        assertTrue(helper.isPasswordChangeNeeded());
    }

    @Test
    public void shadowLastChangeNotExpired() {
        final Instant expireAt = Instant.now().plus(+1, ChronoUnit.DAYS);
        final Long shadowLastChange = expireAt.getEpochSecond() / 86400;
        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SHADOW_MAX, Collections.singleton("1"));
        attributes.put(SHADOW_LAST_CHANGE, Collections.singleton(shadowLastChange.toString()));

        final UniventionUserAccountControlStorageMapper.AccountAttributesHelper helper = new UniventionUserAccountControlStorageMapper.AccountAttributesHelper(attributes);
        assertFalse(helper.isPasswordChangeNeeded());
    }

    @Test
    public void krb5PasswordEndExpired() {
        final Instant expireAt = Instant.now().plus(-1, ChronoUnit.SECONDS);
        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(KRB5_PASSWORD_END, Collections.singleton(krb5Format.format(Date.from(expireAt))));

        final UniventionUserAccountControlStorageMapper.AccountAttributesHelper helper = new UniventionUserAccountControlStorageMapper.AccountAttributesHelper(attributes);
        assertTrue(helper.isPasswordChangeNeeded());
    }

    @Test
    public void krb5PasswordEndNotExpired() throws IllegalAccessException {
        final Instant expireAt = Instant.now();

        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(KRB5_PASSWORD_END, Collections.singleton(krb5Format.format(Date.from(expireAt))));
        final AccountAttributesHelper helper = new AccountAttributesHelper(attributes);
        nowField.set(helper, expireAt);

        assertFalse(helper.isAccountExpired());
    }

    @Test
    public void sambaPasswordMustChangeSetTo0() {
        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SAMBA_PWD_MUST_CHANGE, Collections.singleton("0"));
        final UniventionUserAccountControlStorageMapper.AccountAttributesHelper helper = new UniventionUserAccountControlStorageMapper.AccountAttributesHelper(attributes);

        assertTrue(helper.isPasswordChangeNeeded());
    }

    @Test
    public void sambaPasswordMustChangeSetTo1() {
        Map<String, Set<String>> attributes = new HashMap<>();
        attributes.put(SAMBA_PWD_MUST_CHANGE, Collections.singleton("1"));
        final UniventionUserAccountControlStorageMapper.AccountAttributesHelper helper = new UniventionUserAccountControlStorageMapper.AccountAttributesHelper(attributes);

        assertFalse(helper.isPasswordChangeNeeded());
    }

}
