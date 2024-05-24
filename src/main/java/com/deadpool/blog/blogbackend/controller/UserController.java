package com.deadpool.blog.blogbackend.controller;

import com.deadpool.blog.blogbackend.dto.ApiResponse;
import com.deadpool.blog.blogbackend.dto.UserDto;
import com.deadpool.blog.blogbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }





    // Add your code here

    //write a method to get all users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    //write a method to get a user by id

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) throws Exception {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    //write a method to create a user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid  @RequestBody UserDto userDto) {
        UserDto savedUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    //write a method to update a user

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Integer id) throws Exception {
        UserDto updatedUser = userService.updateUser(userDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    //write a method to delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("User deleted successfully")
                        .status(true)
                        .build(),
                HttpStatus.OK
        );
    }


}
