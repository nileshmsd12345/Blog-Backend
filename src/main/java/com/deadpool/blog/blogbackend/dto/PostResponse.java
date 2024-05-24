package com.deadpool.blog.blogbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private List<PostDto> posts;
    private int page;

    private int size;
    private int totalElements;
    private int totalPages;

    private Boolean last;


}
