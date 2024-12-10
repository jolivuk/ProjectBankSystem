package bank.app.service.impl;

import bank.app.dto.*;
import bank.app.exeption.*;
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
import bank.app.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static bank.app.exeption.errorMessage.ErrorMessage.*;
import static bank.app.util.PrivateInfoUtil.createPrivateInfo;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
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
                .orElseThrow(() -> {
                    log.error("User with ID {} not found", id);
                    return new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
                });
        log.info("Successfully retrieved user with ID: {}", id);
        return userMapper.toDto(user);
    }

    @Override
    public List<UserResponseDto> findAll() {
        log.info("Fetching all users");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDto> findAllByManagerId(Long id) {
        log.info("Finding users for manager ID: {}", id);
        User manager = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Manager with ID {} not found", id);
                    return new UserNotFoundException(ErrorMessage.MANAGER_ID_NOT_FOUND);
                });

        if (!isManager(manager)) {
            log.error("User with ID {} has incorrect role (not a manager)", id);
            throw new UserRoleException(ErrorMessage.MANAGER_ID_HAS_INCORRECT_ROLE);

        }
        return userRepository.findAllByManagerId(id)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        log.info("Starting to create new user with username: {}", userRequestDto.username());

        User manager = userRepository.findById(userRequestDto.manager())
                .orElseThrow(() -> {
                    log.error("Manager with ID {} not found", userRequestDto.manager());
                    return new UserNotFoundException(ErrorMessage.MANAGER_ID_NOT_FOUND);
                });


        if (!isManager(manager)) {
            log.error("User with ID {} has incorrect role (not a manager)", userRequestDto.manager());
            throw new UserRoleException(ErrorMessage.MANAGER_ID_HAS_INCORRECT_ROLE);
        }
        String encodedPassword = passwordEncoder.encode(userRequestDto.password());
        User user = new User(userRequestDto.username(), encodedPassword,
                Status.ACTIVE, userRequestDto.role(), manager);
        userRepository.save(user);

        log.info("Successfully created new user with username: {}", user.getUsername());
        return userMapper.toDto(user);
    }

    @Override
    public User getUserByStatus(Role role) {
        return userRepository.findByRole(role)
                .orElseThrow(() -> new UserNotFoundException(USER_WITH_ROLE_NOT_FOUND + role));
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Attempting to delete user with ID: {}", id);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            log.error("Failed to delete - user with ID: {} not found", id);
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        User user = userOptional.get();
        if (user.getStatus().equals(Status.DELETED)) {
            log.error("Failed to delete - user with ID: {} already has deleted status", id);
            throw new UserAlreadyDeletedException("User with ID " + id + " is already deleted");
        }
        user.setStatus(Status.DELETED);

        List<Account> accounts = accountRepository.findAllByUserId(id);
        log.info("Found {} accounts to delete for user ID: {}", accounts.size(), id);
        for (Account account : accounts) {
            account.setStatus(Status.DELETED);
        }

        accountRepository.saveAll(accounts);
        userRepository.save(user);
        log.info("Successfully deleted user with ID: {} and his {} accounts", id, accounts.size());
    }

    @Override
    public void blockUserById(Long id) {
        log.info("Attempting to block user with ID: {}", id);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            log.error("Failed to block - user with ID: {} not found", id);
            throw new UserNotFoundException(USER_NOT_FOUND + id);
        }
        User user = userOptional.get();
        if (user.getStatus().equals(Status.DELETED)) {
            log.error("Failed to block - user with ID: {} already has deleted status", id);
            throw new UserAlreadyDeletedException(ErrorMessage.USER_ALREADY_DELETED);
        }

        if (user.getStatus().equals(Status.BLOCKED)) {
            log.error("Failed to block - user with ID: {} already has blocked status", id);
            throw new UserAlreadyBlockedException(ErrorMessage.USER_ALREADY_BLOCKED);
        }
        user.setStatus(Status.BLOCKED);

        List<Account> accounts = accountRepository.findAllByUserId(id);
        log.info("Found {} accounts to block for user ID: {}", accounts.size(), id);
        for (Account account : accounts) {
            if(!account.getStatus().equals(Status.DELETED)) {
                account.setStatus(Status.BLOCKED);
            }
        }

        accountRepository.saveAll(accounts);
        userRepository.save(user);
        log.info("Successfully block user with ID: {} and his {} accounts", id, accounts.size());
    }


    @Override
    public void unblockUserById(Long id) {
        log.info("Attempting to unblock user with ID: {}", id);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            log.error("Failed to unblock - user with ID: {} not found", id);
            throw new UserNotFoundException(USER_NOT_FOUND + id);
        }
        User user = userOptional.get();
        if (!user.getStatus().equals(Status.BLOCKED)) {
            log.error("Failed to block - status of user with ID: {} is not BLOCKED", id);
            throw new UserWrongStatusException(ErrorMessage.USER_WRONG_STATUS);
        }

        user.setStatus(Status.ACTIVE);

        userRepository.save(user);
        log.info("Successfully unblock user with ID: {}", id);
    }
    @Override
    public PrivateInfoResponseDto getPrivateInfoByUserId(Long id) {
        log.info("Retrieving private info for user ID: {}", id);
        return getUserById(id).privateInfoResponse();
    }

    @Override
    public UserResponseDto addPrivateInfo(Long id, PrivateInfoRequestDto privateInfoRequestDto) {
        log.info("Adding private info for user ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                {
                    log.error("User with ID {} not found while adding private info", id);
                    return new UserNotFoundException("User with ID " + id + " not found");
                });

        PrivateInfo privateInfo = createPrivateInfo(privateInfoRequestDto, user);

        privateInfoRepository.save(privateInfo);

        user.setPrivateInfo(privateInfo);
        userRepository.save(user);
        log.info("Successfully added private info for user ID: {}", id);

        return userMapper.toDto(user);
    }


    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userDto) {
        log.info("Starting update for user with ID: {}", id);
        if (userDto == null) {
            log.error("Update failed - UserRequestDto is null for user ID: {}", id);
            throw new IllegalArgumentException(USER_DTO_IS_NULL);
        }
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Update failed - User not found with ID: {}", id);
                    return new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
                });

        try {
            String encodedPassword = passwordEncoder.encode(userDto.password());

            existingUser.setUsername(userDto.username());
            existingUser.setPassword(encodedPassword);
            existingUser.setStatus(userDto.status());
            existingUser.setRole(userDto.role());

            if (userDto.manager() != null) {
                User manager = userRepository.findById(userDto.manager())
                        .orElseThrow(() -> {
                            log.error("Update failed - Manager not found with ID: {}", userDto.manager());
                            return new EntityNotFoundException(MANAGER_ID_NOT_FOUND + userDto.manager());
                        });
                existingUser.setManager(manager);
            } else {
                existingUser.setManager(null);
            }
            userRepository.save(existingUser);
            log.info("Successfully updated user with ID: {}", id);
            return userMapper.toDto(existingUser);

        } catch (Exception e) {
            log.error("Failed to update user with ID: {}. Error: {}", id, e.getMessage(), e);
            throw new RuntimeException(ENABLE_UPDATE_USER + e.getMessage(), e);
        }
    }

    @Override
    public UserResponseDto updatePrivateInfo(Long id, PrivateInfoRequestDto privateInfoDto) {
        log.info("Starting to update private info for user ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                {
                    log.error("Failed to update private info - user not found with ID: {}", id);
                    return new EntityNotFoundException(USER_NOT_FOUND + id);
                });

        PrivateInfo privateInfo = user.getPrivateInfo();
        if (privateInfo == null) {
            log.info("Creating new private info for user ID: {}", id);
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

        log.info("Successfully updated private info for user ID: {}", id);
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateAddress(Long id, AddressRequestDto AddressRequestDto) {
        log.info("Starting to update address for user ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new EntityNotFoundException(USER_NOT_FOUND + id);
                });

        PrivateInfo privateInfo = user.getPrivateInfo();
        if (privateInfo == null) {
            log.info("Creating new PrivateInfo for user ID: {}", id);
            privateInfo = new PrivateInfo();
            user.setPrivateInfo(privateInfo);
        }

        Address address = privateInfo.getAddress();
        if (address == null) {
            log.info("Creating new Address for user ID: {}", id);
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
        log.info("Successfully updated address for user ID: {}", id);
        return userMapper.toDto(user);
    }

    @Override
    public boolean isManager(User user) {
        log.debug("Checking if user ID: {} has manager role. Current role: {}", user.getId(), user.getRole());
        return user.getRole().equals(Role.ROLE_MANAGER);
    }


}
