package com.deadpool.blog.blogbackend.repository;

import com.deadpool.blog.blogbackend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
