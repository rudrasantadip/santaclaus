package com.api.santaclaus.santaclaus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.santaclaus.santaclaus.dto.CartDto;
import com.api.santaclaus.santaclaus.dto.UserDto;
import com.api.santaclaus.santaclaus.services.CartService;
import com.api.santaclaus.santaclaus.services.utils.DtoConverter;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class PaymentController 
{
    private final CartService cartService;

    @PostMapping("/create/order")
    public ResponseEntity<CartDto> createOrder(@RequestBody UserDto user) throws Exception{
        return ResponseEntity.ok(DtoConverter.map(cartService.findCartByUser(user.getUsername()), DtoConverter.toCartDto));
        
       
    }
}
