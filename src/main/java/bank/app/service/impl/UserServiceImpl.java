package bank.app.service.impl;

import bank.app.dto.AddressCreateRequestDto;
import bank.app.dto.PrivateInfoDto;
import bank.app.dto.UserBasicDto;
import bank.app.exeptions.UserAlreadyDeletedException;
import bank.app.exeptions.UserNotFoundException;
import bank.app.mapper.AddressMapper;
import bank.app.model.entity.Account;
import bank.app.model.entity.Address;
import bank.app.model.entity.PrivateInfo;
import bank.app.model.entity.User;
import bank.app.model.enums.Status;
import bank.app.repository.AccountRepository;
import bank.app.repository.AddressRepository;
import bank.app.repository.UserRepository;
import bank.app.service.AddressService;
import bank.app.service.PrivateInfoService;
import bank.app.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PrivateInfoService privateInfoService;
    private final AddressService addressService;
    private final AddressRepository addressRepository;
    private final AccountRepository accountRepository;
    private final AddressMapper addressMapper;


    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserBasicDto newUserDto) {
        User manager = getUserById(newUserDto.manager());

        User user = new User(newUserDto.username(),newUserDto.password(),
                Status.ACTIVE, newUserDto.role(),manager);
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
                throw new UserNotFoundException("User with ID " + id + " not found");
        }
        User user = userOptional.get();
        if (user.getStatus().equals(Status.DELETED)) {
                throw new UserAlreadyDeletedException("User with ID " + id + " is already deleted");
        }
        user.setStatus(Status.DELETED);

        List<Account> accounts = accountRepository.findAllByUserId(id);
        for (Account account : accounts) {
            account.setStatus(Status.DELETED);
        }

        accountRepository.saveAll(accounts);
        userRepository.save(user);
    }

    @Override
    public User addPrivateInfo(Long id, PrivateInfoDto privateInfoDto) {
        User user = getUserById(id);
        Address address = addressMapper.toAddress(privateInfoDto.address());
        addressRepository.save(address);
        PrivateInfo savedPrivateInfo = privateInfoService.createPrivateInfo(privateInfoDto, address);
        user.setPrivateInfo(savedPrivateInfo);
        userRepository.save(user);
        return user;
    }



    @Override
    public User updateUser(Long id,UserBasicDto userDto) {
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

    @Override
    public User updatePrivateInfo(Long id, PrivateInfoDto privateInfoDto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        PrivateInfo privateInfo = user.getPrivateInfo();
        if (privateInfo == null) {
            privateInfo = new PrivateInfo();
            user.setPrivateInfo(privateInfo);
        }

        privateInfo.setFirstName(privateInfoDto.firstName());
        privateInfo.setLastName(privateInfoDto.lastName());
        privateInfo.setEmail(privateInfoDto.email());
        privateInfo.setPhone(privateInfoDto.phone());
        privateInfo.setDateOfBirth(privateInfoDto.dateOfBirth());
        privateInfo.setDocumentType(privateInfoDto.documentType());
        privateInfo.setDocumentNumber(privateInfoDto.documentNumber());
        privateInfo.setComment(privateInfoDto.comment());

        privateInfoService.savePrivateInfo(privateInfo);
        return userRepository.save(user);
    }

    @Override
    public User updateAddress(Long id, AddressCreateRequestDto AddressCreateRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        PrivateInfo privateInfo = user.getPrivateInfo();
        if (privateInfo == null) {
            privateInfo = new PrivateInfo();
            user.setPrivateInfo(privateInfo);
        }

        Address address = privateInfo.getAddress();
        if (address == null) {
            address = new Address();
            privateInfo.setAddress(address);
        }

        address.setCountry(AddressCreateRequestDto.country());
        address.setCity(AddressCreateRequestDto.city());
        address.setPostcode(AddressCreateRequestDto.postcode());
        address.setStreet(AddressCreateRequestDto.street());
        address.setHouseNumber(AddressCreateRequestDto.houseNumber());
        address.setInfo(AddressCreateRequestDto.info());

        return userRepository.save(user);
    }

}
