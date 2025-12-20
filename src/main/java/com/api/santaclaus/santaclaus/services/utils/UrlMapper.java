package com.api.santaclaus.santaclaus.services.utils;

import java.util.function.BiConsumer;
import java.util.function.Function;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.santaclaus.santaclaus.models.Product;

@Component
public class UrlMapper {
    public static Function<Product,Product> updateProductImage(){
        return product->{
            if(product.getProductImage()!=null){
                String serveUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/products/view/")
                .path(product.getProductName())
                .toUriString();
                product.setProductImage(serveUrl);
            }
            return product;
        };
    }

    public static <T> Function<T,T> withImageUrl(
        Function<T,String> imageGetter, 
        BiConsumer<T,String> imageSetter,
        String viewPath
    ){
        return entity->{
            String image = imageGetter.apply(entity);
            if (image != null && !image.isBlank()) {
                String fullUrl =
                        ServletUriComponentsBuilder
                                .fromCurrentContextPath()
                                .path(viewPath)
                                .path(image)
                                .toUriString();

                imageSetter.accept(entity, fullUrl);
            }
            return entity;
        };
    }
}
