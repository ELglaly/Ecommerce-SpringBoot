package com.example.demo.serivce.category;

import com.example.demo.model.dto.CategoryDto;

import java.util.List;

public interface ICategorySearchService {
    CategoryDto getCategoryById(Long id);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryByName(String categoryName);
}
