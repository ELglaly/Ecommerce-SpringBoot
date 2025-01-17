package com.example.demo.serivce.product;

import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.model.entity.Product;
import com.example.demo.request.AddProductRequest;
import com.example.demo.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest product);
    Product getProductById(Long id) throws ProductNotFoundException;
    List<Product> getAllProducts();
    Product updateProduct(UpdateProductRequest product,Long id);
    void deleteProduct(Long id);
    List<Product> getProductsByName(String name);
    List<Product> getAllProductsByCategory(String category);
    List<Product> getAllProductsByBrand(String brand);
    List<Product> getAllProductsByCategoryAndBrand(String category, String brand);
    List<Product> getAllProductsByNameAndCategory(String brand, String category);
    List<Product> getAllProductsByBrandAndName(String brand, String category);
    int getProductsCountByCategory(String category);
    int getProductsCountByBrand(String brand);
    int getProductsCountByName(String name);
}
