package com.api.santaclaus.santaclaus.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    public String uploadFile(String path, MultipartFile file) throws IOException{
        String filename = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();

        filename = randomId.concat(filename.substring(filename.lastIndexOf(".")));
        String uploadPath = path+File.separator+filename;

        File fileToUpload = new File(path);

        if(!fileToUpload.exists()){
            fileToUpload.mkdirs();
        }
        Files.copy(file.getInputStream(), Paths.get(uploadPath));
        return filename;
    }

     public InputStream getResource(String path, String fileName) throws FileNotFoundException
    {
        String filePath = path+File.separator+fileName;
        InputStream file = new FileInputStream(filePath);
        return file;
    }
}
