package com.example.demo.controller;

import com.example.demo.constants.*;
import com.example.demo.model.dto.CategoryDto;
import com.example.demo.request.category.AddCategoryRequest;
import com.example.demo.request.category.UpdateCategoryRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.serivce.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.CATEGORY_ENDPOINT)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse(true,"Category deleted!"));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody @Valid UpdateCategoryRequest request) {
            CategoryDto categoryDto = categoryService.updateCategory(request,id);
            return ResponseEntity.ok(new ApiResponse(categoryDto,"Category Updated!"));

    }


}
