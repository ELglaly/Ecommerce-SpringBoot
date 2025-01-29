package com.example.demo.controller;


import com.example.demo.constants.ApiConstants;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.request.AddProductRequest;
import com.example.demo.request.UpdateProductRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.serivce.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.PRODUCT_ENDPOINT)
public class ProductController {

    private final  ProductService productService;
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest addProductRequest) {
        ProductDto productDto = productService.addProduct(addProductRequest);
        return ResponseEntity.ok(new ApiResponse(productDto, "Product added successfully"));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        ProductDto productDto = productService.getProductById(id);
        if (productDto == null) {
            throw new ResourceNotFoundException("Product not found", "Product");
        }
        return ResponseEntity.ok(new ApiResponse(productDto, "Product found"));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse(products, "Products retrieved successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest updateProductRequest, @PathVariable Long id) {
        ProductDto updatedProduct = productService.updateProduct(updateProductRequest, id);
        if (updatedProduct == null) {
            throw new ResourceNotFoundException("Product not found", "Product");
        }
        return ResponseEntity.ok(new ApiResponse(updateProductRequest, "Product updated successfully"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse(true, "Product deleted successfully"));
    }

    @GetMapping("get/name/{name}")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name) {
        List<ProductDto> products = productService.getProductsByName(name);
        return ResponseEntity.ok(new ApiResponse(products, "Products found by name"));
    }

    @GetMapping("/get/category/{category}")
    public ResponseEntity<ApiResponse> getAllProductsByCategory(@PathVariable String category) {
        List<ProductDto> products = productService.getAllProductsByCategory(category);
        return ResponseEntity.ok(new ApiResponse(products, "Products found by category"));
    }

    @GetMapping("/get/brand/{brand}")
    public ResponseEntity<ApiResponse> getAllProductsByBrand(@PathVariable String brand) {
        List<ProductDto> products = productService.getAllProductsByBrand(brand);
        return ResponseEntity.ok(new ApiResponse(products, "Products found by brand"));
    }

    @GetMapping("/get/category/{category}/brand/{brand}")
    public ResponseEntity<ApiResponse> getAllProductsByCategoryAndBrand(@PathVariable String category, @PathVariable String brand) {
        List<ProductDto> products = productService.getAllProductsByCategoryAndBrand(category, brand);
        return ResponseEntity.ok(new ApiResponse(products, "Products found by category and brand"));
    }

    @GetMapping("/get/name/{name}/category/{category}")
    public ResponseEntity<ApiResponse> getAllProductsByNameAndCategory(@PathVariable String name, @PathVariable String category) {
        List<ProductDto> products = productService.getAllProductsByNameAndCategory(name, category);
        return ResponseEntity.ok(new ApiResponse(products, "Products found by name and category"));
    }

    @GetMapping("/get/name/{name}/brand/{brand}")
    public ResponseEntity<ApiResponse> getAllProductsByNameAndBrand(@PathVariable String name, @PathVariable String brand) {
        List<ProductDto> products = productService.getAllProductsByBrandAndName(brand,name);
        return ResponseEntity.ok(new ApiResponse(products, "Products found by name and brand"));
    }

    @GetMapping("/get/count/category/{category}")
    public ResponseEntity<ApiResponse> getProductsCountByCategory(@PathVariable String category) {
        int count = productService.getProductsCountByCategory(category);
        return ResponseEntity.ok(new ApiResponse(count, "Product count by category"));
    }

    @GetMapping("/get/count/brand/{brand}")
    public ResponseEntity<ApiResponse> getProductsCountByBrand(@PathVariable String brand) {
        int count = productService.getProductsCountByBrand(brand);
        return ResponseEntity.ok(new ApiResponse(count, "Product count by brand"));
    }

    @GetMapping("/get/count/name/{name}")
    public ResponseEntity<ApiResponse> getProductsCountByName(@PathVariable String name) {
        int count = productService.getProductsCountByName(name);
        return ResponseEntity.ok(new ApiResponse(count, "Product count by name"));
    }

}
