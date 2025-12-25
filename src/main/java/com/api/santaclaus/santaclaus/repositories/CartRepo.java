package com.api.santaclaus.santaclaus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.santaclaus.santaclaus.models.Cart;
import com.api.santaclaus.santaclaus.models.UserModel;


public interface CartRepo extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUser(UserModel user);
}
