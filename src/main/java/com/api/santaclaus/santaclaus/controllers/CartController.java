
package com.api.santaclaus.santaclaus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.santaclaus.santaclaus.dto.CartDto;
import com.api.santaclaus.santaclaus.dto.CartRequest;
import com.api.santaclaus.santaclaus.models.Cart;
import com.api.santaclaus.santaclaus.models.UserModel;
import com.api.santaclaus.santaclaus.services.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController 
{
    private final CartService cartService;

    @PostMapping("add")
    public ResponseEntity<Cart> addToCart(@RequestBody CartRequest cartRequest) throws Exception{
        return ResponseEntity.ok(
        cartService.addToCart(cartRequest.getUsername(), cartRequest.getProducts()));
    }

    @GetMapping("view")
    public ResponseEntity<CartDto> viewCart(@RequestBody UserModel userModel) throws Exception{
        return ResponseEntity.ok(cartService.viewCart(userModel));
    }
}
