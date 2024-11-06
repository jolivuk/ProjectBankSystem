package bank.app.service;

import bank.app.dto.UserDto;
import bank.app.model.entity.User;
import bank.app.dto.PrivateInfoDto;
import java.util.List;

public interface UserService {
    void createUser(User user);
    User getUserById(Long id);
    List<User> findAll();
    User saveNewUser(UserDto newUserDto);
    void deleteUserById(Long id);
    User addPrivateInfoToUser(Long id, PrivateInfoDto privateInfoDto);
}
