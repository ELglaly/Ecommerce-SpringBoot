package com.example.demo.serivce.category;

import com.example.demo.model.entity.Category;

import java.util.List;

public interface ICategoryService {

    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category getCategoryByName(String categoryName);
    Category addCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategory(Long id);
    int getCategoryCount();

}
