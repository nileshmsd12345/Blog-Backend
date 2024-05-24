package com.deadpool.blog.blogbackend.service;


import com.deadpool.blog.blogbackend.dto.CategoryDto;
import com.deadpool.blog.blogbackend.entity.Category;
import com.deadpool.blog.blogbackend.exception.ResourceNotFoundException;
import com.deadpool.blog.blogbackend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    //create category

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = dtoToEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return entityToDto(savedCategory);
    }


    //update category

    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            Category categoryEntity = category.get();
            categoryEntity.setCategoryTitle(categoryDto.getCategoryTitle());
            categoryEntity.setCategoryDescription(categoryDto.getCategoryDescription());
            Category savedCategory = categoryRepository.save(categoryEntity);
            return entityToDto(savedCategory);

        } else {
            throw new ResourceNotFoundException(
                    "Category",
                    "id",
                    id

            );
        }

    }

    //delete category
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(category);
    }


    //get category by id

    public CategoryDto getCategoryById(Long id) throws Exception {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            return entityToDto(category.get());
        } else {
            throw new ResourceNotFoundException(
                    "Category",
                    "id",
                    id

            );
        }
    }

    //get all categories

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::entityToDto).toList();
    }


    public Category dtoToEntity(CategoryDto categoryDto) {
        return Category.builder()
                .categoryId(categoryDto.getCategoryId())
                .categoryTitle(categoryDto.getCategoryTitle())
                .categoryDescription(categoryDto.getCategoryDescription())
                .build();
    }

    public CategoryDto entityToDto (Category savedCategory) {
        return CategoryDto.builder()
                .categoryId(savedCategory.getCategoryId())
                .categoryTitle(savedCategory.getCategoryTitle())
                .categoryDescription(savedCategory.getCategoryDescription())
                .build();
    }


}
