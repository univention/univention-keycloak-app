package de.univention.keycloak;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;


@JsonDeserialize(builder = ResetPasswordResponse.Builder.class)
class ResetPasswordResponse {
    private final int status;
    private final String message;
    private final String traceback;
    private final String location;

    private ResetPasswordResponse(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.traceback = builder.traceback;
        this.location = builder.location;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getTraceback() {
        return traceback;
    }

    public String getLocation() {
        return location;
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(value={ "result" }, allowGetters=true)
    public static class Builder {
        private int status;
        private String message;
        private String traceback;
        private String location;

        public Builder() {
            status = 500;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder traceback(String traceback) {
            this.traceback = traceback;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public ResetPasswordResponse build() {
            return new ResetPasswordResponse(this);
        }
    }
}