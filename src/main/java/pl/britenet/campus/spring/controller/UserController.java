package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campusapiapp.model.User;
import pl.britenet.campusapiapp.service.UserService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return this.userService.getUser(id);
    }

    @PostMapping
    public User insertUser(@RequestBody User user) {
        this.userService.insertUser(user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        this.userService.updateUser(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        this.userService.deleteUser(id);
    }
}
