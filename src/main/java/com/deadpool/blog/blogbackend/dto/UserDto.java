package com.deadpool.blog.blogbackend.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotEmpty
    @Size(min = 3, max = 10, message = "Name must be between 3 and 50 characters")
    private String name;
    @Email(message = "Please provide a valid email address")
    private String email;
    @NotEmpty
    @Size(min = 3, max = 20, message = "Password must be between 3 and 10 characters")
    private String password;
    @NotEmpty
    private String about;

    private List<RoleDto> roles;


}
