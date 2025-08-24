package com.example.ecommerce.controller;

import com.example.ecommerce.constants.*;
import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.request.category.AddCategoryRequest;
import com.example.ecommerce.request.category.UpdateCategoryRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.serivce.category.CategoryService;
import com.example.ecommerce.util.CategoryUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping(ApiConstants.CATEGORY_ENDPOINT)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody @Valid AddCategoryRequest request) {
        CategoryDTO category = categoryService.addCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(category, CategoryUtils.CATEGORY_ADDED));
    }

    @GetMapping ("/search")
    public ResponseEntity<ApiResponse> searchByName(@RequestParam String name) {
        Page<CategoryDTO> categoryDTOS = categoryService.searchByName(name);
        return ResponseEntity.ok(new ApiResponse(categoryDTOS,CategoryUtils.CATEGORY_FETCHED));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllCategory() {
        Page<CategoryDTO> categoryDTOS = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse(categoryDTOS, CategoryUtils.CATEGORY_FETCHED) );
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> getCategoryCount() {
        return ResponseEntity.ok(new ApiResponse(categoryService.getCategoryCount(),CategoryUtils.CATEGORY_FETCHED));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse(true,CategoryUtils.CATEGORY_DELETED));
    }

    @PutMapping ("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody @Valid UpdateCategoryRequest request) {
            CategoryDTO categoryDto = categoryService.updateCategory(request,id);
            return ResponseEntity.ok(new ApiResponse(categoryDto,CategoryUtils.CATEGORY_UPDATED));

    }



}
