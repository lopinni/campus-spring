package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campus.spring.service.AuthService;
import pl.britenet.campusapiapp.model.Cart;
import pl.britenet.campusapiapp.model.Order;
import pl.britenet.campusapiapp.service.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final AuthService authService;

    @Autowired
    public CartController(CartService cartService, AuthService authService) {
        this.cartService = cartService;
        this.authService = authService;
    }

    @GetMapping
    public List<Cart> getAll() {
        return this.cartService.getAll();
    }

    @GetMapping("/{id}")
    public Cart getCart(@PathVariable int id) {
        return this.cartService.getCart(id);
    }

    @GetMapping("/token")
    public List<Cart> getCart(@RequestHeader("Authorization") String token) {
        Optional<Integer> userId = this.authService.getUserId(token).describeConstable();
        if (userId.isPresent()) {
            return this.cartService.getByUserId(userId.get());
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping
    public Cart insertCart(@RequestBody Cart cart) {
        this.cartService.insertCart(cart);
        return cart;
    }

    @PutMapping
    public Cart updateCart(@RequestBody Cart cart) {
        this.cartService.updateCart(cart);
        return cart;
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable int id) {
        this.cartService.deleteCart(id);
    }
}
