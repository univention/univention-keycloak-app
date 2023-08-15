package de.univention.keycloak;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.security.InvalidParameterException;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class ResetPasswordRequest {

    private final ResetPasswordRequestParams options;

    public ResetPasswordRequest(ResetPasswordRequestParams options) {
        this.options = options;
    }

    public ResetPasswordRequestParams getOptions() {
        return options;
    }
}

@JsonDeserialize(builder = ResetPasswordRequestParams.Builder.class)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
class ResetPasswordRequestParams {

    private final String username;
    private final String password;
    private final String newPassword;
    @JsonIgnore
    private final String language;

    private ResetPasswordRequestParams(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.newPassword = builder.newPassword;
        this.language = builder.language;

        validate();
    }

    private void validate() {
        if (isEmpty(username) || isEmpty(password) || isEmpty(newPassword) || isEmpty(language)) {
            throw new InvalidParameterException("Request parameters are invalid");
        }
    }

    static private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getLanguage() {
        return language;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private String username;
        private String password;
        private String newPassword;
        private String language;

        public Builder() {
            language = "en";
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder newPassword(String newPassword) {
            this.newPassword = newPassword;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public ResetPasswordRequestParams build() {
            return new ResetPasswordRequestParams(this);
        }
    }
}
