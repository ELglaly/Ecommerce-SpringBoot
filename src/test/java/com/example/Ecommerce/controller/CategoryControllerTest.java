package com.example.Ecommerce.controller;

import com.example.Ecommerce.constants.ApiConstants;
import com.example.Ecommerce.exceptions.category.CategoryAlreadyExistsException;
import com.example.Ecommerce.model.dto.CategoryDto;
import com.example.Ecommerce.request.category.AddCategoryRequest;
import com.example.Ecommerce.request.category.UpdateCategoryRequest;
import com.example.Ecommerce.security.jwt.JwtService;
import com.example.Ecommerce.serivce.category.ICategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.web.SpringBootMockServletContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock// Use @MockBean instead of @Mock
    private ICategoryService categoryService;

    @Mock // Mock another dependency if needed
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

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
        when(categoryService.addCategory(request)).thenReturn(categoryDto);

        mockMvc.perform(post(ApiConstants.CATEGORY_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Electronics"))
                .andExpect(jsonPath("$.message").value("Added Successfully!"));

        verify(categoryService, times(1)).addCategory(request);

    }

    @Test
    void addCategory_ReturnsBadRequest_WhenNameIsEmpty() throws Exception {
        request.setName("");

        mockMvc.perform(post(ApiConstants.CATEGORY_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(categoryService, never()).addCategory(any());
    }

    @Test
    void addCategory_ReturnsBadRequest_WhenNameExceedsLimit() throws Exception {
        request.setName("S".repeat(120));

        mockMvc.perform(post(ApiConstants.CATEGORY_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(categoryService, never()).addCategory(any());
        
    }

    @Test
    void addCategory_ReturnsConflict_WhenDuplicateName() throws Exception {
        when(categoryService.addCategory(request))
                .thenThrow(new CategoryAlreadyExistsException("Category already exists"));

        mockMvc.perform(post(ApiConstants.CATEGORY_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Category already exists"));

        verify(categoryService, times(1)).addCategory(request);
    }

    @Test
    void addCategory_ReturnsUnauthorized_WhenNotAdmin() throws Exception {
        mockMvc.perform(post(ApiConstants.CATEGORY_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());

        verify(categoryService, never()).addCategory(any());
    }

    // 2. Get Category by Name
    @Test
    void getCategoryByName_ReturnsSuccess_WhenCategoryExists() throws Exception {
        when(categoryService.getCategoryByName("Electronics")).thenReturn(categoryDto);

        mockMvc.perform(get(ApiConstants.CATEGORY_ENDPOINT + "/search/")
                        .param("name", "Electronics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Electronics"))
                .andExpect(jsonPath("$.message").value("Category found!"));

        verify(categoryService, times(1)).getCategoryByName("Electronics");
    }

    @Test
    void getCategoryByName_ReturnsNotFound_WhenCategoryDoesNotExist() throws Exception {
        when(categoryService.getCategoryByName("NonExistent")).thenReturn(null);

        mockMvc.perform(get(ApiConstants.CATEGORY_ENDPOINT + "/search/")
                        .param("name", "NonExistent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value("Category found!"));

        verify(categoryService, times(1)).getCategoryByName("NonExistent");
    }

    // 3. Get All Categories
    @Test
    void getAllCategories_ReturnsSuccess_WhenCategoriesExist() throws Exception {
        List<CategoryDto> categories = List.of(categoryDto, categoryDto);

        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get(ApiConstants.CATEGORY_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.message").value("Category found!"));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void getAllCategories_ReturnsSuccess_WhenNoCategoriesExist() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(List.of());

        mockMvc.perform(get(ApiConstants.CATEGORY_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(0));

        verify(categoryService, times(1)).getAllCategories();
    }

    // 4. Get Category Count
    @Test
    void getCategoryCount_ReturnsSuccess() throws Exception {
        when(categoryService.getCategoryCount()).thenReturn(5L);

        mockMvc.perform(get(ApiConstants.CATEGORY_ENDPOINT + "/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(5))
                .andExpect(jsonPath("$.message").value("Category found!"));

        verify(categoryService, times(1)).getCategoryCount();
    }

    // 5. Delete Category
    @Test
    void deleteCategory_ReturnsSuccess_WhenCategoryExists() throws Exception {
        doNothing().when(categoryService).deleteCategory(1L);

        mockMvc.perform(delete(ApiConstants.CATEGORY_ENDPOINT + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.message").value("Category deleted!"));

        verify(categoryService, times(1)).deleteCategory(1L);
    }

    @Test
    void deleteCategory_ReturnsConflict_WhenCategoryHasProducts() throws Exception {
        doThrow(new CategoryAlreadyExistsException("Category is associated with products"))
                .when(categoryService).deleteCategory(1L);

        mockMvc.perform(delete(ApiConstants.CATEGORY_ENDPOINT + "/1"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Category is associated with products"));

        verify(categoryService, times(1)).deleteCategory(1L);
    }

    // 6. Update Category
    @Test
    void updateCategory_ReturnsSuccess_WhenValidRequest() throws Exception {
        when(categoryService.updateCategory(updatedRequest, 1L)).thenReturn(categoryDto);

        mockMvc.perform(put(ApiConstants.CATEGORY_ENDPOINT + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Electronics"))
                .andExpect(jsonPath("$.message").value("Category Updated!"));

        verify(categoryService, times(1)).updateCategory(updatedRequest, 1L);
    }

    @Test
    void updateCategory_ReturnsConflict_WhenDuplicateName() throws Exception {
        when(categoryService.updateCategory(updatedRequest, 1L))
                .thenThrow(new CategoryAlreadyExistsException("Category name already exists"));

        mockMvc.perform(put(ApiConstants.CATEGORY_ENDPOINT + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Category name already exists"));

        verify(categoryService, times(1)).updateCategory(updatedRequest, 1L);
    }
}