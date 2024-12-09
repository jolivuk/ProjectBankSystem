package bank.app.service;

public interface AuthenticationService {

    boolean authenticateUser(String username, String rawPassword);
    String getEncodedPasswordFromDatabase(String username);
}
