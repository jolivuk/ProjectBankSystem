package bank.app.controllers;

import bank.app.entity.User;
import bank.app.model.UserExample;
import bank.app.service.UserService;
import bank.app.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public List<User> showAllUsers(){
        List<User> allUser = userService.getAllUsers();
        return allUser;
    }
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id){
        User user = userService.getUser(id);
        return user;
    }

    @GetMapping("/connection")
    public String connect(){
        return userService.checkConnection();
    }
}
