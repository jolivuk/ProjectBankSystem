package bank.app.service.impl;

import bank.app.dto.PrivateInfoDto;
import bank.app.dto.UserBasicDto;
import bank.app.exeptions.UserAlreadyDeletedException;
import bank.app.exeptions.UserNotFountException;
import bank.app.model.entity.Address;
import bank.app.model.entity.PrivateInfo;
import bank.app.model.entity.User;
import bank.app.model.enums.Status;
import bank.app.repository.UserRepository;
import bank.app.service.AddressService;
import bank.app.service.PrivateInfoService;
import bank.app.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivateInfoService privateInfoService;

    @Autowired
    private AddressService addressService;

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            try {
                throw new UserNotFountException("User with ID " + id + " not found");
            } catch (UserNotFountException e) {
                throw new RuntimeException(e);
            }
        }
        return optionalUser.get();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User saveNewUser(UserBasicDto newUserDto) {
        User manager = getUserById(newUserDto.manager());

        User user = new User(newUserDto.username(),newUserDto.password(),
                Status.ACTIVE, newUserDto.role(),manager);
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            try {
                throw new UserNotFountException("User with ID " + id + " not found");
            } catch (UserNotFountException e) {
                throw new RuntimeException(e);
            }
        }
        User user = userOptional.get();
        if (user.getStatus().equals(Status.DELETED)) {
            try {
                throw new UserAlreadyDeletedException("User with ID " + id + " is already deleted");
            } catch (UserAlreadyDeletedException e) {
                throw new RuntimeException(e);
            }
        }
        user.setStatus(Status.DELETED);

        userRepository.save(user);
    }

    @Override
    public User addPrivateInfoToUser(Long id, PrivateInfoDto privateInfoDto) {
        User user = getUserById(id);
        Address savedAddress = addressService.createAddress(privateInfoDto.address());
        PrivateInfo savedPrivateInfo = privateInfoService.createPrivateInfo(privateInfoDto, savedAddress);
        user.setPrivateInfo(savedPrivateInfo);
        userRepository.save(user);
        return user;
    }


//    @Override
//    public User updateInformationAboutUser(UserFullDto fullUserDto, Long id) {
//        if (fullUserDto == null) {
//            throw new IllegalArgumentException("FullUserDto cannot be null");
//        }
//
//        User existingUser = userRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
//
//        if (fullUserDto.privateInfo() == null) {
//            try {
//                throw new PrivateInfoNotFoundException("PrivateInfo is required in request");
//            } catch (PrivateInfoNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        if (fullUserDto.privateInfo().address() == null) {
//            try {
//                throw new PrivateInfoNotFoundException("Address is required in PrivateInfo");
//            } catch (PrivateInfoNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        try {
//
//            existingUser.setUsername(fullUserDto.username());
//            existingUser.setPassword(fullUserDto.password());
//            existingUser.setStatus(Status.valueOf(fullUserDto.status()));
//            existingUser.setRole(fullUserDto.role());
//
//            if (fullUserDto.manager() != null) {
//                User manager = userRepository.findById(fullUserDto.manager())
//                        .orElseThrow(() -> new EntityNotFoundException("Manager not found with id: " + fullUserDto.manager()));
//                existingUser.setManager(manager);
//            } else {
//                existingUser.setManager(null);
//            }
//
//            Address savedAddress = addressService.createAddress(fullUserDto.privateInfo().address());
//            if (savedAddress == null) {
//                throw new RuntimeException("Failed to create address");
//            }
//            PrivateInfo savedPrivateInfo = privateInfoService.createPrivateInfo(fullUserDto.privateInfo(), savedAddress);
//            if (savedPrivateInfo == null) {
//                throw new RuntimeException("Failed to create private info");
//            }
//            existingUser.setPrivateInfo(savedPrivateInfo);
//
//            return userRepository.save(existingUser);
//
//        } catch (Exception e) {
//            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
//        }
//    }
@Override
public User updateInformationAboutUser(UserBasicDto userDto, Long id) {
    if (userDto == null) {
        throw new IllegalArgumentException("FullUserDto cannot be null");
    }
    User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    try {

        existingUser.setUsername(userDto.username());
        existingUser.setPassword(userDto.password());
        existingUser.setStatus(Status.valueOf(userDto.status()));
        existingUser.setRole(userDto.role());

        if (userDto.manager() != null) {
            User manager = userRepository.findById(userDto.manager())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found with id: " + userDto.manager()));
            existingUser.setManager(manager);
        } else {
            existingUser.setManager(null);
        }

        return userRepository.save(existingUser);

    } catch (Exception e) {
        throw new RuntimeException("Error updating user: " + e.getMessage(), e);
    }
}
}
