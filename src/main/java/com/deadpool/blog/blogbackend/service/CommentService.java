package com.deadpool.blog.blogbackend.service;


import com.deadpool.blog.blogbackend.dto.CommentDto;
import com.deadpool.blog.blogbackend.entity.Comment;
import com.deadpool.blog.blogbackend.entity.Post;
import com.deadpool.blog.blogbackend.entity.User;
import com.deadpool.blog.blogbackend.repository.CommentRepository;
import com.deadpool.blog.blogbackend.repository.PostRepository;
import com.deadpool.blog.blogbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    final CommentRepository commentRepository;

    final PostService postService;

    final PostRepository postRepository;

    final UserRepository userRepository;

    final UserService userService;

    public CommentService(CommentRepository commentRepository, PostService postService, PostRepository postRepository, UserRepository userRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    // create a new comment
    public CommentDto createComment(CommentDto commentDto, Long postId, Long userId) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));


        Comment savedComment = commentRepository.save(
                Comment.builder().content(commentDto.getContent()).post(post).user(user).build());
        return entityToDto(savedComment);
    }

    //update a comment
    public CommentDto updateComment(CommentDto commentDto, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(commentDto.getContent());
        Comment savedComment = commentRepository.save(comment);
        return entityToDto(savedComment);
    }

    //delete a comment
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(comment);
    }


    public Comment dtoToEntity(CommentDto commentDto) {
        return Comment.builder().id(commentDto.getId()).content(commentDto.getContent()).build();
    }

    public CommentDto entityToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId()).content(comment.getContent())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .build();
    }
}
