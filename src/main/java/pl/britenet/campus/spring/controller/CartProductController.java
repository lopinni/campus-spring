package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campus.spring.service.AuthService;
import pl.britenet.campusapiapp.model.Cart;
import pl.britenet.campusapiapp.model.CartProduct;
import pl.britenet.campusapiapp.model.Product;
import pl.britenet.campusapiapp.model.builder.CartBuilder;
import pl.britenet.campusapiapp.model.builder.CartProductBuilder;
import pl.britenet.campusapiapp.service.CartProductService;
import pl.britenet.campusapiapp.service.CartService;
import pl.britenet.campusapiapp.service.ProductService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cartproduct")
public class CartProductController {

    private final CartProductService cartProductService;
    private final AuthService authService;
    private final CartService cartService;
    private final ProductService productService;

    @Autowired
    public CartProductController(CartProductService cartProductService,
                                 AuthService authService,
                                 CartService cartService,
                                 ProductService productService) {
        this.cartProductService = cartProductService;
        this.authService = authService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public List<CartProduct> getAll() {
        return this.cartProductService.getAll();
    }

    @GetMapping("/{cartId}/{productId}")
    public CartProduct getCartProduct(@PathVariable int cartId, @PathVariable int productId) {
        return this.cartProductService.getCartProduct(cartId, productId);
    }

    @GetMapping("/token")
    public List<CartProduct> getCart(@RequestHeader("Authorization") String token) {
        try {
            return this.cartProductService.getByUserId(this.authService.getUserId(token));
        } catch (NullPointerException e) {
            System.out.println("User has empty cart");
            return new ArrayList<>();
        }
    }

    @PostMapping
    public CartProduct insertCartProduct(@RequestBody CartProduct cartProduct) {
        this.cartProductService.insertCartProduct(cartProduct);
        return cartProduct;
    }

    @GetMapping("/update")
    public CartProduct updateCartProduct(@RequestBody CartProduct CartProduct,
                                         @RequestHeader("Cart-Id") int cartId,
                                         @RequestHeader("Product-Id") int productId) {
        this.cartProductService.updateCartProduct(cartId, productId, CartProduct);
        return CartProduct;
    }

    @GetMapping("/delete")
    public void deleteCartProduct(@RequestHeader("Cart-Id") int cartId,
                                  @RequestHeader("Product-Id") int productId) {
        this.cartProductService.deleteCartProduct(cartId, productId);
    }

    @PostMapping("/add")
    public void addProductToCart(@RequestHeader("Authorization") String token,
                                 @RequestHeader("ProductId") int productId) {
        Product product = this.productService.getProduct(productId);
        this.cartService.insertCart(new CartBuilder(new Cart())
                .setUserId(this.authService.getUserId(token))
                .setOrderDate(new Date())
                .setTotalPrice(product.getPrice())
                .setDiscount(0)
                .getCart());
        Cart cart = this.cartService.getNewestCartItem();
        this.cartProductService.insertCartProduct(new CartProductBuilder(new CartProduct())
                        .setCartId(cart.getId())
                        .setProductId(product.getId())
                        .setQuantity(1)
                        .setProductPrice(product.getPrice())
                        .setCart(cart)
                        .setProduct(product)
                        .getCartProduct());
    }
}