package com.example.demo.serivce.product;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Product;
import com.example.demo.request.AddProductRequest;
import com.example.demo.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {

    ProductDto addProduct(AddProductRequest product);
    ProductDto getProductById(Long id) ;
    List<ProductDto> getAllProducts();
    ProductDto updateProduct(UpdateProductRequest product,Long id);
    void deleteProduct(Long id);
    List<ProductDto> getProductsByName(String name);
    List<ProductDto> getAllProductsByCategory(String category);
    List<ProductDto> getAllProductsByBrand(String brand);
    List<ProductDto> getAllProductsByCategoryAndBrand(String category, String brand);
    List<ProductDto> getAllProductsByNameAndCategory(String brand, String category);
    List<ProductDto> getAllProductsByBrandAndName(String brand, String category);
    int getProductsCountByCategory(String category);
    int getProductsCountByBrand(String brand);
    int getProductsCountByName(String name);
}
