package com.api.santaclaus.santaclaus.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.santaclaus.santaclaus.models.Product;
import com.api.santaclaus.santaclaus.services.SampleImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sample/images")
@RequiredArgsConstructor
public class SampleImageController {

    private final SampleImageService sampleImageService;

    @PostMapping("add")
    public ResponseEntity<Product> addSampleImages(
        @RequestParam("productName")String productName,
        @RequestParam("files") List<MultipartFile> files
    ) throws Exception{
        return ResponseEntity.ok(sampleImageService.addSampleImagesToProduct(productName, files));
    }
}
