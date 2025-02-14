package com.example.demo.controller;

import com.example.demo.constants.ApiConstants;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.request.product.AddProductRequest;
import com.example.demo.request.product.UpdateProductRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.serivce.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.PRODUCT_ENDPOINT) // e.g., "/api/products"
public class ProductController {

    private final ProductService productService;

    // Constructor for dependency injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Create a new product.
     */
    @PostMapping()
    public ResponseEntity<ApiResponse> createProduct(@RequestBody @Valid AddProductRequest addProductRequest) {
        ProductDto productDto = productService.addProduct(addProductRequest);
        return ResponseEntity.ok(new ApiResponse(productDto, "Product created successfully"));
    }

    /**
     * Get a product by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        ProductDto productDto = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse(productDto, "Product found"));
    }

    /**
     * Get all products.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse(products, "Products retrieved successfully"));
    }

    /**
     * Update a product by its ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest updateProductRequest) {
        ProductDto updatedProduct = productService.updateProduct(updateProductRequest, id);
        if (updatedProduct == null) {
            throw new ResourceNotFoundException("Product not found", "Product");
        }
        return ResponseEntity.ok(new ApiResponse(updatedProduct, "Product updated successfully"));
    }

    /**
     * Delete a product by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse(true, "Product deleted successfully"));
    }

    /**
     * Get products by name.
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> getProductsByName(@RequestParam String name) {
        List<ProductDto> products = productService.getProductsByName(name);
        return ResponseEntity.ok(new ApiResponse(products, "Products found by name"));
    }

    /**
     * Get products by category.
     */
    @GetMapping("/filter/category")
    public ResponseEntity<ApiResponse> getProductsByCategory(@RequestParam String category) {
        List<ProductDto> products = productService.getAllProductsByCategory(category);
        return ResponseEntity.ok(new ApiResponse(products, "Products found by category"));
    }

    /**
     * Get products by brand.
     */
    @GetMapping("/filter/brand")
    public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brand) {
        List<ProductDto> products = productService.getAllProductsByBrand(brand);
        return ResponseEntity.ok(new ApiResponse(products, "Products found by brand"));
    }

    /**
     * Get products by category and brand.
     */
    @GetMapping("/filter/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(
            @RequestParam String category,
            @RequestParam String brand) {
        List<ProductDto> products = productService.getAllProductsByCategoryAndBrand(category, brand);
        return ResponseEntity.ok(new ApiResponse(products, "Products found by category and brand"));
    }

    /**
     * Get products by name and category.
     */
    @GetMapping("/filter/name-and-category")
    public ResponseEntity<ApiResponse> getProductsByNameAndCategory(
            @RequestParam String name,
            @RequestParam String category) {
        List<ProductDto> products = productService.getAllProductsByNameAndCategory(name, category);
        return ResponseEntity.ok(new ApiResponse(products, "Products found by name and category"));
    }

    /**
     * Get products by name and brand.
     */
    @GetMapping("/filter/name-and-brand")
    public ResponseEntity<ApiResponse> getProductsByNameAndBrand(
            @RequestParam String name,
            @RequestParam String brand) {
        List<ProductDto> products = productService.getAllProductsByBrandAndName(brand, name);
        return ResponseEntity.ok(new ApiResponse(products, "Products found by name and brand"));
    }

    /**
     * Get the count of products by category.
     */
    @GetMapping("/count/category")
    public ResponseEntity<ApiResponse> getProductsCountByCategory(@RequestParam String category) {
        int count = productService.getProductsCountByCategory(category);
        return ResponseEntity.ok(new ApiResponse(count, "Product count by category"));
    }

    /**
     * Get the count of products by brand.
     */
    @GetMapping("/count/brand")
    public ResponseEntity<ApiResponse> getProductsCountByBrand(@RequestParam String brand) {
        int count = productService.getProductsCountByBrand(brand);
        return ResponseEntity.ok(new ApiResponse(count, "Product count by brand"));
    }

    /**
     * Get the count of products by name.
     */
    @GetMapping("/count/name")
    public ResponseEntity<ApiResponse> getProductsCountByName(@RequestParam String name) {
        int count = productService.getProductsCountByName(name);
        return ResponseEntity.ok(new ApiResponse(count, "Product count by name"));
    }
}