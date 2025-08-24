package com.example.ecommerce.controller;

import com.example.ecommerce.constants.ApiConstants;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.exceptions.product.ProductNotFoundException;
import com.example.ecommerce.mapper.ProductMapper;
import com.example.ecommerce.request.product.AddProductRequest;
import com.example.ecommerce.request.product.UpdateProductRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.serivce.product.IProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.PRODUCT_ENDPOINT) // e.g., "/api/products"
public class ProductController {

    private final IProductService productService;
    private final ProductMapper productMapper;

    // Constructor for dependency injection
    public ProductController(IProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }


    @PostMapping()
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody @Valid AddProductRequest addProductRequest) {
        ProductDTO product = productService.addProduct(addProductRequest);
        return ResponseEntity.ok(new ApiResponse(product, "Product created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        ProductDTO productDto = productService.getProductDtoById(id);
        return ResponseEntity.ok(new ApiResponse(productDto, "Product found"));
    }


    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts()
                .stream()
                .map(productMapper::toDto).toList();
        return ResponseEntity.ok(new ApiResponse(products, "Products retrieved successfully"));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest updateProductRequest) {
        ProductDTO updatedProduct = productService.updateProduct(updateProductRequest, id);
        if (updatedProduct == null) {
            throw new ProductNotFoundException("Product not found");
        }
        return ResponseEntity.ok(new ApiResponse(updatedProduct, "Product updated successfully"));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse(true, "Product deleted successfully"));
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse> getProductsByName(@RequestParam String name) {
        List<ProductDTO> products = productService.getProductsByName(name)
                .stream()
                .map(productMapper::toDto).toList();
        return ResponseEntity.ok(new ApiResponse(products, "Products found by name"));
    }

    @GetMapping("/filter/category")
    public ResponseEntity<ApiResponse> getProductsByCategory(@RequestParam String category) {
        List<ProductDTO> products = productService.getAllProductsByCategory(category)
                .stream()
                .map(productMapper::toDto).toList();
        return ResponseEntity.ok(new ApiResponse(products, "Products found by category"));
    }


    @GetMapping("/filter/brand")
    public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brand) {
        List<ProductDTO> products = productService.getAllProductsByBrand(brand)
                .stream()
                .map(productMapper::toDto).toList();
        return ResponseEntity.ok(new ApiResponse(products, "Products found by brand"));
    }


    @GetMapping("/filter/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(
            @RequestParam String category,
            @RequestParam String brand) {
        List<ProductDTO> products = productService.getAllProductsByCategoryAndBrand(category, brand)
                .stream()
                .map(productMapper::toDto).toList();
        return ResponseEntity.ok(new ApiResponse(products, "Products found by category and brand"));
    }

    @GetMapping("/filter/name-and-category")
    public ResponseEntity<ApiResponse> getProductsByNameAndCategory(
            @RequestParam String name,
            @RequestParam String category) {
        List<ProductDTO> products = productService.getAllProductsByNameAndCategory(name, category)
                .stream()
                .map(productMapper::toDto).toList();
        return ResponseEntity.ok(new ApiResponse(products, "Products found by name and category"));
    }

    @GetMapping("/filter/name-and-brand")
    public ResponseEntity<ApiResponse> getProductsByNameAndBrand(
            @RequestParam String name,
            @RequestParam String brand) {
        List<ProductDTO> products = productService.getAllProductsByBrandAndName(brand, name)
                .stream()
                .map(productMapper::toDto).toList();
        return ResponseEntity.ok(new ApiResponse(products, "Products found by name and brand"));
    }


    @GetMapping("/count/category")
    public ResponseEntity<ApiResponse> getProductsCountByCategory(@RequestParam String category) {
        int count = productService.getProductsCountByCategory(category);
        return ResponseEntity.ok(new ApiResponse(count, "Product count by category"));
    }

    @GetMapping("/count/brand")
    public ResponseEntity<ApiResponse> getProductsCountByBrand(@RequestParam String brand) {
        int count = productService.getProductsCountByBrand(brand);
        return ResponseEntity.ok(new ApiResponse(count, "Product count by brand"));
    }

    @GetMapping("/count/name")
    public ResponseEntity<ApiResponse> getProductsCountByName(@RequestParam String name) {
        int count = productService.getProductsCountByName(name);
        return ResponseEntity.ok(new ApiResponse(count, "Product count by name"));
    }
}