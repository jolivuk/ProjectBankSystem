package bank.app.dao;

import bank.app.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    public List<User> getAllUsers();
    public void saveUser(User user);
    public User getUser(int id);
    public void deleteUserById(int id);

    public String checkConnection();
}
