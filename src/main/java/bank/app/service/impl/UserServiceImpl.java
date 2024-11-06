package bank.app.service.impl;

import bank.app.dto.PrivateInfoDto;
import bank.app.dto.UserDto;
import bank.app.model.entity.Address;
import bank.app.model.entity.PrivateInfo;
import bank.app.model.entity.User;
import bank.app.model.enums.Status;
import bank.app.repository.AddressRepository;
import bank.app.repository.PrivateInfoRepository;
import bank.app.repository.UserRepository;
import bank.app.service.AddressService;
import bank.app.service.PrivateInfoService;
import bank.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository users;

    @Autowired
    private PrivateInfoService privateInfoService;

    @Autowired
    private AddressService addressService;

    @Override
    public void createUser(User user) {
        users.save(user);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = users.findById(id);
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return optionalUser.get();
    }

    @Override
    public List<User> findAll() {
        return users.findAll();
    }

    @Override
    public User saveNewUser(UserDto newUserDto) {
        User manager = getUserById(newUserDto.manager());

        User user = new User(newUserDto.username(),newUserDto.password(),
                Status.ACTIVE, newUserDto.role(),manager);
        users.save(user);
        return user;
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

        users.save(user);
    }

    @Override
    public User addPrivateInfoToUser(Long id, PrivateInfoDto privateInfoDto) {

        User user = getUserById(id);

        Address savedAddress = addressService.createAddress(privateInfoDto.address());

        // Создаем и сохраняем PrivateInfo
        PrivateInfo savedPrivateInfo = privateInfoService.createPrivateInfo(privateInfoDto, savedAddress);

        // Связываем PrivateInfo с пользователем
        user.setPrivateInfo(savedPrivateInfo);

        // Сохраняем пользователя с обновленным PrivateInfo
        users.save(user);
        return user;
    }
}
