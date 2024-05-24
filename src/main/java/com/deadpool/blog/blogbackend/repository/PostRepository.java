package com.deadpool.blog.blogbackend.repository;

import com.deadpool.blog.blogbackend.entity.Category;
import com.deadpool.blog.blogbackend.entity.Post;
import com.deadpool.blog.blogbackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    Page<Post> findByTitleContaining(String title, Pageable pageable);

    List<Post> findByContentContaining(String content);

    Page<Post> findByTitleContainingOrContentContaining(String title,String content,Pageable pageable);

    Page<Post> findByUser(User user, Pageable pageable);

    Page<Post> findByCategory(Category category, Pageable pageable);
}
