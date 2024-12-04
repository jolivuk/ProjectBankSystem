package bank.app.controllers;

import bank.app.annotation.*;
import bank.app.dto.*;
import bank.app.mapper.UserMapper;
import bank.app.model.entity.User;
import bank.app.model.enums.Role;
import bank.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            summary = "find All users in database",
            description = "receives nothing in parameters and returns UserResponseDto"
    )
    @GetMapping()
    public List<UserResponseDto> findAllUsers() {
        return userService.findAll();
    }

    @Operation(
            summary = "gets 1 user from the database by id",
            description = "takes user id in parameters and returns UserResponseDto"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @Operation(
            summary = "find All users for manager with Id",
            description = "takes user id (manager id) in parameters and returns List<UserResponseDto>"
    )
    @GetMapping("/{id}/customers")
    public List<UserResponseDto> findAllUsersForManager(@PathVariable Long id) {

        return userService.findAllByManagerId(id);
    }

    @Operation(
            summary = "find the user who is the bank",
            description = "accepts nothing and returns UserResponseDto"
    )
    @GetMapping("/bank")
    public ResponseEntity<UserResponseDto> findByBank() {
        User user = userService.getUserByStatus(Role.ROLE_BANK);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @Operation(
            summary = "retrieves private information about the user from the database",
            description = "takes user id and returns PrivateInfoResponseDto"
    )

    @GetMapping("/{id}/private_info")
    public ResponseEntity<PrivateInfoResponseDto> getPrivateInfo(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getPrivateInfoByUserId(id));
    }

    @Operation(
            summary = "delete user from the database",
            description = "takes the user ID and deletes it from the database, and if the user is not created," +
                    " throws an exception that the user is not found"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }



    @CreateUser(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto userDto) {
        UserResponseDto user = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


    @AddPrivateInfo(path = "/{id}/private_info/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDto> addPrivateInfo(@PathVariable Long id, @Valid @RequestBody PrivateInfoRequestDto privateInfoRequestDto) {
        UserResponseDto user = userService.addPrivateInfo(id, privateInfoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


    @UpdateUser(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto userDto) {
        UserResponseDto user = userService.updateUser(id, userDto);
        return ResponseEntity.ok(user);
    }


    @UpdatePrivateInfo(path = "/{id}/private_info")
    public ResponseEntity<UserResponseDto> updatePrivateInfo(@PathVariable Long id, @RequestBody PrivateInfoRequestDto privateInfoDto) {
        UserResponseDto user = userService.updatePrivateInfo(id, privateInfoDto);
        return ResponseEntity.ok(user);
    }



    @UpdateAddress(path = "/{id}/private_info/address")
    public ResponseEntity<UserResponseDto> updateAddress(@PathVariable Long id, @RequestBody AddressRequestDto AddressRequestDto) {
        UserResponseDto user = userService.updateAddress(id, AddressRequestDto);
        return ResponseEntity.ok(user);
    }
}
