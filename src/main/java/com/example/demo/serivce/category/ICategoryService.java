package com.example.demo.serivce.category;

import com.example.demo.model.entity.Category;
import com.example.demo.request.AddCategoryRequest;

import java.util.List;

public interface ICategoryService {

    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category getCategoryByName(String categoryName);
    Category addCategory(AddCategoryRequest category);
    Category updateCategory(AddCategoryRequest category,Long id);
    void deleteCategory(Long id);
    Long getCategoryCount();

}
