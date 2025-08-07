package com.example.ecommerce.controller;

import com.example.ecommerce.constants.*;
import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.request.category.AddCategoryRequest;
import com.example.ecommerce.request.category.UpdateCategoryRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.serivce.category.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.CATEGORY_ENDPOINT)
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody @Valid AddCategoryRequest request) {
        CategoryDTO category = categoryService.addCategory(request);
        return ResponseEntity.ok(new ApiResponse(category,"Added Successfully!"));
    }

    @GetMapping ("/search/")
    public ResponseEntity<ApiResponse> getCategoryByName(@RequestParam String name) {
        CategoryDTO categoryDto = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(new ApiResponse(categoryDto,"Category found!"));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllCategory() {

        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse(categoryDTOS,"Category found!") );
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> getCategoryCount() {
        return ResponseEntity.ok(new ApiResponse(categoryService.getCategoryCount(),"Category found!"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse(true,"Category deleted!"));
    }

    @PutMapping ("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody @Valid UpdateCategoryRequest request) {
            CategoryDTO categoryDto = categoryService.updateCategory(request,id);
            return ResponseEntity.ok(new ApiResponse(categoryDto,"Category Updated!"));

    }



}
