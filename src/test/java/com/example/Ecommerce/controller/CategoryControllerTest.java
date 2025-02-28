package com.example.Ecommerce.controller;

import com.example.Ecommerce.constants.ApiConstants;
import com.example.Ecommerce.exceptions.category.CategoryAlreadyExistsException;
import com.example.Ecommerce.model.dto.CategoryDto;
import com.example.Ecommerce.request.category.AddCategoryRequest;
import com.example.Ecommerce.request.category.UpdateCategoryRequest;
import com.example.Ecommerce.security.TestSecurityConfig;
import com.example.Ecommerce.security.jwt.JwtService;
import com.example.Ecommerce.serivce.category.ICategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.web.SpringBootMockServletContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {


    private AddCategoryRequest request;
    private UpdateCategoryRequest updatedRequest;
    private CategoryDto categoryDto;

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

        categoryDto = CategoryDto.builder()
                .name(request.getName())
                .id(1L)
                .description(request.getDescription())
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