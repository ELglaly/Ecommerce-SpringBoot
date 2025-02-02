package com.example.demo.serivce.product;

import com.example.demo.model.dto.ProductDto;
import com.example.demo.request.product.AddProductRequest;
import com.example.demo.request.product.UpdateProductRequest;

public interface IProductManagementService {
    ProductDto addProduct(AddProductRequest product);
    ProductDto updateProduct(UpdateProductRequest product, Long id);
    void deleteProduct(Long id);
}
