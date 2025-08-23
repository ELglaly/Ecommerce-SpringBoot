package com.example.ecommerce.serivce.category;

import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.request.category.AddCategoryRequest;
import com.example.ecommerce.request.category.UpdateCategoryRequest;

public interface CategoryManagementService {
    /**
     * Adds a new category.
     *
     * @param category The AddCategoryRequest object containing the details of the category to be added.
     * @return The CategoryDTO object representing the added category.
     */
    CategoryDTO addCategory(AddCategoryRequest category);
    /**
     * Updates an existing category.
     *
     * @param category The UpdateCategoryRequest object containing the updated details of the category.
     * @param id The ID of the category to be updated.
     * @return The CategoryDTO object representing the updated category.
     */
    CategoryDTO updateCategory(UpdateCategoryRequest category, Long id);
    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to be deleted.
     */
    void deleteCategory(Long id);

}
