package com.deadpool.blog.blogbackend.service;


import com.deadpool.blog.blogbackend.config.AppConstants;
import com.deadpool.blog.blogbackend.dto.RoleDto;
import com.deadpool.blog.blogbackend.dto.UserDto;
import com.deadpool.blog.blogbackend.entity.Role;
import com.deadpool.blog.blogbackend.entity.User;
import com.deadpool.blog.blogbackend.exception.ResourceNotFoundException;
import com.deadpool.blog.blogbackend.repository.RoleRepository;
import com.deadpool.blog.blogbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    final UserRepository userRepository;

    final PasswordEncoder passwordEncoder;

    final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    ///register a new user

    public UserDto registerNewUser(UserDto userDto) {
        User user = dtoToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userDto.getRoles() != null) {
            Set<Role> roles = userDto.getRoles().stream().map(role -> roleRepository.findById(role.getId()).orElseThrow(() -> new ResourceNotFoundException("Role", "id", role.getId()))).collect(Collectors.toSet());
            user.setRoles(roles);

        } else {
            Role role = roleRepository.findById(Long.valueOf(AppConstants.USER_ROLE_ID)).orElseThrow(() -> new ResourceNotFoundException("Role", "id", AppConstants.USER_ROLE_ID));
            user.setRoles(Set.of(role));
        }


        User savedUser = userRepository.save(user);
        return entityToDto(savedUser);
    }

    public UserDto createUser(UserDto userDto) {
        User user = dtoToEntity(userDto);
        User savedUser = userRepository.save(user);
        return entityToDto(savedUser);
    }

    public UserDto updateUser(UserDto userDto, Integer id) throws Exception {
        Optional<User> user = userRepository.findById(Long.valueOf(id));

        if (user.isPresent()) {
            User userEntity = user.get();
            userEntity.setName(userDto.getName());
            userEntity.setEmail(userDto.getEmail());
            userEntity.setPassword(userDto.getPassword());
            userEntity.setAbout(userDto.getAbout());
            User savedUser = userRepository.save(userEntity);
            return entityToDto(savedUser);

        } else {
            throw new ResourceNotFoundException("User", "id", id

            );
        }

    }

    public UserDto getUserById(Integer id) throws Exception {
        Optional<User> user = userRepository.findById(Long.valueOf(id));

        if (user.isPresent()) {
            return entityToDto(user.get());
        } else {
            throw new ResourceNotFoundException("User", "id", id

            );
        }
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::entityToDto).toList();
    }

    public void deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(Long.valueOf(id));

        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new ResourceNotFoundException("User", "id", id

            );
        }
    }

    UserDto entityToDto(User user) {
        return UserDto.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).password(user.getPassword()).about(user.getAbout()).roles(user.getRoles().stream().map(role -> RoleDto.builder().id(role.getId()).name(role.getName()).build()).toList()).build();

    }

    User dtoToEntity(UserDto userDto) {
        return User.builder().id(userDto.getId()).name(userDto.getName()).email(userDto.getEmail()).password(userDto.getPassword()).about(userDto.getAbout()).build();
    }


}
