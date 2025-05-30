package com.example.Ecommerce.controller;

import com.example.Ecommerce.constants.*;
import com.example.Ecommerce.model.dto.CategoryDto;
import com.example.Ecommerce.request.category.AddCategoryRequest;
import com.example.Ecommerce.request.category.UpdateCategoryRequest;
import com.example.Ecommerce.response.ApiResponse;
import com.example.Ecommerce.serivce.email.IRegistrationEmail;
import com.example.Ecommerce.serivce.category.ICategoryService;
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
        CategoryDto category = categoryService.addCategory(request);
        return ResponseEntity.ok(new ApiResponse(category,"Added Successfully!"));
    }

    @GetMapping ("/search/")
    public ResponseEntity<ApiResponse> getCategoryByName(@RequestParam String name) {
        CategoryDto categoryDto = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(new ApiResponse(categoryDto,"Category found!"));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllCategory() {

        List<CategoryDto> categoryDtos = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse(categoryDtos,"Category found!") );
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
            CategoryDto categoryDto = categoryService.updateCategory(request,id);
            return ResponseEntity.ok(new ApiResponse(categoryDto,"Category Updated!"));

    }



}
