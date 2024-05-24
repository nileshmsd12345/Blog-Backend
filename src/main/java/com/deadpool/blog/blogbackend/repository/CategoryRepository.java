package com.deadpool.blog.blogbackend.repository;

import com.deadpool.blog.blogbackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
