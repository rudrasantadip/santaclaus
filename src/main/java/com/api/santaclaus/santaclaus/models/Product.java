package com.api.santaclaus.santaclaus.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private String description;
    private String productImage;
    private Double price;
    private Integer discountPercent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SampleImage> sampleImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product",orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CartItem> cartItems; 

    public void addSampleImage(SampleImage sampleImage) {
        sampleImages.add(sampleImage);
        sampleImage.setProduct(this);
    }
}
