package com.api.santaclaus.santaclaus.controllers;



import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.santaclaus.santaclaus.models.UserModel;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("users")
public class MainController 
{

    @GetMapping("protected")
    public String protectedUrl(){
        return "this is protected url";
    }

    @GetMapping("/restore/session")
    public ResponseEntity<UserModel> restoreSession(Authentication authentication){
        if(authentication==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(
            UserModel.builder()
            .username(authentication.getName())
            .build());
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(HttpServletResponse response){
        ResponseCookie cookie = ResponseCookie.from("access_token","")
        .httpOnly(true)
        .secure(false)
        .path("/")
        .maxAge(0)
        .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(null);
    }
}
