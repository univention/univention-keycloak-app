package de.univention.keycloak;

import org.junit.jupiter.api.Test;
import org.keycloak.models.KeycloakContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.services.messages.Messages;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static de.univention.keycloak.UniventionUserAccountControlStorageMapper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountStateMessageTest {

    private static Optional<String> reasonFor(Map<String, Set<String>> attributes) {
        return new AccountAttributesHelper(attributes).findDisabledReason();
    }

    private static UniventionFreeMarkerLoginFormsProvider providerWithReason(Object reason) {
        final KeycloakSession session = mock(KeycloakSession.class);
        when(session.getContext()).thenReturn(mock(KeycloakContext.class));
        when(session.getAttribute(ACCOUNT_STATE_REASON)).thenReturn(reason);
        return new UniventionFreeMarkerLoginFormsProvider(session);
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

    @Test
    public void accountDisabledMessageIsReplaced() {
        final UniventionFreeMarkerLoginFormsProvider provider = providerWithReason("accountExpired");
        provider.setErrors(List.of(new FormMessage("username", Messages.ACCOUNT_DISABLED)));

        provider.replaceAccountDisabledMessage();

        assertEquals("accountExpired", provider.getMessages().get(0).getMessage());
        assertEquals("username", provider.getMessages().get(0).getField());
    }

    @Test
    public void otherMessagesAreKept() {
        final UniventionFreeMarkerLoginFormsProvider provider = providerWithReason("accountExpired");
        provider.setErrors(List.of(new FormMessage("username", Messages.INVALID_USER)));

        provider.replaceAccountDisabledMessage();

        assertEquals(Messages.INVALID_USER, provider.getMessages().get(0).getMessage());
    }

    @Test
    public void messageIsKeptWithoutReason() {
        final UniventionFreeMarkerLoginFormsProvider provider = providerWithReason(null);
        provider.setErrors(List.of(new FormMessage(null, Messages.ACCOUNT_DISABLED)));

        provider.replaceAccountDisabledMessage();

        assertEquals(Messages.ACCOUNT_DISABLED, provider.getMessages().get(0).getMessage());
    }
}
