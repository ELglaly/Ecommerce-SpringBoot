package com.example.ecommerce.serivce.product;

public interface IProductAnalyticsService {
    int getProductsCountByCategory(String category);
    int getProductsCountByBrand(String brand);
    int getProductsCountByName(String name);
}
