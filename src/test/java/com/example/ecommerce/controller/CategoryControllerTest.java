package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.request.category.AddCategoryRequest;
import com.example.ecommerce.request.category.UpdateCategoryRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {


    private AddCategoryRequest request;
    private UpdateCategoryRequest updatedRequest;
    private CategoryDTO categoryDto;

    @BeforeEach
    void beforeEach() {
        request = AddCategoryRequest.builder()
                .name("Electronics")
                .description("Contains Electronics")
                .build();

        updatedRequest = UpdateCategoryRequest.builder()
                .name("Updated Electronics")
                .description("Contains Electronics")
                .build();


    }

    // 1. Add Category
    @Test
    void addCategory_ReturnsSuccess_WhenValidRequest() throws Exception {

    }

    @Test
    void addCategory_ReturnsBadRequest_WhenNameIsEmpty() throws Exception {
    }

    @Test
    void addCategory_ReturnsBadRequest_WhenNameExceedsLimit() throws Exception {

    }

    @Test
    void addCategory_ReturnsConflict_WhenDuplicateName() throws Exception {

    }

    @Test
    void addCategory_ReturnsUnauthorized_WhenNotAdmin() throws Exception {
    }

    // 2. Get Category by Name
    @Test
    void getCategoryByName_ReturnsSuccess_WhenCategoryExists() throws Exception {

    }

    @Test
    void getCategoryByName_ReturnsNotFound_WhenCategoryDoesNotExist() throws Exception {

    }

    // 3. Get All Categories
    @Test
    void getAllCategories_ReturnsSuccess_WhenCategoriesExist() throws Exception {


    }

    @Test
    void getAllCategories_ReturnsSuccess_WhenNoCategoriesExist() throws Exception {

    }

    // 4. Get Category Count
    @Test
    void getCategoryCount_ReturnsSuccess() throws Exception {

    }

    // 5. Delete Category
    @Test
    void deleteCategory_ReturnsSuccess_WhenCategoryExists() throws Exception {

    }

    @Test
    void deleteCategory_ReturnsConflict_WhenCategoryHasProducts() throws Exception {

    }

    // 6. Update Category
    @Test
    void updateCategory_ReturnsSuccess_WhenValidRequest() throws Exception {

    }

    @Test
    void updateCategory_ReturnsConflict_WhenDuplicateName() throws Exception {

    }
}