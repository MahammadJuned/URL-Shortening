package com.example.URL.Shortening.Service.service;

import com.example.URL.Shortening.Service.entity.UserEntity;
import com.example.URL.Shortening.Service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity existingUser =userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found.."));
        return new User(existingUser.getEmail(), existingUser.getPassword(), new ArrayList<>());
    }
}
