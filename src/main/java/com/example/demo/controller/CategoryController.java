package com.example.demo.controller;


import com.example.demo.model.dto.CategoryDto;
import com.example.demo.response.ApiResponse;
import com.example.demo.serivce.category.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        CategoryDto category = categoryService.addCategory(categoryDto);
        System.out.println(category.toString());
        return ResponseEntity.ok(new ApiResponse(category,"Added Successfully!"));
    }

    @GetMapping ("/get/{name}")
    public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable String name) {
        CategoryDto categoryDto = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(categoryDto);
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDto categoryDto) {
        categoryDto = categoryService.updateCategory(categoryDto,id);
        return ResponseEntity.ok(categoryDto);
    }


}
