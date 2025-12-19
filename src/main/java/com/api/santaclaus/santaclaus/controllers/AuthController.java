package com.api.santaclaus.santaclaus.controllers;


import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.santaclaus.santaclaus.models.ApiResponse;
import com.api.santaclaus.santaclaus.models.UserModel;
import com.api.santaclaus.santaclaus.services.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController 
{
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserModel userModel) throws Exception{
        Boolean isSaved = authService.saveUser(userModel);
        ApiResponse response = ApiResponse.builder()
        .response("success")
        .status(isSaved)
        .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserModel userModel, HttpServletResponse response){

        String token = authService.verify(userModel);

        ApiResponse tokenResponse =ApiResponse.builder()
        .response("success")
        .status(true)
        .build();

        if(token.equals("")){
            tokenResponse.setResponse("failure");
            tokenResponse.setStatus(false);
        }

        //create an http only cookie
        ResponseCookie cookie = ResponseCookie.from("access_token",token)
        .httpOnly(true)
        .secure(false)
        .sameSite("Lax")
        .path("/")
        .maxAge(Duration.ofHours(1))
        .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(tokenResponse);
    }
}
