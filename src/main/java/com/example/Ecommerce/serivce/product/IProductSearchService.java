package com.example.Ecommerce.serivce.product;

import com.example.Ecommerce.model.dto.ProductDto;
import com.example.Ecommerce.model.entity.Product;

import java.util.List;

public interface IProductSearchService {
    ProductDto getProductDtoById(Long id);
    Product getProductById(Long id);
    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByName(String name);
    List<ProductDto> getAllProductsByCategory(String category);
    List<ProductDto> getAllProductsByBrand(String brand);
    List<ProductDto> getAllProductsByCategoryAndBrand(String category, String brand);
    List<ProductDto> getAllProductsByNameAndCategory(String name, String category);
    List<ProductDto> getAllProductsByBrandAndName(String brand, String name);
}
