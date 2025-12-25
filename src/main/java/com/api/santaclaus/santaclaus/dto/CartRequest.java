package com.api.santaclaus.santaclaus.dto;

import java.util.List;

import com.api.santaclaus.santaclaus.models.Product;
import com.api.santaclaus.santaclaus.models.UserModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
    private String username;
    private List<Product> products;
}
