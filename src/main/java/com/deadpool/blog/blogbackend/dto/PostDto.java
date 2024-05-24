package com.deadpool.blog.blogbackend.dto;

import lombok.*;

import java.util.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;

    private String title;


    private String content;

    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto user;

    private List<CommentDto> comments = new ArrayList<>();
}
