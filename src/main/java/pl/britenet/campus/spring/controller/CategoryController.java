package pl.britenet.campus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.britenet.campusapiapp.model.Category;
import pl.britenet.campusapiapp.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService CategoryService) {
        this.categoryService = CategoryService;
    }

    @GetMapping
    public List<Category> getAll() {
        return this.categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable int id) {
        return this.categoryService.getCategory(id);
    }

    @PostMapping
    public Category insertCategory(@RequestBody Category category) {
        this.categoryService.insertCategory(category);
        return category;
    }

    @PostMapping("/update")
    public Category updateCategory(@RequestBody Category category) {
        this.categoryService.updateCategory(category);
        return category;
    }

    @GetMapping("/delete/{id}")
    public void deleteCategory(@PathVariable int id) {
        this.categoryService.deleteCategory(id);
    }
}
