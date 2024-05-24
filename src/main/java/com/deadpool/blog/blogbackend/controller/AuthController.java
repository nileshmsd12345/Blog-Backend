package com.deadpool.blog.blogbackend.controller;


import com.deadpool.blog.blogbackend.dto.JwtAuthRequest;
import com.deadpool.blog.blogbackend.dto.JwtAuthResponse;
import com.deadpool.blog.blogbackend.dto.UserDto;
import com.deadpool.blog.blogbackend.service.AuthService;
import com.deadpool.blog.blogbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    final AuthService authService;

    final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createAuthenticationToken(
            @RequestBody JwtAuthRequest jwtAuthRequest) {
        return new ResponseEntity<>(authService.createAuthenticationToken(jwtAuthRequest), HttpStatus.OK);

    }

    // register a new user
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        UserDto savedUser = userService.registerNewUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
