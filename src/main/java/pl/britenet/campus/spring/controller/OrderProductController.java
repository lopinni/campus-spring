package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campusapiapp.model.OrderProduct;
import pl.britenet.campusapiapp.service.OrderProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orderproduct")
public class OrderProductController {

    private final OrderProductService orderProductService;

    @Autowired
    public OrderProductController(OrderProductService OrderProductService) {
        this.orderProductService = OrderProductService;
    }

    @GetMapping
    public List<OrderProduct> getAll() {
        return this.orderProductService.getAll();
    }

    @GetMapping("/{orderId}/{productId}")
    public OrderProduct getOrderProduct(
            @PathVariable int orderId, @PathVariable int productId) {
        return this.orderProductService.getOrderProduct(orderId, productId);
    }

    @PostMapping
    public OrderProduct insertOrderProduct(
            @RequestBody OrderProduct orderProduct) {
        this.orderProductService.insertOrderProduct(orderProduct);
        return orderProduct;
    }

    @PutMapping("/{orderId}/{productId}")
    public OrderProduct updateOrderProduct(
            @RequestBody OrderProduct OrderProduct, @PathVariable int orderId, @PathVariable int productId) {
        this.orderProductService.updateOrderProduct(orderId, productId, OrderProduct);
        return OrderProduct;
    }

    @DeleteMapping("/{orderId}/{productId}")
    public void deleteOrderProduct(
            @PathVariable int orderId, @PathVariable int productId) {
        this.orderProductService.deleteOrderProduct(orderId, productId);
    }
}
