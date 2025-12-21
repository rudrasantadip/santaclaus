package com.api.santaclaus.santaclaus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.santaclaus.santaclaus.models.Product;



public interface ProductRepository extends JpaRepository<Product,Long>{
    Optional<Product>  findByProductName(String productName);
}
