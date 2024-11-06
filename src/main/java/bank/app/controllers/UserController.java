package bank.app.controllers;


import bank.app.dto.PrivateInfoDto;
import bank.app.dto.UserDto;
import bank.app.model.entity.Address;
import bank.app.model.entity.PrivateInfo;
import bank.app.model.entity.User;
import bank.app.model.enums.Role;
import bank.app.model.enums.Status;
import bank.app.service.AddressService;
import bank.app.service.PrivateInfoService;
import bank.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PrivateInfoService privateInfoService;

    @Autowired
    private AddressService addressService;

    @GetMapping()
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("user with id :" + id + " was deleted");
    }

    @PostMapping("/")
    public ResponseEntity<User> create(@RequestBody UserDto userDto) {
//        Role role = switch (userDto.role()) {
//            case "CUSTOMER" -> Role.ROLE_CUSTOMER;
//            case "ADMIN" -> Role.ROLE_ADMIN;
//            case "MANAGER" -> Role.ROLE_MANAGER;
//            case "SUPERMANAGER" -> Role.ROLE_SUPER_MANAGER;
//            default -> throw new IllegalArgumentException("Unknown role: " + userDto.role());
//        };
        Optional<User> manager = userService.getUserById(userDto.manager());
        if(!manager.isPresent()){
           // throw new ManagerNotFoundExeption()
            return (ResponseEntity<User>) ResponseEntity.notFound();
        }
        User user = new User(userDto.username(),userDto.password(),
                Status.ACTIVE, userDto.role(),manager.get());
        userService.saveNewUser(user);
        return ResponseEntity.ok(user);

    }

    @PostMapping("/{id}/add/private_info")
    public ResponseEntity<User> addPrivateInfo(@PathVariable Long id, @RequestBody PrivateInfoDto privateInfoDto) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User user = optionalUser.get();
        Address address = new Address(
                privateInfoDto.address().country(),
                privateInfoDto.address().street(),
                privateInfoDto.address().city(),
                privateInfoDto.address().state(),
                privateInfoDto.address().postalCode(),
                privateInfoDto.address().country()
        );

        Address savedAddress = addressService.saveAddress(address);

        PrivateInfo privateInfo = new PrivateInfo(
                privateInfoDto.firstName(),
                privateInfoDto.lastName(),
                privateInfoDto.email(),
                privateInfoDto.phone(),
                privateInfoDto.dateOfBirth(),
                privateInfoDto.documentType(),
                privateInfoDto.documentNumber(),
                privateInfoDto.comment(),
                savedAddress
        );
        PrivateInfo savedPrivateInfo = privateInfoService.savePrivateInfo(privateInfo);

        user.setPrivateInfo(savedPrivateInfo);
        userService.saveNewUser(user);

        return ResponseEntity.ok(user);
    }

}
