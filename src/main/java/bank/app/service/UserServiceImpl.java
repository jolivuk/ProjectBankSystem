package bank.app.service;

import bank.app.dao.UserDao;
import bank.app.entity.User;
import bank.app.model.UserExample;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public void deleteUserById(int id) {

    }

    @Override
    public String checkConnection() {
        return userDao.checkConnection();
    }
}
