package com.api.santaclaus.santaclaus.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.api.santaclaus.santaclaus.models.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    private final String secretKey="d6a997ef520fcae6297de6544538b10e";

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(UserModel userModel){
        Map<String,Object> claims = new HashMap<>();

        claims.put("role", userModel.getRole().name());

        return Jwts.builder()
        .claims().add(claims)
        .subject(userModel.getUsername())
        .issuer("rudrasantadip")
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 30*60*1000)).and()
        .signWith(getSecretKey())
        .compact();
    }

    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> resolver){
        return resolver.apply(extractClaims(token));
    }

    private Claims extractClaims(String token){
        return Jwts.parser()
        .verifyWith(getSecretKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}


