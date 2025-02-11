package de.univention.udm.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Collections;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSearchResult {
    @JsonProperty("_embedded")
    private Embedded embedded;

    public List<User> getUsers() {
        return embedded != null && embedded.getUdmObjects() != null
                ? embedded.getUdmObjects()
                : Collections.emptyList();
    }

    public static class Embedded {
        @JsonProperty("udm:object")
        private List<User> udmObjects;

        public List<User> getUdmObjects() {
            return udmObjects;
        }

        public void setUdmObjects(List<User> udmObjects) {
            this.udmObjects = udmObjects;
        }
    }
}
