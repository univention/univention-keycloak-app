package de.univention.keycloak;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ResetPasswordResponseTest {

    ObjectMapper objectMapper = new ObjectMapper();
    final String location = "https://ucs-sso-ng.test.intranet/univention/auth";

    @Test
    public void emptyResponse() throws JsonProcessingException {
        final String respAsString = "{}";
        final ResetPasswordResponse resp = objectMapper.readValue(respAsString, ResetPasswordResponse.class);

        assertEquals(500, resp.getStatus());
    }

    @Test
    public void invalidUser() throws JsonProcessingException {
        final String respAsString = String.format("{\"status\": 401, \"message\": \"The authentication has failed, please login again.\", \"traceback\": null, \"location\": \"%s\"}", location);
        final ResetPasswordResponse resp = objectMapper.readValue(respAsString, ResetPasswordResponse.class);

        assertEquals(401, resp.getStatus());
        assertEquals("The authentication has failed, please login again.", resp.getMessage());
        assertEquals(null, resp.getTraceback());
        assertEquals(location, resp.getLocation());
    }

    @Test
    public void expiredUserInvalidPassword() throws JsonProcessingException {
        final String respAsString = String.format("{\"status\": 401, \"message\": \"The authentication has failed, please login again.\", \"traceback\": null, \"location\": \"%s\"}", location);
        final ResetPasswordResponse resp = objectMapper.readValue(respAsString, ResetPasswordResponse.class);

        assertEquals(401, resp.getStatus());
        assertEquals("The authentication has failed, please login again.", resp.getMessage());
        assertEquals(null, resp.getTraceback());
        assertEquals(location, resp.getLocation());
    }

    @Test
    public void expiredUserValidPassword() throws JsonProcessingException {
        final String respAsString = "{\"status\": 200, \"result\": {\"username\": \"testuser\"}}";
        final ResetPasswordResponse resp = objectMapper.readValue(respAsString, ResetPasswordResponse.class);

        assertEquals(200, resp.getStatus());
    }

    @Test
    public void expiredUserValidPasswordIncorrectNewPassword() throws JsonProcessingException {
        final String respAsString = String.format("{\"status\": 401, \"message\": \"The password has expired and must be renewed.\", \"traceback\": null, \"location\": \"%s\", \"result\": {\"password_expired\": true}}", location);
        final ResetPasswordResponse resp = objectMapper.readValue(respAsString, ResetPasswordResponse.class);

        assertEquals(401, resp.getStatus());
        assertEquals("The password has expired and must be renewed.", resp.getMessage());
        assertEquals(null, resp.getTraceback());
        assertEquals(location, resp.getLocation());
    }

    @Test
    public void disabledUser() throws JsonProcessingException {
        final String respAsString = String.format("{\"status\": 401, \"message\": \"The account is expired and can not be used anymore.\", \"traceback\": null, \"location\": \"%s\", \"result\": {\"account_expired\": true}}", location);
        final ResetPasswordResponse resp = objectMapper.readValue(respAsString, ResetPasswordResponse.class);

        assertEquals(401, resp.getStatus());
        assertEquals("The account is expired and can not be used anymore.", resp.getMessage());
        assertEquals(null, resp.getTraceback());
        assertEquals(location, resp.getLocation());
    }
}
