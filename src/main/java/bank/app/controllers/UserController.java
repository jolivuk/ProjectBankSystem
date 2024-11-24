package bank.app.controllers;

import bank.app.dto.*;
import bank.app.mapper.PrivateInfoMapper;
import bank.app.mapper.UserMapper;
import bank.app.model.entity.PrivateInfo;
import bank.app.model.entity.User;
import bank.app.model.enums.Role;
import bank.app.service.PrivateInfoService;
import bank.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    private final PrivateInfoService privateInfoService;
    private final UserMapper userMapper;
    private final PrivateInfoMapper privateInfoMapper;

    @Operation(
            summary = "find All users in database",
            description = "receives nothing in parameters and returns UserResponseDto"
    )
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

    @Operation(
            summary = "gets 1 user from the database by id",
            description = "takes user id in parameters and returns UserResponseDto"
    )

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
            summary = "find the user who is the bank",
            description = "accepts nothing and returns UserResponseDto"
    )
    @GetMapping("/bank")
    public ResponseEntity<UserResponseDto> findByBank() {
        User user = userService.getUserByStatus(Role.BANK);
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
    public void delete(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    /**
     {
     "username": "customer5",
     "password": "sUy9ITt08Q8Mgty",
     "status": "ACTIVE",
     "role": "CUSTOMER",
     "manager": 1
     }
     */
    @Operation(
            summary = "adds the user to the database",
            description = "takes UserRequestDto and returns UserResponseDto when added"
    )
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
     "comment": "string",
     "address": {
     "country": "USA",
     "city": "New York",
     "postcode": "10001",
     "street": "Main St",
     "houseNumber": "123",
     "info": "string"
     }
     }
     */

    @Operation(
            summary = "adds the user's private field to the database ",
            description = "takes PrivateInfoRequestDto and returns UserResponseDto" +
                    " which contains updated information about the user"
    )
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

    @Operation(
            summary = "updates the user  ",
            description = "accepts UserRequestDto and returns UserResponseDto"
    )
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
     "comment": "Test comment",
     "address": null
     }
     */

    @Operation(
            summary = " update user's private information by specified id ",
            description = "accepts PrivateInfoDto and returns UserResponseDto"
    )

    @PutMapping("/{id}/private_info")
    public ResponseEntity<UserResponseDto> updatePrivateInfo(@PathVariable Long id, @RequestBody PrivateInfoRequestDto privateInfoDto) {
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

    @Operation(
            summary = " update user's address by specified id ",
            description = "accepts AddressRequestDto and returns UserResponseDto"
    )
    @PutMapping("/{id}/private_info/address")
    public ResponseEntity<UserResponseDto> updateAddress(@PathVariable Long id, @RequestBody AddressRequestDto AddressRequestDto) {
        UserResponseDto user = userService.updateAddress(id, AddressRequestDto);
        return ResponseEntity.ok(user);
    }
}
