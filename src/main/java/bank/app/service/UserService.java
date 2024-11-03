package bank.app.service;

import bank.app.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);
    List<User> findAll();
    void saveNewUser(User newUser);
}
