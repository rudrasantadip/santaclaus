package com.api.santaclaus.santaclaus.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.santaclaus.santaclaus.models.UserModel;
import com.api.santaclaus.santaclaus.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService 
{
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public Boolean saveUser(UserModel userModel) throws Exception{
        if(userRepository.findByUsername(userModel.getUsername()).isPresent()){
            throw new Exception("user already exists");
        }
        userModel.setPassword(new BCryptPasswordEncoder().encode(userModel.getPassword()));
        userRepository.save(userModel);
        return true;
    }


    public String verify(UserModel userModel){
       try{
        Authentication authentication = 
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(userModel);
        }
       }catch(Exception e){
        System.out.println(e.getMessage());
        } 
        return "";
}

public UserModel findUserByName(String username) throws Exception{
    return userRepository.findByUsername(username)
    .orElseThrow(()-> new Exception("User not found"));
}
}
