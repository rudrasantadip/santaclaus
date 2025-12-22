package com.api.santaclaus.santaclaus.services;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.santaclaus.santaclaus.dto.ProductRequest;
import com.api.santaclaus.santaclaus.models.Product;
import com.api.santaclaus.santaclaus.repositories.ProductRepository;
import com.api.santaclaus.santaclaus.services.utils.PreSave;
import com.api.santaclaus.santaclaus.services.utils.ServiceExecutor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final FileUploadService fileUploadService;
    private final CategoryService categoryService;

    private static Consumer<Product> isNotNull() {
        return p -> {
            if (p == null) {
                throw new IllegalArgumentException("Product cannot be empty");
            }
        };
    }

    private static Consumer<Product> rejectDublicates(ProductRepository productRepository) {
        return p -> {
            if (productRepository.findByProductName(p.getProductName()).isPresent()) {
                throw new RuntimeException("Product Already exists");
            }
        };
    }

    public Product addProduct(Product p) throws Exception {
        return ServiceExecutor.save(productRepository, p,
                product -> Objects.requireNonNull(product));
    }

    public Product addProduct(ProductRequest productRequest, MultipartFile file) throws Exception, IOException {

        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .build();

        return ServiceExecutor
                .save(productRepository,
                        product,
                        isNotNull(),
                        p -> {
                            if (productRepository.findByProductName(p.getProductName()).isPresent()) {
                                throw new RuntimeException("Product Already exists");
                            }
                        },
                        p -> p.setCategory(
                                categoryService.findByName(productRequest.getCategoryName())),

                        PreSave
                                .<Product>setImageOnProduct(
                                        file,
                                        fileUploadService.uploadFile(),
                                        Product::setProductImage));
    }

    public Product findByName(String name) {
        return productRepository.findByProductName(name)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> findAllProducts(){
            return productRepository.findAll();
    }

}
