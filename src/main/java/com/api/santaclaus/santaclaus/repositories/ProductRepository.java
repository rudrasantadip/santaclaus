package com.api.santaclaus.santaclaus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.santaclaus.santaclaus.models.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

}
