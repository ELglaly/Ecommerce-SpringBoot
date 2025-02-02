package com.example.demo.serivce.product;

public interface IProductAnalyticsService {
    int getProductsCountByCategory(String category);
    int getProductsCountByBrand(String brand);
    int getProductsCountByName(String name);
}
