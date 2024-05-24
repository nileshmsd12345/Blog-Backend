package com.deadpool.blog.blogbackend.controller;


import com.deadpool.blog.blogbackend.config.AppConstants;
import com.deadpool.blog.blogbackend.dto.PostDto;
import com.deadpool.blog.blogbackend.dto.PostResponse;
import com.deadpool.blog.blogbackend.service.FileService;
import com.deadpool.blog.blogbackend.service.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


@RequestMapping("/api/posts")
@RestController
public class PostController {


    final PostService postService;
    final FileService fileService;


    @Value("${project.image}")
    private String path;

    public PostController(PostService postService, FileService fileService) {
        this.postService = postService;

        this.fileService = fileService;
    }

    //create post
    @PostMapping("/user/{userId}/category/{categoryId}")
    ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Long userId, @PathVariable Long categoryId) throws Exception {
        return ResponseEntity.ok(postService.createPost(postDto, userId, categoryId));
    }

    //update post
    @PutMapping("/{id}")
    ResponseEntity<PostDto> updatePost(PostDto postDto, @PathVariable Long id) {
        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }

    //delete post
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    //get post by id
    @GetMapping("/{id}")
    ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //get all posts
    @GetMapping("/all")
    ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = AppConstants.PAGE, defaultValue = AppConstants.PAGE_DEFAULT, required = false) Integer page,
                                             @RequestParam(value = AppConstants.SIZE, defaultValue = AppConstants.SIZE_DEFAULT, required = false) Integer size,
                                             @RequestParam(value = AppConstants.SORT_BY, defaultValue = AppConstants.SORT_DEFAULT, required = false) String sort,
                                             @RequestParam(value = AppConstants.ORDER, defaultValue = AppConstants.ASC, required = false) String order
    ) {
        return ResponseEntity.ok(postService.getAllPosts(page, size, sort, order));
    }

    //get posts by user
    @GetMapping("/user/{id}/posts")
    ResponseEntity<PostResponse> getPostsByUser(@PathVariable Long id,
                                                @RequestParam(value = AppConstants.PAGE, defaultValue = AppConstants.PAGE_DEFAULT, required = false) Integer page,
                                                @RequestParam(value = AppConstants.SIZE, defaultValue = AppConstants.SIZE_DEFAULT, required = false) Integer size

    ) throws Exception {
        return ResponseEntity.ok(postService.getPostByUser(id, page, size));
    }

    //get posts by category
    @GetMapping("/category/{id}/posts")
    ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Long id,
                                                    @RequestParam(value = AppConstants.PAGE, defaultValue = AppConstants.PAGE_DEFAULT, required = false) Integer page,
                                                    @RequestParam(value = AppConstants.SIZE, defaultValue = AppConstants.SIZE_DEFAULT, required = false) Integer size) throws Exception {
        return ResponseEntity.ok(postService.getPostByCategory(id, page, size));
    }

    //search posts
    @GetMapping("/search")
    ResponseEntity<PostResponse> searchPosts(@RequestParam(value = AppConstants.KEYWORD, required = false) String keyword,
                                             @RequestParam(value = AppConstants.PAGE, defaultValue = AppConstants.PAGE_DEFAULT, required = false) Integer page,
                                             @RequestParam(value = AppConstants.SIZE, defaultValue = AppConstants.SIZE_DEFAULT, required = false) Integer size) {
        return ResponseEntity.ok(postService.searchPosts(keyword, page, size));
    }

    //uploadImage
    @PostMapping("/image/upload/{postId}")
    ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,
                                        @PathVariable Long postId
    ) throws IOException {
        PostDto post = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);


        post.setImageName(fileName);
        postService.updatePost(post, postId);

        return ResponseEntity.ok(post);
    }

    //getImage
    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        InputStream stream = fileService.getResource(path, imageName);
        byte[] bytes = stream.readAllBytes();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }


}
