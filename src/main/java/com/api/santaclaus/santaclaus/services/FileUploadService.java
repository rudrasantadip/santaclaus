package com.api.santaclaus.santaclaus.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    @Value("${project.image}")
    private String PATH;

    private static final Set<String> ALLOWED_FILE_TYPES= Set.of(
        "image/png","image/jpeg","image/jpg"
    );

    private Predicate<MultipartFile> isAllowedImage = file -> file.getContentType()!=null && ALLOWED_FILE_TYPES.contains(file.getContentType());

    public String doUpload(MultipartFile file) throws IOException{

        if(!isAllowedImage.test(file)){
            throw new IOException("Only PNG and JPG and JPEG images are allowed");
        }

        String extension = file.getOriginalFilename()
        .substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString()+extension;

        Path uploadDir = Paths.get(PATH);
        Files.createDirectories(uploadDir);

        Path target = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), target);

        return fileName;
    }

     public InputStream getResource(String path, String fileName) throws FileNotFoundException
    {
        String filePath = path+File.separator+fileName;
        InputStream file = new FileInputStream(filePath);
        return file;
    }

    public Function<MultipartFile,String> uploadFile(){
        return file->{
            try {
                return doUpload(file);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("File upload failed", e);
            }
        };
    }
}
