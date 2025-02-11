package de.univention.udm.models;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String dn;
    private Map<String, Object> properties;

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public Map<String, Object> getProperties() {
        if (properties == null) {
            return null;
        }
        return deepCopyMap(properties);
    }

    public void setProperties(Map<String, Object> properties) {
        if (properties == null) {
            this.properties = null;
        } else {
            this.properties = deepCopyMap(properties);
        }
    }

    /**
     * Deep copy a map of properties to avoid modifying the original map
     * @param original The original map
     * @return A deep copy of the original map
     */
    private Map<String, Object> deepCopyMap(Map<String, Object> original) {
        Map<String, Object> copy = new HashMap<>();
        for (Map.Entry<String, Object> entry : original.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                copy.put(entry.getKey(), deepCopyMap((Map<String, Object>) value));
            } else {
                copy.put(entry.getKey(), value);
            }
        }
        return copy;
    }
}
