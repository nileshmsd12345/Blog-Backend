package com.deadpool.blog.blogbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Table(name = "roles")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {


    @Id
    private Long id;
    private String name;


    @ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();



}
