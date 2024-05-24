package com.deadpool.blog.blogbackend.controller;


import com.deadpool.blog.blogbackend.dto.CommentDto;
import com.deadpool.blog.blogbackend.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/comments")
    @RestController
public class CommentController {

    final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // create a new comment
    @PostMapping("/user/{userId}/post/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Long postId, @PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(commentService.createComment(comment, postId,userId));
    }

    // update a comment
    @PostMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto comment, @PathVariable Long id) {
        return ResponseEntity.ok(commentService.updateComment(comment, id));
    }

    // delete a comment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
