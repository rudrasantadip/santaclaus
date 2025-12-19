package com.api.santaclaus.santaclaus.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_table")
public class Product 
{
    private Long productId;
    private String productName;
    private String description;
    private String category;
    private String productImage;
}
