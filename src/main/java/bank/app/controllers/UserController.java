package bank.app.controllers;


import bank.app.dto.AddressDto;
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
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("user with id :" + id + " was deleted");
    }

    @PostMapping("/")
    public ResponseEntity<User> create(@RequestBody UserDto userDto) {
        User user = userService.saveNewUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/{id}/add/private_info")
    public ResponseEntity<User> addPrivateInfo(@PathVariable Long id, @RequestBody PrivateInfoDto privateInfoDto) {

        User user = userService.addPrivateInfoToUser(id, privateInfoDto);

        return ResponseEntity.ok(user);
    }

}
