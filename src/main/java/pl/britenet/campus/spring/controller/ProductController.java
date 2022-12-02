package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campusapiapp.model.Product;
import pl.britenet.campusapiapp.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return this.productService.getAll();
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable int id) {
        return this.productService.getProduct(id);
    }

    @PostMapping
    public Product insertProduct(@RequestBody Product product) {
        this.productService.insertProduct(product);
        return product;
    }
}
