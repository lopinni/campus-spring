package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campus.spring.service.AuthService;
import pl.britenet.campusapiapp.model.Cart;
import pl.britenet.campusapiapp.model.CartProduct;
import pl.britenet.campusapiapp.service.CartProductService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cartproduct")
public class CartProductController {

    private final CartProductService cartProductService;
    private final AuthService authService;

    @Autowired
    public CartProductController(CartProductService cartProductService, AuthService authService) {
        this.cartProductService = cartProductService;
        this.authService = authService;
    }

    @GetMapping
    public List<CartProduct> getAll() {
        return this.cartProductService.getAll();
    }

    @GetMapping("/{cartId}/{productId}")
    public CartProduct getCartProduct(
            @PathVariable int cartId, @PathVariable int productId) {
        return this.cartProductService.getCartProduct(cartId, productId);
    }

    @GetMapping("/token")
    public List<CartProduct> getCart(@RequestHeader("Authorization") String token) {
        int userId;
        try {
            userId = this.authService.getUserId(token);
            return this.cartProductService.getByUserId(userId);
        } catch (NullPointerException e) {
            System.out.println("User has empty cart");
            return new ArrayList<>();
        }
    }

    @PostMapping
    public CartProduct insertCartProduct(
            @RequestBody CartProduct cartProduct) {
        this.cartProductService.insertCartProduct(cartProduct);
        return cartProduct;
    }

    @PutMapping("/{cartId}/{productId}")
    public CartProduct updateCartProduct(
            @RequestBody CartProduct CartProduct, @PathVariable int cartId, @PathVariable int productId) {
        this.cartProductService.updateCartProduct(cartId, productId, CartProduct);
        return CartProduct;
    }

    @DeleteMapping("/{cartId}/{productId}")
    public void deleteCartProduct(
            @PathVariable int cartId, @PathVariable int productId) {
        this.cartProductService.deleteCartProduct(cartId, productId);
    }
}
