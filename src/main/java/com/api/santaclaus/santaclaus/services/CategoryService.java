package com.api.santaclaus.santaclaus.services;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.api.santaclaus.santaclaus.models.Category;
import com.api.santaclaus.santaclaus.repositories.CategoryRepository;
import com.api.santaclaus.santaclaus.services.utils.ServiceExecutor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public static Consumer<Category> rejectDublicates(CategoryRepository categoryRepository) {
        return category->{
            if(categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName()).isPresent()){
                throw new RuntimeException("Category already exist");
            }
        };
    }

    public Category addCategory(Category category){
        return ServiceExecutor.save
        (
            categoryRepository, 
            category,
            rejectDublicates(categoryRepository)
        );
    }

    public Category findByName(String categoryName){
        return categoryRepository.findByCategoryNameIgnoreCase(categoryName)
        .orElseThrow(()-> new RuntimeException("Category not found"));
    }

}
