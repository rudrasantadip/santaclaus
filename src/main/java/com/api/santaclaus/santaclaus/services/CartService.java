package com.api.santaclaus.santaclaus.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.santaclaus.santaclaus.dto.CartDto;
import com.api.santaclaus.santaclaus.models.Cart;
import com.api.santaclaus.santaclaus.models.CartItem;
import com.api.santaclaus.santaclaus.models.Product;
import com.api.santaclaus.santaclaus.models.UserModel;
import com.api.santaclaus.santaclaus.repositories.CartRepo;
import com.api.santaclaus.santaclaus.services.utils.DtoConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepo cartRepo;
    private final AuthService authService;

    public Cart addToCart(String username, List<Product> products) throws Exception {
        UserModel u = authService.findUserByName(username);

        Cart c = cartRepo.findByUser(u).orElse(new Cart());
        c.setUser(u);

        List<CartItem> cartItems = products.stream()
                .map((product) -> {
                    return new CartItem(product);
                }).toList();

        cartItems.forEach(item -> {
            item.setCart(c);
            c.addItemToCart(item);
        });

        return cartRepo.save(c);
    }

    public Cart findCartByUser(UserModel userModel) throws Exception {
        return cartRepo.findByUser(userModel)
                .orElseThrow(() -> new Exception("User not found"));
    }

    public Cart findCartByUser(String username) throws Exception{
        return cartRepo.findByUser(authService.findUserByName(username))
        .orElseThrow(()->new Exception("Üser not found"));
       
    }

    public CartDto viewCart(UserModel userModel) throws Exception {
        return DtoConverter.map(
                // arg1
                findCartByUser(userModel)
                // arg2
                , DtoConverter.toCartDto);
    }
}
