package bank.app.service;

import bank.app.model.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(User user);
    Optional<User> getUserById(Long id);
    List<User> findAll();
    void saveNewUser(User newUser);
    void deleteUserById(Long id);
}
