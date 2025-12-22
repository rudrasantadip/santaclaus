package com.api.santaclaus.santaclaus.services;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.api.santaclaus.santaclaus.models.Category;
import com.api.santaclaus.santaclaus.models.Product;
import com.api.santaclaus.santaclaus.repositories.CategoryRepository;
import com.api.santaclaus.santaclaus.services.utils.ServiceExecutor;
import com.api.santaclaus.santaclaus.services.utils.UrlMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public static Consumer<Category> rejectDublicates(CategoryRepository categoryRepository) {
        return category -> {
            if (categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName()).isPresent()) {
                throw new RuntimeException("Category already exist");
            }
        };
    }

    public Category addCategory(Category category) {
        return ServiceExecutor.save(
                categoryRepository,
                category,
                rejectDublicates(categoryRepository));
    }

    public Category findByName(String categoryName) {
        return categoryRepository.findByCategoryNameIgnoreCase(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public List<String> findAllCategoryNames() {
        return categoryRepository.findAllCategoryNames();
    }

    public List<Category> findAllCategories() {

        return categoryRepository.findAll().stream()
                .peek(category -> category.getProducts()
                .forEach
                (p -> UrlMapper.withImageUrl(
                    Product::getProductImage,
                    Product::setProductImage, 
                    "/products/view/")
                    .apply(p)
                ))
                .toList();
    }

    public List<Product> findProductsByCategoryName(String name) {
        return categoryRepository.findByCategoryNameIgnoreCase(name)
                .get().getProducts();
    }

}
