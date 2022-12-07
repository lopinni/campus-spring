package pl.britenet.campus.spring.service;

import jakarta.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Service;
import pl.britenet.campus.spring.model.Credentials;
import pl.britenet.campus.spring.model.LoginResponse;
import pl.britenet.campusapiapp.model.User;
import pl.britenet.campusapiapp.model.builder.UserBuilder;
import pl.britenet.campusapiapp.service.UserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserService userService;

    private final Map<String, Integer> activeTokenMap = new HashMap<>();

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public LoginResponse login(Credentials credentials) {
        Optional<User> user;
        String token = UUID.randomUUID().toString();
        try {
            user = Optional.ofNullable(this.userService.findUser(
                    credentials.getUsername(),
                    hashMD5(credentials.getPassword())
            ));
            if (user.isPresent()) {
                this.activeTokenMap.put(token, user.get().getId());
                return new LoginResponse(true, token);
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Password hash failed");
        }
        return new LoginResponse(false, "");
    }

    public boolean isLoggedIn(String token) {
        return this.activeTokenMap.containsKey(token);
    }

    public void register(Credentials credentials) {
        try {
            userService.insertUser(new UserBuilder(new User())
                    .setLogin(credentials.getUsername())
                    .setPassword(hashMD5(credentials.getPassword()))
                    .getUser());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Password hash failed");
        }
    }

    public String hashMD5(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        return DatatypeConverter.printHexBinary(messageDigest.digest()).toUpperCase();
    }
}
