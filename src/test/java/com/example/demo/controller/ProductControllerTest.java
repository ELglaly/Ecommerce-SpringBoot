package com.example.demo.controller;

import com.example.demo.constants.ApiConstants;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.request.product.AddProductRequest;
import com.example.demo.request.product.UpdateProductRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.serivce.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private AddProductRequest addProductRequest;
    private UpdateProductRequest updateProductRequest;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        // Setup mock objects
        addProductRequest = new AddProductRequest();
        addProductRequest.setName("Product1");
        updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setName("Updated Product");

        productDto = new ProductDto();
        productDto.setName("Product1");
    }

    @Test
    void createProduct() throws Exception {
        when(productService.addProduct(addProductRequest)).thenReturn(productDto);
        testCreateProductWithEmptyName();
        testCreateProductWithNameTooLong();
        testCreateProductWithNameTooShort();
        testCreateProductWithNullName();

    }

    void testCreateProductWithNullName() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": null, \"category\": \"Category1\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Name cannot be null"));
    }

    void testCreateProductWithEmptyName() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"\", \"category\": \"Category1\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Name cannot be empty"));
    }

    void testCreateProductWithNameTooShort() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"A\", \"category\": \"Category1\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Name must be between 2 and 100 characters"));
    }

    void testCreateProductWithNameTooLong() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"" + "A".repeat(101) + "\", \"category\": \"Category1\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Name must be between 2 and 100 characters"));
    }

    @Test
    void getProductById() throws Exception {
        when(productService.getProductById(1L)).thenReturn(productDto);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Product found"))
                .andExpect(jsonPath("$.data.name").value("Product1"));
    }

    @Test
    void getProductByIdNotFound() throws Exception {
        when(productService.getProductById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product not found"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void getAllProducts() throws Exception {
        List<ProductDto> products = List.of(productDto);
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Products retrieved successfully"))
                .andExpect(jsonPath("$.data[0].name").value("Product1"));
    }

    @Test
    void updateProduct() throws Exception {
        when(productService.updateProduct(updateProductRequest, 1L)).thenReturn(productDto);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Updated Product\", \"category\": \"Updated Category\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Product updated successfully"))
                .andExpect(jsonPath("$.data.name").value("Updated Product"));
    }

    @Test
    void updateProductNotFound() throws Exception {
        when(productService.updateProduct(updateProductRequest, 1L)).thenReturn(null);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Updated Product\", \"category\": \"Updated Category\" }"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product not found"));
    }

    @Test
    void deleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Product deleted successfully"));
    }

    @Test
    void getProductsByName() throws Exception {
        when(productService.getProductsByName("Product1")).thenReturn(List.of(productDto));

        mockMvc.perform(get("/api/products/search?name=Product1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Products found by name"))
                .andExpect(jsonPath("$.data[0].name").value("Product1"));
    }

    @Test
    void getProductsByCategory() throws Exception {
        when(productService.getAllProductsByCategory("Category1")).thenReturn(List.of(productDto));

        mockMvc.perform(get("/api/products/filter/category?category=Category1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Products found by category"))
                .andExpect(jsonPath("$.data[0].name").value("Product1"));
    }

    @Test
    void getProductsByBrand() throws Exception {
        when(productService.getAllProductsByBrand("Brand1")).thenReturn(List.of(productDto));

        mockMvc.perform(get("/api/products/filter/brand?brand=Brand1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Products found by brand"))
                .andExpect(jsonPath("$.data[0].name").value("Product1"));
    }

    @Test
    void getProductsByCategoryAndBrand() throws Exception {
        when(productService.getAllProductsByCategoryAndBrand("Category1", "Brand1")).thenReturn(List.of(productDto));

        mockMvc.perform(get("/api/products/filter/category-and-brand?category=Category1&brand=Brand1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Products found by category and brand"))
                .andExpect(jsonPath("$.data[0].name").value("Product1"));
    }

    @Test
    void getProductsByNameAndCategory() throws Exception {
        when(productService.getAllProductsByNameAndCategory("Product1", "Category1")).thenReturn(List.of(productDto));

        mockMvc.perform(get("/api/products/filter/name-and-category?name=Product1&category=Category1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Products found by name and category"))
                .andExpect(jsonPath("$.data[0].name").value("Product1"));
    }

    @Test
    void getProductsByNameAndBrand() throws Exception {
        when(productService.getAllProductsByBrandAndName("Brand1", "Product1")).thenReturn(List.of(productDto));

        mockMvc.perform(get("/api/products/filter/name-and-brand?name=Product1&brand=Brand1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Products found by name and brand"))
                .andExpect(jsonPath("$.data[0].name").value("Product1"));
    }

    @Test
    void getProductsCountByCategory() throws Exception {
        when(productService.getProductsCountByCategory("Category1")).thenReturn(10);

        mockMvc.perform(get("/api/products/count/category?category=Category1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Product count by category"))
                .andExpect(jsonPath("$.data").value(10));
    }

    @Test
    void getProductsCountByBrand() throws Exception {
        when(productService.getProductsCountByBrand("Brand1")).thenReturn(5);

        mockMvc.perform(get("/api/products/count/brand?brand=Brand1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Product count by brand"))
                .andExpect(jsonPath("$.data").value(5));
    }

    @Test
    void getProductsCountByName() throws Exception {
        when(productService.getProductsCountByName("Product1")).thenReturn(3);

        mockMvc.perform(get("/api/products/count/name?name=Product1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Product count by name"))
                .andExpect(jsonPath("$.data").value(3));
    }
}
