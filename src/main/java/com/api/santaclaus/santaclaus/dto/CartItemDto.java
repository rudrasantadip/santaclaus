package com.api.santaclaus.santaclaus.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDto {
    private Long cartItemId;
    private ProductDto product;
}
