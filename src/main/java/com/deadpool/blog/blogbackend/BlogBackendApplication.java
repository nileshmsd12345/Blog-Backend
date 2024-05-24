package com.deadpool.blog.blogbackend;

import com.deadpool.blog.blogbackend.config.AppConstants;
import com.deadpool.blog.blogbackend.entity.Role;
import com.deadpool.blog.blogbackend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BlogBackendApplication implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BlogBackendApplication.class, args);
    }


    @Override
    public void run(String... args) {
        try {

            List<Role> roles = Arrays.asList(
                    Role.builder().id(Long.valueOf(AppConstants.ADMIN_ROLE_ID)).name("ADMIN").build(),
                    Role.builder().id(Long.valueOf(AppConstants.USER_ROLE_ID)).name("USER").build()
            );

            List<Role> result = roleRepository.saveAll(roles);

            result.forEach(System.out::println);


        } catch (Exception e) {
            System.out.println("error while saving roles:" + e);
        }
    }


}
