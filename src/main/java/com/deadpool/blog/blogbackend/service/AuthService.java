package com.deadpool.blog.blogbackend.service;


import com.deadpool.blog.blogbackend.config.security.JwtTokenHelper;
import com.deadpool.blog.blogbackend.dto.JwtAuthRequest;
import com.deadpool.blog.blogbackend.dto.JwtAuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


///add logger annotation
@Slf4j
@Service
public class AuthService {


    final JwtTokenHelper jwtTokenHelper;
    final AuthenticationManager authenticationManager;

    final CustomUserDetailService userDetailsService;

    public AuthService(JwtTokenHelper jwtTokenHelper, AuthenticationManager authenticationManager, CustomUserDetailService userDetailsService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public JwtAuthResponse createAuthenticationToken(JwtAuthRequest jwtAuthRequest) {
        authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());

        String token = jwtTokenHelper.generateToken(userDetails);

        return new JwtAuthResponse(token);
    }


    void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            log.error("Invalid username or password", e);

            throw new RuntimeException("Invalid username or password", e);
        }
    }

}
