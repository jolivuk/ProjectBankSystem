package bank.app.service;

import bank.app.model.entity.User;
import bank.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository users;

    @Override
    public Optional<User> getUserById(Long id) {
        System.out.println("Fetching user with ID: " + id);
        return users.findById(id);
    }

    @Override
    public List<User> findAll() {
        return users.findAll();
    }

    @Override
    public void saveNewUser(User newUser) {
        users.save(newUser);
    }
}
