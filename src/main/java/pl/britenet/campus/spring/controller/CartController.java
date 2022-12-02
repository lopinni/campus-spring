package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campusapiapp.model.Cart;
import pl.britenet.campusapiapp.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
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
