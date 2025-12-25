package com.api.santaclaus.santaclaus.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDto {
    private Long cartId;
    private List<CartItemDto> cartItems;
    private UserDto user;
}
