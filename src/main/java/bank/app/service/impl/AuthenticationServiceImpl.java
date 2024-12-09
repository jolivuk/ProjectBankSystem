package bank.app.service.impl;

import bank.app.repository.UserRepository;
import bank.app.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public boolean authenticateUser(String username, String rawPassword) {
        log.info("Attempting to authenticate user: {}", username);

        String storedEncodedPassword = getEncodedPasswordFromDatabase(username);
        boolean isAuthenticated = passwordEncoder.matches(rawPassword, storedEncodedPassword);

        if (!isAuthenticated) {
            log.error("Authentication failed for user: {}", username);
        } else {
            log.info("User successfully authenticated: {}", username);
        }

        return isAuthenticated;

    }

    @Override
    public String getEncodedPasswordFromDatabase(String username) {
        log.info("Retrieving encoded password for user: {}", username);
        String password = userRepository.findPasswordByUsername(username);

        if (password == null) {
            log.error("User not found : {}", username);
        }
        return password;
    }
}