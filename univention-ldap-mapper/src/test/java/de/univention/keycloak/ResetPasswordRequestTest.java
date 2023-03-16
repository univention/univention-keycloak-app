package de.univention.keycloak;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

public class ResetPasswordRequestTest {

    private final String message = "Request parameters are invalid";

    @Test
    public void requestParamsAllEmpty() {
        final InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> {
            new ResetPasswordRequestParams.Builder().build();
        });

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void requestParamsUsernameEmpty() {
        final InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> {
            new ResetPasswordRequestParams.Builder()
                    .password("pwd")
                    .newPassword("newPwd")
                    .build();
        });

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void requestParamsPasswordEmpty() {
        final InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> {
            new ResetPasswordRequestParams.Builder()
                    .username("user")
                    .newPassword("newPwd")
                    .build();
        });

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void requestParamsNewPasswordEmpty() {
        final InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> {
            new ResetPasswordRequestParams.Builder()
                    .username("user")
                    .password("pwd")
                    .build();
        });

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void requestParamsLanguageEmpty() {
        final InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> {
            new ResetPasswordRequestParams.Builder()
                    .username("user")
                    .password("pwd")
                    .newPassword("newPwd")
                    .language("")
                    .build();
        });

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void requestParamsValid() {
        final String username = "user";
        final String pwd = "pwd";
        final String newPwd = "newPwd";
        final String lang = "fr";
        final ResetPasswordRequestParams reqParams = new ResetPasswordRequestParams.Builder()
                .username(username)
                .password(pwd)
                .newPassword(newPwd)
                .language(lang)
                .build();

        assertEquals(reqParams.getUsername(), username);
        assertEquals(reqParams.getPassword(), pwd);
        assertEquals(reqParams.getNewPassword(), newPwd);
        assertEquals(reqParams.getLanguage(), lang);
    }

    @Test
    public void resetPasswordRequestOptions() {
        final String username = "user";
        final String pwd = "pwd";
        final String newPwd = "newPwd";
        final String lang = "fr";
        final ResetPasswordRequestParams reqParams = new ResetPasswordRequestParams.Builder()
                .username(username)
                .password(pwd)
                .newPassword(newPwd)
                .language(lang)
                .build();
        final ResetPasswordRequest req = new ResetPasswordRequest(reqParams);
        final ResetPasswordRequestParams options = req.getOptions();

        assertEquals(options.getUsername(), username);
        assertEquals(options.getPassword(), pwd);
        assertEquals(options.getNewPassword(), newPwd);
        assertEquals(options.getLanguage(), lang);
    }

    @Test
    public void resetPasswordRequestPayload() throws JsonProcessingException, JSONException {
        final String username = "user";
        final String pwd = "pwd";
        final String newPwd = "newPwd";
        final String lang = "fr";
        final String expectedJson = String.format("{\"options\" : {\"new_password\" : \"%s\", \"password\" : \"%s\", \"username\" : \"%s\" }}", newPwd, pwd, username);

        final ResetPasswordRequestParams reqParams = new ResetPasswordRequestParams.Builder()
                .username(username)
                .password(pwd)
                .newPassword(newPwd)
                .language(lang)
                .build();
        final ResetPasswordRequest req = new ResetPasswordRequest(reqParams);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        final String actualJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(req);

        JSONAssert.assertEquals(expectedJson, actualJson, true);

        final JSONObject jsonObj = new JSONObject(actualJson);
        assertTrue(jsonObj.has("options"));
        assertFalse(jsonObj.has("language"));
    }
}
