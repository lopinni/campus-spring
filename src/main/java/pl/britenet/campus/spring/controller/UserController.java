package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campus.spring.service.AuthService;
import pl.britenet.campusapiapp.model.User;
import pl.britenet.campusapiapp.model.builder.UserBuilder;
import pl.britenet.campusapiapp.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
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

    @PostMapping("/update")
    public User updateUser(@RequestBody User user) {
        this.userService.updateUser(user);
        return user;
    }

    @GetMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id) {
        this.userService.deleteUser(id);
    }

    @PostMapping("/update/address")
    public void updateShippingAddress(@RequestHeader("Authorization") String token,
                                      @RequestHeader("City") String city,
                                      @RequestHeader("Street") String street,
                                      @RequestHeader("Country") String country,
                                      @RequestHeader("ZipCode") String zipCode) {
        try {
            User user = this.userService.getUser(this.authService.getUserId(token));
            user.setCity(city);
            user.setStreet(street);
            user.setCountry(country);
            user.setZipCode(zipCode);
            this.userService.updateUser(user);
        } catch (NullPointerException e) {
            System.out.println("User does not exist");
        }
    }
}
