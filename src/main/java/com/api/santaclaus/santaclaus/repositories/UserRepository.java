package com.api.santaclaus.santaclaus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.santaclaus.santaclaus.models.UserModel;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserModel,Long> {
    Optional<UserModel>  findByUsername(String username);
}
