package com.example.demo.serivce.product;

import com.example.demo.model.dto.ProductDto;

import java.util.List;

public interface IProductSearchService {
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByName(String name);
    List<ProductDto> getAllProductsByCategory(String category);
    List<ProductDto> getAllProductsByBrand(String brand);
    List<ProductDto> getAllProductsByCategoryAndBrand(String category, String brand);
    List<ProductDto> getAllProductsByNameAndCategory(String name, String category);
    List<ProductDto> getAllProductsByBrandAndName(String brand, String name);
}
