package com.api.santaclaus.santaclaus.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.api.santaclaus.santaclaus.models.CustomUserDetails;
import com.api.santaclaus.santaclaus.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
        return userRepository.findByUsername(username)
        .map(CustomUserDetails::new)
        .orElseThrow( ()-> new UsernameNotFoundException("user not found"));
    }
}
