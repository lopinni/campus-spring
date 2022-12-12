package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campusapiapp.model.ProductCategory;
import pl.britenet.campusapiapp.service.ProductCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productcategory")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService ProductCategoryService) {
        this.productCategoryService = ProductCategoryService;
    }

    @GetMapping
    public List<ProductCategory> getAll() {
        return this.productCategoryService.getAll();
    }

    @GetMapping("/{productId}/{categoryId}")
    public ProductCategory getProductCategory(
            @PathVariable int productId, @PathVariable int categoryId) {
        return this.productCategoryService.getProductCategory(productId, categoryId);
    }

    @PostMapping
    public ProductCategory insertProductCategory(
            @RequestBody ProductCategory productCategory) {
        this.productCategoryService.insertProductCategory(productCategory);
        return productCategory;
    }

    @PutMapping("/{productId}/{categoryId}")
    public ProductCategory updateProductCategory(
            @RequestBody ProductCategory productCategory, @PathVariable int productId, @PathVariable int categoryId) {
        this.productCategoryService.updateProductCategory(productId, categoryId, productCategory);
        return productCategory;
    }

    @DeleteMapping("/{productId}/{categoryId}")
    public void deleteProductCategory(
            @PathVariable int productId, @PathVariable int categoryId) {
        this.productCategoryService.deleteProductCategory(productId, categoryId);
    }
}
