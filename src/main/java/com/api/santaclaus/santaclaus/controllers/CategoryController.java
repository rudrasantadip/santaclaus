package com.api.santaclaus.santaclaus.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.santaclaus.santaclaus.models.Category;
import com.api.santaclaus.santaclaus.services.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController 
{
    private final CategoryService categoryService;

    @PostMapping("add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.addCategory(category));
    }
}
