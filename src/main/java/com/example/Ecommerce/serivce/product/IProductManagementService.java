package com.example.Ecommerce.serivce.product;

import com.example.Ecommerce.model.dto.ProductDto;
import com.example.Ecommerce.model.entity.Product;
import com.example.Ecommerce.request.product.AddProductRequest;
import com.example.Ecommerce.request.product.UpdateProductRequest;

public interface IProductManagementService {
    Product addProduct(AddProductRequest product);
    Product updateProduct(UpdateProductRequest product, Long id);
    void deleteProduct(Long id);
}
