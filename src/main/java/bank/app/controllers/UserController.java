package bank.app.controllers;

import bank.app.dto.*;
import bank.app.mapper.UserMapper;
import bank.app.model.entity.User;
import bank.app.model.enums.Role;
import bank.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(
            summary = "Adds the user to the database",
            description = "Takes UserRequestDto and returns UserResponseDto when added",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User object that needs to be created",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRequestDto.class),
                            examples = @ExampleObject(
                                    name = "Default User Example",
                                    value = """
                                            {
                                              "username": "customer5",
                                              "password": "sUy9ITt08Q8Mgty",
                                              "status": "ACTIVE",
                                              "role": "CUSTOMER",
                                              "manager": 2
                                            }
                                """
                            )
                    )
            )
    )
    @PostMapping("/")
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto userDto) {
        UserResponseDto user = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Operation(
            summary = "adds the user's private field to the database ",
            description = "takes PrivateInfoRequestDto and returns UserResponseDto" +
                    " which contains updated information about the user",

            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User object that needs to be created",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PrivateInfoRequestDto.class),
                    examples = @ExampleObject(
                            name = "Default User Example",
                            value = """
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
                                """
                    )
            ))

    )
    @PostMapping("/{id}/private_info/")
    public ResponseEntity<UserResponseDto> addPrivateInfo(@PathVariable Long id, @Valid @RequestBody PrivateInfoRequestDto privateInfoRequestDto) {
        UserResponseDto user = userService.addPrivateInfo(id, privateInfoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
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
            description = "accepts UserRequestDto and returns UserResponseDto",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User object that needs to be created",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRequestDto.class),
                            examples = @ExampleObject(
                                    name = "Default User Example",
                                    value = """
                                            {
                                                "username": "Ra",
                                                "password": "Fa",
                                                "status": "ACTIVE",
                                                "role": "CUSTOMER",
                                                "manager" : "2"
                                            }
                                """
                            )
                    )
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto userDto) {
        UserResponseDto user = userService.updateUser(id, userDto);
        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = " update user's private information by specified id ",
            description = "accepts PrivateInfoDto and returns PrivateInfoResponseDto",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "PrivateInfo object that needs to be created",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PrivateInfoRequestDto.class),
                    examples = @ExampleObject(
                            name = "Default PrivateInfo Example",
                            value = """
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
                                """
                    )
                )
            )
    )

    @PutMapping("/{id}/private_info")
    public ResponseEntity<UserResponseDto> updatePrivateInfo(@PathVariable Long id, @RequestBody PrivateInfoRequestDto privateInfoDto) {
        UserResponseDto user = userService.updatePrivateInfo(id, privateInfoDto);
        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = " update user's address by specified id ",
            description = "accepts AddressRequestDto and returns AddressResponseDto",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Address object that needs to be created",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AddressRequestDto.class),
                            examples = @ExampleObject(
                                    name = "Default Address Example",
                                    value = """
                                         {
                                              "country": "USA",
                                              "street": "Main St22",
                                              "city": "New York",
                                              "houseNumber": "12311",
                                              "postcode": "10001",
                                              "info": ""
                                         }
                                         """
                            )
                    )
            )
    )
    @PutMapping("/{id}/private_info/address")
    public ResponseEntity<UserResponseDto> updateAddress(@PathVariable Long id, @RequestBody AddressRequestDto AddressRequestDto) {
        UserResponseDto user = userService.updateAddress(id, AddressRequestDto);
        return ResponseEntity.ok(user);
    }
}
