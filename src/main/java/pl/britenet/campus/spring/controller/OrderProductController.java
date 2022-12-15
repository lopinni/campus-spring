package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campus.spring.service.AuthService;
import pl.britenet.campusapiapp.model.OrderProduct;
import pl.britenet.campusapiapp.service.OrderProductService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orderproduct")
public class OrderProductController {

    private final OrderProductService orderProductService;
    private final AuthService authService;

    @Autowired
    public OrderProductController(OrderProductService OrderProductService, AuthService authService) {
        this.orderProductService = OrderProductService;
        this.authService = authService;
    }

    @GetMapping
    public List<OrderProduct> getAll() {
        return this.orderProductService.getAll();
    }

    @GetMapping("/{orderId}/{productId}")
    public OrderProduct getOrderProduct(@PathVariable int orderId, @PathVariable int productId) {
        return this.orderProductService.getOrderProduct(orderId, productId);
    }

    @GetMapping("/token")
    public List<OrderProduct> getOrders(@RequestHeader("Authorization") String token) {
        int userId;
        try {
            userId = this.authService.getUserId(token);
            return this.orderProductService.getByUserId(userId);
        } catch (NullPointerException e) {
            System.out.println("User does not have order history");
            return new ArrayList<>();
        }
    }

    @PostMapping
    public OrderProduct insertOrderProduct(@RequestBody OrderProduct orderProduct) {
        this.orderProductService.insertOrderProduct(orderProduct);
        return orderProduct;
    }

    @GetMapping("/update")
    public OrderProduct updateOrderProduct(@RequestBody OrderProduct OrderProduct,
                                           @RequestHeader("Order-Id") int orderId,
                                           @RequestHeader("Product-Id") int productId) {
        this.orderProductService.updateOrderProduct(orderId, productId, OrderProduct);
        return OrderProduct;
    }

    @GetMapping("/delete")
    public void deleteOrderProduct(@RequestHeader("Order-Id") int orderId,
                                   @RequestHeader("Product-Id") int productId) {
        this.orderProductService.deleteOrderProduct(orderId, productId);
    }
}
