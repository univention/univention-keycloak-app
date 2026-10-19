package de.univention.keycloak;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static de.univention.keycloak.UniventionUserAccountControlStorageMapper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountStateReasonTest {

    private static Optional<String> reasonFor(Map<String, Set<String>> attributes) {
        return new AccountAttributesHelper(attributes).findDisabledReason();
    }

    @Test
    public void activeAccountHasNoReason() {
        assertEquals(Optional.empty(), reasonFor(Map.of()));
    }

    @Test
    public void disabledAccount() {
        assertEquals(Optional.of("accountDisabled"), reasonFor(Map.of(SHADOW_EXPIRE, Set.of("1"))));
    }

    @Test
    public void expiredAccount() {
        assertEquals(Optional.of("accountExpired"), reasonFor(Map.of(SAMBA_KICKOFF_TIME, Set.of("1000"))));
    }

    @Test
    public void lockedAccount() {
        assertEquals(Optional.of("accountLocked"), reasonFor(Map.of(SAMBA_ACCT_FLAGS, Set.of("[UL         ]"))));
    }

    @Test
    public void passwordChangeDoesNotDisableAccount() {
        assertEquals(Optional.empty(), reasonFor(Map.of(SAMBA_PWD_MUST_CHANGE, Set.of("0"))));
    }
}
