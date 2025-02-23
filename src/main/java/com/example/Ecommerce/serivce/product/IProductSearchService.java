package com.example.Ecommerce.serivce.product;

import com.example.Ecommerce.model.entity.Product;
import com.example.Ecommerce.model.dto.ProductDto;
import com.example.Ecommerce.model.entity.Product;

import java.util.List;

public interface IProductSearchService {
    ProductDto getProductDtoById(Long id);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByName(String name);
    List<Product> getAllProductsByCategory(String category);
    List<Product> getAllProductsByBrand(String brand);
    List<Product> getAllProductsByCategoryAndBrand(String category, String brand);
    List<Product> getAllProductsByNameAndCategory(String name, String category);
    List<Product> getAllProductsByBrandAndName(String brand, String name);
}
