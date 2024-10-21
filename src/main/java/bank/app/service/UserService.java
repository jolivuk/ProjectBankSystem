package bank.app.service;

import bank.app.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    public User getUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .firstName("Jim")
                .lastName("Smith")
                .email("jsmith@example.com")
                .build();
    }
}
