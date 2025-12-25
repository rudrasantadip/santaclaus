package com.api.santaclaus.santaclaus.services.utils;

import java.util.function.Function;

import com.api.santaclaus.santaclaus.dto.CartDto;
import com.api.santaclaus.santaclaus.dto.CartItemDto;
import com.api.santaclaus.santaclaus.dto.ProductDto;
import com.api.santaclaus.santaclaus.dto.UserDto;
import com.api.santaclaus.santaclaus.models.Cart;
import com.api.santaclaus.santaclaus.models.CartItem;
import com.api.santaclaus.santaclaus.models.Product;
import com.api.santaclaus.santaclaus.models.UserModel;

public class DtoConverter {
    public static Function<Product, ProductDto> toProductDto = product -> ProductDto.builder()
            .productId(product.getProductId())
            .productName(product.getProductName())
            .description(product.getDescription())
            .productImage(product.getProductImage())
            .price(product.getPrice())
            .discountPercent(product.getDiscountPercent())
            .build();

    public static Function<CartItem, CartItemDto> toCartItemDto = cartItem -> CartItemDto.builder()
            .cartItemId(cartItem.getCartItemId())
            .product(toProductDto
                    .apply(UrlMapper.withImageUrl(Product::getProductImage, Product::setProductImage, "/products/view/")
                            .apply(cartItem.getProduct())))
            .build();

    public static Function<UserModel, UserDto> toUserDto = user -> UserDto.builder()
            .userId(user.getUserId())
            .username(user.getUsername())
            .build();

    public static Function<Cart, CartDto> toCartDto = cart -> CartDto.builder()
            .cartId(cart.getCartId())

            .cartItems(cart.getCartItems().stream()
                    //this map is from the stream api
                    .map(toCartItemDto)
                    .toList())

                    //this map is user defined
            .user(map(cart.getUser(), toUserDto))
            .build();

    

    public static <T, R> R map(T input, Function<T, R> mapper) {
        return mapper.apply(input);
    }
}
