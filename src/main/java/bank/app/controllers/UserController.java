package bank.app.controllers;


import bank.app.dto.*;
import bank.app.mapper.UserMapper;
import bank.app.model.entity.User;
import bank.app.model.enums.Role;
import bank.app.service.UserService;
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

    @GetMapping()
    public List<UserResponseDto> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/bank")
    public ResponseEntity<UserResponseDto> findByBank() {
        User user = userService.getUserByStatus(Role.BANK);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("user with id :" + id + " was deleted");
    }

    @PostMapping("/")
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto userDto) {
        UserResponseDto user = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/{id}/private_info/add")
    public ResponseEntity<UserResponseDto> addPrivateInfo(@PathVariable Long id, @Valid @RequestBody PrivateInfoRequestDto privateInfoRequestDto) {
        UserResponseDto user = userService.addPrivateInfo(id, privateInfoRequestDto);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto userDto) {
        UserResponseDto user = userService.updateUser(id, userDto);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/private_info")
    public ResponseEntity<UserResponseDto> updatePrivateInfo(@PathVariable Long id, @RequestBody PrivateInfoDto privateInfoDto) {
        UserResponseDto user = userService.updatePrivateInfo(id, privateInfoDto);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/private_info/address")
    public ResponseEntity<UserResponseDto> updateAddress(@PathVariable Long id, @RequestBody AddressRequestDto AddressRequestDto) {
        UserResponseDto user = userService.updateAddress(id, AddressRequestDto);
        return ResponseEntity.ok(user);
    }
}
