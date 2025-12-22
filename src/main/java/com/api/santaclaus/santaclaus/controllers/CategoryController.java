package com.api.santaclaus.santaclaus.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.santaclaus.santaclaus.models.Category;
import com.api.santaclaus.santaclaus.models.Product;
import com.api.santaclaus.santaclaus.services.CategoryService;
import com.api.santaclaus.santaclaus.services.utils.UrlMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.addCategory(category));
    }

    @GetMapping
    public ResponseEntity<List<String>> findAllCategories() {
        return ResponseEntity.ok(categoryService.findAllCategoryNames());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> findAll() {               
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @GetMapping("/all/products")
    public ResponseEntity<List<Product>> findAllProductsByCategory(@RequestParam("category") String name) {
        return ResponseEntity.ok(
                categoryService.findProductsByCategoryName(name).stream()
                        .map(p -> UrlMapper
                                .withImageUrl(Product::getProductImage, Product::setProductImage, "/products/view")
                                .apply(p))
                        .toList());

    }
}
