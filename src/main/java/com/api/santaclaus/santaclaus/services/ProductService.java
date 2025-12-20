package com.api.santaclaus.santaclaus.services;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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


    private static Consumer<Product>isNotNull(){
        return  p->{
            if(p==null){
                throw new IllegalArgumentException("Product cannot be empty");
            }
        };
    }

    public Product addProduct(Product p) throws Exception{
        return ServiceExecutor.save(productRepository,p,
             product->Objects.requireNonNull(product));
    }

    public Product addProduct(Product product, MultipartFile file) throws Exception,IOException{
        return ServiceExecutor
        .save(productRepository, 
            product,
            isNotNull(),
            PreSave
            .<Product>setImageOnProduct(file, 
                fileUploadService.uploadFile(), 
                Product::setProductImage));
    }
}
