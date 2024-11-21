package bank.app.controllers;

import bank.app.dto.*;
import bank.app.mapper.UserMapper;
import bank.app.model.entity.User;
import bank.app.model.enums.Role;
import bank.app.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    public List<UserResponseDto> findAllUsers(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    System.out.println("JSESSIONID: " + cookie.getValue());
                }
            }
        }

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
    public void delete(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    /**
     {
     "username": "Vladimir",
     "password": "adfasdffff",
     "status": "ACTIVE",
     "role": "CUSTOMER",
     "manager" : "2"
     }
     */
    @PostMapping("/")
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto userDto) {
        UserResponseDto user = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     {
     "firstName": "Vladimir",
     "lastName": "Ivanov",
     "email": "vivanov@example.com",
     "phone": "+4915257666666",
     "dateOfBirth": "1990-01-01",
     "documentType": "PASSPORT_EU",
     "documentNumber": "1234567890",
     "comment": "Test comment",
     "address": {
     "country": "USA",
     "street": "Main St",
     "city": "New York",
     "houseNumber": "123",
     "postcode": "10001",
     "info": ""
     }
     }
     */
    @PostMapping("/{id}/private_info/add")
    public ResponseEntity<UserResponseDto> addPrivateInfo(@PathVariable Long id, @Valid @RequestBody PrivateInfoRequestDto privateInfoRequestDto) {
        UserResponseDto user = userService.addPrivateInfo(id, privateInfoRequestDto);
        return ResponseEntity.ok(user);
    }

    /**
     *
     * {
              "username": "Ra",
              "password": "Fa",
              "status": "ACTIVE",
              "role": "CUSTOMER",
              "manager" : "2"
          }
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto userDto) {
        UserResponseDto user = userService.updateUser(id, userDto);
        return ResponseEntity.ok(user);
    }

    /**
     {
     "firstName": "Vladimir",
     "lastName": "Ivanov",
     "email": "vivanov@example.com",
     "phone": "+4915257776666",
     "dateOfBirth": "1990-01-01",
     "documentType": "PASSPORT_EU",
     "documentNumber": "1234567890",
     "comment": "Test comment"
     }
     */

    @PutMapping("/{id}/private_info")
    public ResponseEntity<UserResponseDto> updatePrivateInfo(@PathVariable Long id, @RequestBody PrivateInfoDto privateInfoDto) {
        UserResponseDto user = userService.updatePrivateInfo(id, privateInfoDto);
        return ResponseEntity.ok(user);
    }

    /**
     {

     "country": "USA",
     "street": "Main St22",
     "city": "New York",
     "houseNumber": "12311",
     "postcode": "10001",
     "info": ""
     }
     */
    @PutMapping("/{id}/private_info/address")
    public ResponseEntity<UserResponseDto> updateAddress(@PathVariable Long id, @RequestBody AddressRequestDto AddressRequestDto) {
        UserResponseDto user = userService.updateAddress(id, AddressRequestDto);
        return ResponseEntity.ok(user);
    }
}
