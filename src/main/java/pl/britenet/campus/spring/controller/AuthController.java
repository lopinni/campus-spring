package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campus.spring.model.Credentials;
import pl.britenet.campus.spring.model.LoginResponse;
import pl.britenet.campus.spring.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody Credentials credentials) {
        return this.authService.login(credentials);
    }

    @GetMapping
    public boolean getUser(@RequestHeader("Authorization") String token) {
        return this.authService.isLoggedIn(token);
    }

    @GetMapping("/id")
    public Integer getUserId(@RequestHeader("Authorization") String token) {
        return this.authService.getUserId(token);
    }

    @GetMapping("/username")
    public String getUserFullName(@RequestHeader("Authorization") String token) {
        String userNameJSON = "";
        try {
            userNameJSON = "{ \"name\": \""+ this.authService.getUserFullName(token) + "\" }";
        } catch (NullPointerException e) {
            System.out.println("User does not exist");
        }
        return userNameJSON;
    }

    @PostMapping("/register")
    public void register(@RequestBody Credentials credentials,
                         @RequestHeader("Name") String name,
                         @RequestHeader("Surname") String surname) {
        this.authService.register(credentials, name, surname);
    }
}
