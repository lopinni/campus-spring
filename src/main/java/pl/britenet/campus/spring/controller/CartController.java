package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campus.spring.service.AuthService;
import pl.britenet.campusapiapp.model.Cart;
import pl.britenet.campusapiapp.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService, AuthService authService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<Cart> getAll() {
        return this.cartService.getAll();
    }

    @GetMapping("/{id}")
    public Cart getCart(@PathVariable int id) {
        return this.cartService.getCart(id);
    }

    @PostMapping
    public Cart insertCart(@RequestBody Cart cart) {
        this.cartService.insertCart(cart);
        cart.setId(cartService.getNewestCartItem().getId());
        return cart;
    }

    @PostMapping("/update")
    public Cart updateCart(@RequestBody Cart cart) {
        this.cartService.updateCart(cart);
        return cart;
    }

    @GetMapping("/delete/{id}")
    public void deleteCart(@PathVariable int id) {
        this.cartService.deleteCart(id);
    }
}
