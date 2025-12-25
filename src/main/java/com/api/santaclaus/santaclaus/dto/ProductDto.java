package com.api.santaclaus.santaclaus.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDto {
    private Long productId;
    private String productName;
    private String description;
    private String productImage;
    private Double price;
    private Integer discountPercent;
}
