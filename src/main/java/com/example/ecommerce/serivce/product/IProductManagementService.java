package com.example.ecommerce.serivce.product;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.request.product.AddProductRequest;
import com.example.ecommerce.request.product.UpdateProductRequest;

public interface IProductManagementService {
    ProductDTO addProduct(AddProductRequest product);
    ProductDTO updateProduct(UpdateProductRequest product, Long id);
    void deleteProduct(Long id);
}
