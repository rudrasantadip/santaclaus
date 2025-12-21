package com.api.santaclaus.santaclaus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.santaclaus.santaclaus.models.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{
    Optional<Category> findByCategoryNameIgnoreCase(String name);
}
