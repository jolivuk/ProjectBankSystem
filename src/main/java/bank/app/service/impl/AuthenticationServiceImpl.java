package bank.app.service.impl;

import bank.app.repository.UserRepository;
import bank.app.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public boolean authenticateUser(String username, String rawPassword) {
        String storedEncodedPassword = getEncodedPasswordFromDatabase(username);
        return passwordEncoder.matches(rawPassword, storedEncodedPassword);
    }

    @Override
    public String getEncodedPasswordFromDatabase(String username) {
        return userRepository.findPasswordByUsername(username);
    }
}