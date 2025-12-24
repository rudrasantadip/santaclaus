package com.api.santaclaus.santaclaus.services;

import java.util.List;
import java.util.function.BiFunction;

import javax.swing.plaf.multi.MultiPanelUI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.santaclaus.santaclaus.models.Product;
import com.api.santaclaus.santaclaus.models.SampleImage;
import com.api.santaclaus.santaclaus.repositories.SampleImageRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SampleImageService {
    private final SampleImageRepo sImageRepo;
    private final FileUploadService fileUploadService;
    private final ProductService productService;

    @Value("${project.image}")
    private String PATH;

    public Product addSampleImagesToProduct(String productName, List<MultipartFile> files) throws Exception {

        Product p = productService.findByName(productName);
        fileUploadService.uploadFiles()
                .apply(files, PATH)
                .stream()
                .map(imageUrl -> toSampleImage.apply(imageUrl, p))
                .forEach(p::addSampleImage);
        return productService.addProduct(p);
    }

    BiFunction<String, Product, SampleImage> toSampleImage = (imageUrl, product) -> {
        return SampleImage.builder()
                .imageUrl(imageUrl)
                .product(product)
                .build();
    };

}
