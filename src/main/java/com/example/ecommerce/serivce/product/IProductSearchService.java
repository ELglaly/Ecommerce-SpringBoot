package com.example.ecommerce.serivce.product;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.dto.ProductDTO;

import java.util.List;

public interface IProductSearchService {
    ProductDTO getProductDtoById(Long id);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByName(String name);
    List<Product> getAllProductsByCategory(String category);
    List<Product> getAllProductsByBrand(String brand);
    List<Product> getAllProductsByCategoryAndBrand(String category, String brand);
    List<Product> getAllProductsByNameAndCategory(String name, String category);
    List<Product> getAllProductsByBrandAndName(String brand, String name);
}
