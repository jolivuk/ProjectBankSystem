package bank.app.controllers;


import bank.app.model.entity.User;
import bank.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
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


//    @PostMapping("/")
//    public User create(@RequestBody UserDto userDto) {
//        User user = new User(userDto.firstName(),userDto.lastName(), userDto.email(), userDto.username(), userDto.password(),
//        userDto.telephone());
//        return userService.saveNewUser(user);
//    }

}
