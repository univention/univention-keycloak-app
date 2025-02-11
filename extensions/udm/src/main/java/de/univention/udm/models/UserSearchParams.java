package de.univention.udm.models;

import java.util.Map;
import org.apache.http.client.utils.URIBuilder;

public class UserSearchParams {
    private String filter;
    private String position;
    private String scope;
    private Map<String, Object> query;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Map<String, Object> getQuery() {
        return query;
    }

    public void setQuery(Map<String, Object> query) {
        this.query = query;
    }

    /**
     * Applies all search parameters to the given URIBuilder
     * @param uri The URIBuilder to add parameters to
     * @return The URIBuilder with parameters added
     */
    public URIBuilder applyToUri(URIBuilder uri) {
        if (filter != null) {
            uri.addParameter("filter", filter);
        }
        if (position != null) {
            uri.addParameter("position", position);
        }
        if (scope != null) {
            uri.addParameter("scope", scope);
        }
        if (query != null && !query.isEmpty()) {
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value != null) {
                    if (value instanceof String[]) {
                        for (String arrayValue : (String[]) value) {
                            uri.addParameter("query[" + key + "]", arrayValue);
                        }
                    } else {
                        uri.addParameter("query[" + key + "]", value.toString());
                    }
                }
            }
        }
        return uri;
    }

    public static UserSearchParams.Builder builder() {
        return new UserSearchParams.Builder();
    }

    public static class Builder {
        private final UserSearchParams params = new UserSearchParams();

        public Builder filter(String filter) {
            params.setFilter(filter);
            return this;
        }

        public Builder position(String position) {
            params.setPosition(position);
            return this;
        }

        public Builder scope(String scope) {
            params.setScope(scope);
            return this;
        }

        public Builder query(Map<String, Object> query) {
            params.setQuery(query);
            return this;
        }

        public UserSearchParams build() {
            return params;
        }
    }
}
