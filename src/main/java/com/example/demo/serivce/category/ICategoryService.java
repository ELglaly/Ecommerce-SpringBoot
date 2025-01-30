package com.example.demo.serivce.category;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.entity.Category;
import com.example.demo.request.AddCategoryRequest;
import com.example.demo.request.UpdateCategoryRequest;

import java.util.List;

public interface ICategoryService {

    CategoryDto getCategoryById(Long id);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryByName(String categoryName);
    CategoryDto addCategory(AddCategoryRequest category);
    CategoryDto updateCategory(UpdateCategoryRequest category, Long id);
    void deleteCategory(Long id);
    Long getCategoryCount();
}
