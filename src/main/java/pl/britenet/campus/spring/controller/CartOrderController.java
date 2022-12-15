package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.britenet.campus.spring.service.AuthService;
import pl.britenet.campusapiapp.model.CartProduct;
import pl.britenet.campusapiapp.model.Order;
import pl.britenet.campusapiapp.model.OrderProduct;
import pl.britenet.campusapiapp.model.builder.OrderBuilder;
import pl.britenet.campusapiapp.model.builder.OrderProductBuilder;
import pl.britenet.campusapiapp.service.CartProductService;
import pl.britenet.campusapiapp.service.OrderProductService;
import pl.britenet.campusapiapp.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cartorder")
public class CartOrderController {

    private final CartProductService cartProductService;
    private final OrderProductService orderProductService;
    private final AuthService authService;
    private final OrderService orderService;

    @Autowired
    public CartOrderController(CartProductService cartProductService,
                               OrderProductService orderProductService,
                               AuthService authService,
                               OrderService orderService) {
        this.cartProductService = cartProductService;
        this.orderProductService = orderProductService;
        this.authService = authService;
        this.orderService = orderService;
    }

    @PostMapping
    public void orderProductsFromCart(@RequestHeader("Authorization") String token,
                                      @RequestHeader("Address") String address) {
        List<CartProduct> cartProductList;
        try {
            cartProductList = this.cartProductService.getByUserId(this.authService.getUserId(token));
            for(CartProduct cartProduct : cartProductList) {
                orderService.insertOrder(new OrderBuilder(new Order())
                                .setUserId(cartProduct.getCart().getUserId())
                                .setOrderDate(cartProduct.getCart().getOrderDate())
                                .setTotalPrice(cartProduct.getCart().getTotalPrice())
                                .setShippingAddress(address)
                                .setDiscount(cartProduct.getCart().getDiscount())
                                .setStatus("Paid")
                                .getOrder());
                Order newOrder = orderService.getNewestOrderItem();
                orderProductService.insertOrderProduct(
                        new OrderProductBuilder(new OrderProduct())
                                .setProductId(cartProduct.getProduct().getId())
                                .setQuantity(1)
                                .setProductPrice(cartProduct.getProduct().getPrice())
                                .setOrder(newOrder)
                                .setProduct(cartProduct.getProduct())
                                .getOrderProduct()
                );
                cartProductService.deleteCartProduct(cartProduct.getCartId(), cartProduct.getProductId());
            }
        } catch (NullPointerException e) {
            System.out.println("User has empty cart");
        }
    }
}