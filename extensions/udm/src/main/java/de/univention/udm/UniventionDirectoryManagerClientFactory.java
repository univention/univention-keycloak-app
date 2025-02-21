package de.univention.udm;

public class UniventionDirectoryManagerClientFactory {
    public UniventionDirectoryManagerClient create(String baseUrl, String username, String password) {
        return new UniventionDirectoryManagerClient(baseUrl, username, password);
    }
}
