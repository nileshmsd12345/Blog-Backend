package com.deadpool.blog.blogbackend.repository;

import com.deadpool.blog.blogbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
