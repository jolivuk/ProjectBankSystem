package bank.app.service;

import bank.app.dto.AddressDto;
import bank.app.dto.UserBasicDto;
import bank.app.model.entity.Account;
import bank.app.model.entity.User;
import bank.app.dto.PrivateInfoDto;
import java.util.List;

public interface UserService {
    User getUserById(Long id);
    List<User> findAll();
    User createUser(UserBasicDto newUserDto);
    void deleteUserById(Long id);
    User addPrivateInfo(Long id, PrivateInfoDto privateInfoDto);
    User updateUser(Long id, UserBasicDto userDto);
    User updatePrivateInfo(Long id, PrivateInfoDto privateInfoDto);
    User updateAddress(Long id, AddressDto AddressDto);
    }

