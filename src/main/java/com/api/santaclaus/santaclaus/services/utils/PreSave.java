package com.api.santaclaus.santaclaus.services.utils;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PreSave {
    public static <T> Consumer<T> setImageOnProduct(
        MultipartFile file,
        Function<MultipartFile,String> uploader,
        BiConsumer<T,String> urlSetter
    ){
       return entity->{
        if(file!=null && !file.isEmpty()){
            String url = uploader.apply(file);
            urlSetter.accept(entity, url);
        }
       }; 
    }

    public static <T> Consumer<T> setImageOnProduct(
        MultipartFile file,
        String uploadPath,
        BiFunction<MultipartFile,String,String> uploader,
        BiConsumer<T,String> urlSetter
    ){
       return entity->{
        if(file!=null && !file.isEmpty()){
            String url = uploader.apply(file,uploadPath);
            urlSetter.accept(entity, url);
        }
       }; 
    }
}
