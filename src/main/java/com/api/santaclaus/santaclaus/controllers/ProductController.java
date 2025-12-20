package com.api.santaclaus.santaclaus.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.santaclaus.santaclaus.services.FileUploadService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController 
{

    private final FileUploadService fileUploadService;
    
    @Value("${project.image}")
    public String uploadPath;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("image") MultipartFile file){
        try{
            String response = fileUploadService.uploadFile(uploadPath, file);
            return ResponseEntity.ok(
                ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(uploadPath)
                .path(response)
                .toUriString()
            );
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("view/{image}")
    public void viewImage(@PathVariable String image, HttpServletResponse response){
         try 
        {
            InputStream fileStream = fileUploadService.getResource(uploadPath, image);
            Path path = Paths.get(uploadPath+image);

            String contentType = Files.probeContentType(path);
            response.setContentType(MediaType.parseMediaType(contentType).toString());
            StreamUtils.copy(fileStream, response.getOutputStream());
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
