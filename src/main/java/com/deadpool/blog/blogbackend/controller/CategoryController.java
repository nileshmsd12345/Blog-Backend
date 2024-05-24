package com.deadpool.blog.blogbackend.controller;

import com.deadpool.blog.blogbackend.dto.CategoryDto;
import com.deadpool.blog.blogbackend.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    //create category
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(201).body(savedCategory);
    }


    //update category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable String id) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, Long.valueOf(id));
        return ResponseEntity.status(200).body(updatedCategory);
    }

    //delete category

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(Long.valueOf(id));
        return ResponseEntity.status(200).body("Category deleted successfully");
    }


    //get all categories
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.status(200).body(categoryService.getAllCategories());
    }


    //get category by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String id) throws Exception {
        return ResponseEntity.status(200).body(categoryService.getCategoryById(Long.valueOf(id)));
    }
}
