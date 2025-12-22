package com.api.santaclaus.santaclaus.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.santaclaus.santaclaus.dto.ProductRequest;
import com.api.santaclaus.santaclaus.models.Product;
import com.api.santaclaus.santaclaus.services.FileUploadService;
import com.api.santaclaus.santaclaus.services.ProductService;
import com.api.santaclaus.santaclaus.services.utils.UrlMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final FileUploadService fileUploadService;
    private final ProductService productService;

    @Value("${project.image}")
    public String uploadPath;

    private final ObjectMapper mapper;

    // @PostMapping("/add")
    // public ResponseEntity<Product> addProduct(@RequestBody Product p) throws
    // Exception{
    // return ResponseEntity.ok(productService.addProduct(p));
    // }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> addProduct(
            @RequestParam("product") String p,
            @RequestParam("image") MultipartFile file) throws Exception {

        return ResponseEntity.ok(
                UrlMapper.withImageUrl(Product::getProductImage,
                        Product::setProductImage, "/products/view/")
                        .apply(productService.addProduct(mapper.readValue(p, ProductRequest.class), file)));

    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("image") MultipartFile file) {
        try {
            String response = fileUploadService.doUpload(file);
            return ResponseEntity.ok(
                    ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("products/view/")
                            .path(response)
                            .toUriString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("view/{image}")
    public void viewImage(@PathVariable String image, HttpServletResponse response) {
        try {
            InputStream fileStream = fileUploadService.getResource(uploadPath, image);
            Path path = Paths.get(uploadPath + image);

            String contentType = Files.probeContentType(path);
            response.setContentType(MediaType.parseMediaType(contentType).toString());
            StreamUtils.copy(fileStream, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Product> getProductByName(@RequestParam("product") String productName) {

        Product exists = productService.findByName(productName);
        return ResponseEntity
                .ok(UrlMapper.withImageUrl(Product::getProductImage, Product::setProductImage, "/products/view/")
                        .apply(exists));
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(
                productService.findAllProducts().stream()
                        .map(p -> UrlMapper
                                .withImageUrl(Product::getProductImage, Product::setProductImage, "/products/view/")
                                .apply(p))
                        .toList());
    }
}
