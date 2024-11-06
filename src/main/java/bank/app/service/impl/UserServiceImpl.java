package bank.app.service.impl;

import bank.app.model.entity.User;
import bank.app.model.enums.Status;
import bank.app.repository.UserRepository;
import bank.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository users;

    @Override
    public void createUser(User user) {
        users.save(user);
    }

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

    @Override
    public void deleteUserById(Long id) {
        Optional<User> userOptional = users.findById(id);
        if (!userOptional.isPresent()) {
            //  throw new UserNotFoundExeption
        }
        User user = userOptional.get();
        if (user.getStatus().equals(Status.DELETED)) {
            //throw new UserAlreadyDeletedExeption
        }
        user.setStatus(Status.DELETED);
        // Так же дополнить Изменения и  все статусы с связанными таблицами поменять
        users.save(user);
    }
}
