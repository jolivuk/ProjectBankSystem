package bank.app.service.impl;

import bank.app.dto.*;
import bank.app.exeption.UserAlreadyDeletedException;
import bank.app.exeption.UserNotFoundException;
import bank.app.exeption.UserRoleException;
import bank.app.exeption.errorMessage.ErrorMessage;
import bank.app.mapper.UserMapper;
import bank.app.model.entity.Account;
import bank.app.model.entity.Address;
import bank.app.model.entity.PrivateInfo;
import bank.app.model.entity.User;
import bank.app.model.enums.Role;
import bank.app.model.enums.Status;
import bank.app.repository.AccountRepository;
import bank.app.repository.PrivateInfoRepository;
import bank.app.repository.UserRepository;
import bank.app.service.PrivateInfoService;
import bank.app.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static bank.app.exeption.errorMessage.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PrivateInfoService privateInfoService;
    private final AccountRepository accountRepository;
    private final UserMapper userMapper;
    private final PrivateInfoRepository privateInfoRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.USERNAME_NOT_FOUND));
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDto> findAllByManagerId(Long id){
        User manager = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.MANAGER_ID_NOT_FOUND));

        if (!isManager(manager))
            throw new UserRoleException(ErrorMessage.MANAGER_ID_HAS_INCORRECT_ROLE);

        return userRepository.findAllByManagerId(id)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User manager = userRepository.findById(userRequestDto.manager())
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.MANAGER_ID_NOT_FOUND));

        if (!isManager(manager))
            throw new UserRoleException(ErrorMessage.MANAGER_ID_HAS_INCORRECT_ROLE);

        String encodedPassword = passwordEncoder.encode(userRequestDto.password());
        User user = new User(userRequestDto.username(),encodedPassword,
                Status.ACTIVE, userRequestDto.role(),manager);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public User getUserByStatus(Role role) {
        return userRepository.findByRole(role)
                .orElseThrow(() -> new UserNotFoundException(USER_WITH_ROLE_NOT_FOUND + role));
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
    public PrivateInfoResponseDto getPrivateInfoByUserId(Long id) {
        return getUserById(id).privateInfoResponse();
    }

    @Override
    public UserResponseDto addPrivateInfo(Long id, PrivateInfoRequestDto privateInfoRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        PrivateInfo privateInfo = privateInfoService.createPrivateInfo(privateInfoRequestDto,user);

        privateInfoRepository.save(privateInfo);

        user.setPrivateInfo(privateInfo);
        userRepository.save(user);

        return userMapper.toDto(user);
    }


    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException(USER_DTO_IS_NULL);
        }
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
        try {

            String encodedPassword = passwordEncoder.encode(userDto.password());

            existingUser.setUsername(userDto.username());
            existingUser.setPassword(encodedPassword);
            existingUser.setStatus(userDto.status());
            existingUser.setRole(userDto.role());

            if (userDto.manager() != null) {
                User manager = userRepository.findById(userDto.manager())
                        .orElseThrow(() -> new EntityNotFoundException(MANAGER_ID_NOT_FOUND + userDto.manager()));
                existingUser.setManager(manager);
            } else {
                existingUser.setManager(null);
            }
            userRepository.save(existingUser);
            return userMapper.toDto(existingUser);

        } catch (Exception e) {
            throw new RuntimeException(ENABLE_UPDATE_USER + e.getMessage(), e);
        }
    }

    @Override
    public UserResponseDto updatePrivateInfo(Long id, PrivateInfoRequestDto privateInfoDto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND + id));

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

        privateInfoRepository.save(privateInfo);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateAddress(Long id, AddressRequestDto AddressRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND + id));

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

        address.setCountry(AddressRequestDto.country());
        address.setCity(AddressRequestDto.city());
        address.setPostcode(AddressRequestDto.postcode());
        address.setStreet(AddressRequestDto.street());
        address.setHouseNumber(AddressRequestDto.houseNumber());
        address.setInfo(AddressRequestDto.info());

        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public boolean isManager(User user) {
        return user.getRole().equals(Role.ROLE_MANAGER);
    }


}
