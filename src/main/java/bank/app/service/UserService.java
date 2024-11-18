package bank.app.service;

import bank.app.dto.*;
import bank.app.model.entity.User;
import bank.app.model.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponseDto getUserById(Long id);
    List<UserResponseDto> findAll();
    UserResponseDto createUser(UserRequestDto newUserDto);
    User getUserByStatus(Role role);
    void deleteUserById(Long id);
    UserResponseDto addPrivateInfo(Long id, PrivateInfoRequestDto privateInfoRequestDto);
    UserResponseDto updateUser(Long id, UserRequestDto userDto);
    UserResponseDto updatePrivateInfo(Long id, PrivateInfoDto privateInfoDto);
    UserResponseDto updateAddress(Long id, AddressRequestDto AddressRequestDto);
    }

