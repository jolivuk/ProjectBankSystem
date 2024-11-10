package bank.app.controllers;


import bank.app.dto.AddressDto;
import bank.app.dto.PrivateInfoDto;
import bank.app.dto.UserBasicDto;
import bank.app.model.entity.Account;
import bank.app.model.entity.User;
import bank.app.service.AddressService;
import bank.app.service.PrivateInfoService;
import bank.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
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


    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("user with id :" + id + " was deleted");
    }

    @PostMapping("/")
    public ResponseEntity<User> create(@RequestBody UserBasicDto userDto) {
        User user = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/{id}/private_info/add")
    public ResponseEntity<User> addPrivateInfo(@PathVariable Long id, @RequestBody PrivateInfoDto privateInfoDto) {
        User user = userService.addPrivateInfo(id, privateInfoDto);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserBasicDto userDto) {
        User user = userService.updateUser(id, userDto);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/private_info")
    public ResponseEntity<User> updatePrivateInfo(@PathVariable Long id, @RequestBody PrivateInfoDto privateInfoDto) {
        User user = userService.updatePrivateInfo(id, privateInfoDto);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/private_info/address")
    public ResponseEntity<User> updateAddress(@PathVariable Long id, @RequestBody AddressDto AddressDto) {
        User user = userService.updateAddress(id, AddressDto);
        return ResponseEntity.ok(user);
    }
}
