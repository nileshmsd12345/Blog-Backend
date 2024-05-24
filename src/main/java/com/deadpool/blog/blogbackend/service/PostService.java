package com.deadpool.blog.blogbackend.service;


import com.deadpool.blog.blogbackend.dto.*;
import com.deadpool.blog.blogbackend.entity.Category;
import com.deadpool.blog.blogbackend.entity.Post;
import com.deadpool.blog.blogbackend.entity.User;
import com.deadpool.blog.blogbackend.exception.ResourceNotFoundException;
import com.deadpool.blog.blogbackend.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    final PostRepository postRepository;


    final UserService userService;

    final CategoryService categoryService;


    public PostService(PostRepository postRepository, UserService userService, CategoryService categoryService) {
        this.postRepository = postRepository;

        this.userService = userService;
        this.categoryService = categoryService;

    }

    //create post
    public PostDto createPost(PostDto postDto, Long userId, Long categoryId) throws Exception {
        postDto.setAddedDate(new Date());
        postDto.setImageName("default.jpg");

        UserDto userDto = userService.getUserById(userId.intValue());
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);

        postDto.setUser(userDto);
        postDto.setCategory(categoryDto);

        Post post = dtoToEntity(postDto);
        Post savedPost = postRepository.save(post);
        return entityToDto(savedPost);
    }


    //update post
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postDto.getId()));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setAddedDate(postDto.getAddedDate());
        post.setCategory(categoryService.dtoToEntity(postDto.getCategory()));
        post.setUser(userService.dtoToEntity(postDto.getUser()));
        Post savedPost = postRepository.save(post);
        return entityToDto(savedPost);
    }


    //delete post
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }


    //get all posts
    public PostResponse getAllPosts(Integer page, Integer size, String sortBy, String order) {
        Sort sort = (order.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();


        PageRequest p = PageRequest.of(page, size, sort);
        Page<Post> postPage = postRepository.findAll(p);
        List<Post> posts = postPage.getContent();
        return PostResponse.builder()
                .posts(posts.stream().map(this::entityToDto).collect(Collectors.toList()))
                .page(postPage.getNumber())
                .size(postPage.getSize())
                .totalElements((int) postPage.getTotalElements())
                .totalPages(postPage.getTotalPages())
                .last(postPage.isLast()).build();
    }


    //get post by id
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return entityToDto(post);
    }


    //get posts by user
    public PostResponse getPostByUser(Long userId, Integer page, Integer size) throws Exception {

        PageRequest p = PageRequest.of(page, size);

        UserDto userdto = userService.getUserById(userId.intValue());

        User user = userService.dtoToEntity(userdto);

        Page<Post> res = postRepository.findByUser(user, p);


        return PostResponse.builder()
                .posts(res.stream().map(this::entityToDto).collect(Collectors.toList()))
                .page(res.getNumber())
                .size(res.getSize())
                .totalElements((int) res.getTotalElements())
                .totalPages(res.getTotalPages())
                .last(res.isLast()).build();

    }


    //get posts by category
    public PostResponse getPostByCategory(Long categoryId, Integer page, Integer size) throws Exception {

        PageRequest p = PageRequest.of(page, size);

        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);

        Category category = categoryService.dtoToEntity(categoryDto);

        Page<Post> res = postRepository.findByCategory(category, p);


        return PostResponse.builder()
                .posts(res.stream().map(this::entityToDto).collect(Collectors.toList()))
                .page(res.getNumber())
                .size(res.getSize())
                .totalElements((int) res.getTotalElements())
                .totalPages(res.getTotalPages())
                .last(res.isLast()).build();

    }


    // search posts by title


    public Post dtoToEntity(PostDto postDto) {
        return Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .imageName(postDto.getImageName())
                .addedDate(postDto.getAddedDate())
                .category(categoryService.dtoToEntity(postDto.getCategory()))
                .user(userService.dtoToEntity(postDto.getUser()))
                .build();
    }

    public PostDto entityToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageName(post.getImageName())
                .addedDate(post.getAddedDate())
                .category(categoryService.entityToDto(post.getCategory()))
                .user(userService.entityToDto(post.getUser()))
                .comments(
                        post.getComments().stream().toList().stream().map(comment ->
                                CommentDto.builder()
                                        .id(comment.getId())
                                        .content(comment.getContent())
                                        .postId(comment.getPost().getId())
                                        .userId(comment.getUser().getId())
                                        .build()

                        ).collect(Collectors.toList())
                )
                .build();
    }

    public PostResponse searchPosts(String keyword, Integer page, Integer size) {
        PageRequest p = PageRequest.of(page, size);
        Page<Post> res = postRepository.findByTitleContainingOrContentContaining(keyword, keyword, p);
        return PostResponse.builder()
                .posts(res.stream().map(this::entityToDto).collect(Collectors.toList()))
                .page(res.getNumber())
                .size(res.getSize())
                .totalElements((int) res.getTotalElements())
                .totalPages(res.getTotalPages())
                .last(res.isLast()).build();
    }


}


