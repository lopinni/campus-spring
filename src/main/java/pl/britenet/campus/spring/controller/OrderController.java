package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campus.spring.service.AuthService;
import pl.britenet.campusapiapp.model.Order;
import pl.britenet.campusapiapp.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;
    private final AuthService authService;

    @Autowired
    public OrderController(OrderService orderService, AuthService authService) {
        this.orderService = orderService;
        this.authService = authService;
    }

    @GetMapping
    public List<Order> getAll() {
        return this.orderService.getAll();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable int id) {
        return this.orderService.getOrder(id);
    }

    @GetMapping("/token")
    public List<Order> getOrders(@RequestHeader("Authorization") String token) {
        Optional<Integer> userId = this.authService.getUserId(token).describeConstable();
        if (userId.isPresent()) {
            return this.orderService.getByUserId(userId.get());
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping
    public Order insertOrder(@RequestBody Order order) {
        this.orderService.insertOrder(order);
        return order;
    }

    @PutMapping
    public Order updateOrder(@RequestBody Order order) {
        this.orderService.updateOrder(order);
        return order;
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable int id) {
        this.orderService.deleteOrder(id);
    }
}
