# Blog Service with Spring Boot and SQL

## Project Overview

This project is a Blog Service built with Spring Boot and SQL. It provides a RESTful API for creating, reading, updating, and deleting blog posts. The project is designed to handle multiple users, each of whom can create and manage their own blog posts. The backend is implemented using Spring Boot, and the data is stored in a relational database managed by SQL.

## Features

- **User Authentication and Authorization**:
  - User registration and login.
  - JWT (JSON Web Token) for securing API endpoints.
  - Role-based access control (admin, user).

- **Blog Post Management**:
  - Create, read, update, and delete blog posts.
  - Users can only edit or delete their own posts.
  - Admins can manage all posts.

- **Comment System**:
  - Users can add comments to blog posts.
  - CRUD operations for comments.
  - Users can only manage their own comments.

- **Category Management**:
  - Blog posts can be categorized.
  - CRUD operations for categories.

- **Database Management**:
  - Use of SQL (MySQL, PostgreSQL, or H2) for data storage.
  - Database schema designed with relationships between users, posts, comments, and categories.

## Technologies Used

- **Spring Boot**: For building the RESTful API.
- **Spring Security**: For securing the application.
- **Spring Data JPA**: For data persistence and ORM.
- **SQL (MySQL/PostgreSQL)**: For database management.
- **Hibernate**: As the JPA implementation.
- **JWT**: For authentication.
- **Maven**: For project management and dependencies.
- **Lombok**: For reducing boilerplate code.


