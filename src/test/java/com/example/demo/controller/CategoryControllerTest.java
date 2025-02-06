package com.example.demo.controller;


import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Product;
import com.example.demo.request.category.AddCategoryRequest;
import com.example.demo.request.category.UpdateCategoryRequest;
import com.example.demo.request.product.AddProductRequest;
import com.example.demo.request.product.UpdateProductRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.serivce.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private Category category;
    private List<Category> categories;
    private List<Product> products;
    private List<CategoryDto> categoryDtos;
    private List<ProductDto> productDtos;
    private AddCategoryRequest addCategoryRequest;
    private UpdateCategoryRequest updateCategoryRequest;


    @BeforeEach
    void setUp() {
            // Setup mock objects
            addCategoryRequest  = new AddCategoryRequest();
            addCategoryRequest.setName("Product1");
             updateCategoryRequest = new UpdateCategoryRequest();
          updateCategoryRequest.setName("Updated Product");

    }

    @Nested
    @DisplayName("Add Category Tests")
    class AddCategoryTests {

        @Test
        @DisplayName("Should successfully add valid category")
        void addCategory_WhenValidCategory_ShouldReturnCreatedStatus() {
            // Arrange
            AddCategoryRequest category = new AddCategoryRequest();
            CategoryDto categoryDto = new CategoryDto();
            category.setName("Electronics");
            when(categoryService.addCategory(any(AddCategoryRequest.class))).thenReturn(categoryDto);

            // Act
            ResponseEntity<ApiResponse> response = categoryController.addCategory(category);

            // Assert
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Electronics", response.getBody().getMessage());
            verify(categoryService, times(1)).addCategory(any(AddCategoryRequest.class));
        }

        @Test
        @DisplayName("Should handle null category")
        void addCategory_WhenNullCategory_ShouldReturnBadRequest() {
            // Act
            ResponseEntity<ApiResponse> response = categoryController.addCategory(null);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        @DisplayName("Should handle category with null name")
        void addCategory_WhenCategoryNameIsNull_ShouldReturnBadRequest() {
            // Arrange
            AddCategoryRequest category = new AddCategoryRequest();

            // Act
            ResponseEntity<ApiResponse> response = categoryController.addCategory(category);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Should handle duplicate category")
        void addCategory_WhenCategoryAlreadyExists_ShouldReturnConflict() {
            // Arrange
            AddCategoryRequest category = new AddCategoryRequest();
            when(categoryService.addCategory(any(AddCategoryRequest.class)))
                .thenThrow(new ResourceAlreadyExistsException("Category already exists","category"));

            // Act
            ResponseEntity<ApiResponse> response = categoryController.addCategory(category);

            // Assert
            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("Get Category Tests")
    class GetCategoryTests {

        @Test
        @DisplayName("Should return category when exists")
        void getCategoryByName_WhenCategoryExists_ShouldReturnCategory() {
            // Arrange
            String categoryName = "Electronics";
            AddCategoryRequest category = new AddCategoryRequest();
            CategoryDto categoryDto = new CategoryDto();
            when(categoryService.getCategoryByName(categoryName)).thenReturn(categoryDto);

            // Act
            ResponseEntity<ApiResponse> response = categoryController.getCategoryByName(categoryName);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(categoryName, response.getBody().getData());
        }

//        @Test
//        @DisplayName("Should handle non-existent category")
//        void getCategoryByName_WhenCategoryDoesNotExist_ShouldReturnNotFound() {
//            // Arrange
//            String categoryName = "NonExistent";
//            when(categoryService.getCategoryByName(categoryName))
//                .thenThrow(new CategoryNotFoundException("Category not found"));
//
//            // Act
//            ResponseEntity<Category> response = categoryController.getCategoryByName(categoryName);
//
//            // Assert
//            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        }
//
//        @Test
//        @DisplayName("Should handle null or empty category name")
//        void getCategoryByName_WhenNameIsNullOrEmpty_ShouldReturnBadRequest() {
//            // Act & Assert
//            assertEquals(HttpStatus.BAD_REQUEST,
//                categoryController.getCategoryByName(null).getStatusCode());
//            assertEquals(HttpStatus.BAD_REQUEST,
//                categoryController.getCategoryByName("").getStatusCode());
//        }
//    }
//
//    @Nested
//    @DisplayName("Get All Categories Tests")
//    class GetAllCategoriesTests {
//
//        @Test
//        @DisplayName("Should return all categories when exists")
//        void getAllCategory_WhenCategoriesExist_ShouldReturnList() {
//            // Arrange
//            List<Category> categories = Arrays.asList(
//                new Category("Electronics"),
//                new Category("Books")
//            );
//            when(categoryService.getAllCategories()).thenReturn(categories);
//
//            // Act
//            ResponseEntity<List<ApiResponse>> response = categoryController.getAllCategory();
//
//            // Assert
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(2, response.getBody().size());
//        }
//
//        @Test
//        @DisplayName("Should return empty list when no categories exist")
//        void getAllCategory_WhenNoCategoriesExist_ShouldReturnEmptyList() {
//            // Arrange
//            when(categoryService.getAllCategories()).thenReturn(Collections.emptyList());
//
//            // Act
//            ResponseEntity<List<Category>> response = categoryController.getAllCategory();
//
//            // Assert
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertTrue(response.getBody().isEmpty());
//        }
//    }
//
//    @Nested
//    @DisplayName("Get Category Count Tests")
//    class GetCategoryCountTests {
//
//        @Test
//        @DisplayName("Should return correct count when categories exist")
//        void getCategoryCount_WhenCategoriesExist_ShouldReturnCount() {
//            // Arrange
//            when(categoryService.getCategoryCount()).thenReturn(5L);
//
//            // Act
//            ResponseEntity<ApiResponse> response = categoryController.getCategoryCount();
//
//            // Assert
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(5L, response.getBody());
//        }
//
//        @Test
//        @DisplayName("Should return zero when no categories exist")
//        void getCategoryCount_WhenNoCategoriesExist_ShouldReturnZero() {
//            // Arrange
//            when(categoryService.getCategoryCount()).thenReturn(0L);
//
//            // Act
//            ResponseEntity<ApiResponse> response = categoryController.getCategoryCount();
//
//            // Assert
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(0L, response.getBody());
//        }
//    }
//
//    @Nested
//    @DisplayName("Delete Category Tests")
//    class DeleteCategoryTests {
//
//        @Test
//        @DisplayName("Should successfully delete existing category")
//        void deleteCategory_WhenCategoryExists_ShouldReturnNoContent() {
//            // Arrange
//            String categoryName = "Electronics";
//            doNothing().when(categoryService).deleteCategory(categoryName);
//
//            // Act
//            ResponseEntity<Void> response = categoryController.deleteCategory(categoryName);
//
//            // Assert
//            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//            verify(categoryService, times(1)).deleteCategory(categoryName);
//        }
//
//        @Test
//        @DisplayName("Should handle deletion of non-existent category")
//        void deleteCategory_WhenCategoryDoesNotExist_ShouldReturnNotFound() {
//            // Arrange
//            String categoryName = "NonExistent";
//            doThrow(new CategoryNotFoundException("Category not found"))
//                .when(categoryService).deleteCategory(categoryName);
//
//            // Act
//            ResponseEntity<Void> response = categoryController.deleteCategory(categoryName);
//
//            // Assert
//            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        }
//
//        @Test
//        @DisplayName("Should handle null or empty category name for deletion")
//        void deleteCategory_WhenNameIsNullOrEmpty_ShouldReturnBadRequest() {
//            // Act & Assert
//            assertEquals(HttpStatus.BAD_REQUEST,
//                categoryController.deleteCategory(null).getStatusCode());
//            assertEquals(HttpStatus.BAD_REQUEST,
//                categoryController.deleteCategory("").getStatusCode());
//        }
//    }
//
//    @Nested
//    @DisplayName("Update Category Tests")
//    class UpdateCategoryTests {
//
//        @Test
//        @DisplayName("Should successfully update existing category")
//        void updateCategory_WhenValidUpdate_ShouldReturnUpdatedCategory() {
//            // Arrange
//            String categoryName = "Electronics";
//            Category updatedCategory = new Category("Updated Electronics");
//            when(categoryService.updateCategory(eq(categoryName), any(Category.class)))
//                .thenReturn(updatedCategory);
//
//            // Act
//            ResponseEntity<Category> response =
//                categoryController.updateCategory(categoryName, updatedCategory);
//
//            // Assert
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertNotNull(response.getBody());
//            assertEquals("Updated Electronics", response.getBody().getName());
//        }
//
//        @Test
//        @DisplayName("Should handle update of non-existent category")
//        void updateCategory_WhenCategoryDoesNotExist_ShouldReturnNotFound() {
//            // Arrange
//            String categoryName = "NonExistent";
//            Category updatedCategory = new Category("Updated");
//            when(categoryService.updateCategory(eq(categoryName), any(Category.class)))
//                .thenThrow(new CategoryNotFoundException("Category not found"));
//
//            // Act
//            ResponseEntity<Category> response =
//                categoryController.updateCategory(categoryName, updatedCategory);
//
//            // Assert
//            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        }
//
//        @Test
//        @DisplayName("Should handle null or empty category name for update")
//        void updateCategory_WhenNameIsNullOrEmpty_ShouldReturnBadRequest() {
//            // Arrange
//            Category updatedCategory = new Category("Updated");
//
//            // Act & Assert
//            assertEquals(HttpStatus.BAD_REQUEST,
//                categoryController.updateCategory(null, updatedCategory).getStatusCode());
//            assertEquals(HttpStatus.BAD_REQUEST,
//                categoryController.updateCategory("", updatedCategory).getStatusCode());
//        }
//
//        @Test
//        @DisplayName("Should handle null update category")
//        void updateCategory_WhenUpdateCategoryIsNull_ShouldReturnBadRequest() {
//            // Act
//            ResponseEntity<Category> response =
//                categoryController.updateCategory("Electronics", null);
//
//            // Assert
//            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        }
//    }
}}