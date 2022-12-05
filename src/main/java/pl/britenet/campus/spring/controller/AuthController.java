package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campus.spring.Service.AuthService;
import pl.britenet.campus.spring.model.Credentials;
import pl.britenet.campus.spring.model.LoginResponse;

import java.security.NoSuchAlgorithmException;

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

    @PostMapping("/register")
    public void register(@RequestBody Credentials credentials) {
        this.authService.register(credentials);
    }
}
