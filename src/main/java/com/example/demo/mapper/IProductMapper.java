package com.example.demo.mapper;

import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Product;
import com.example.demo.request.product.AddProductRequest;
import com.example.demo.request.product.UpdateProductRequest;

public interface IProductMapper {

    Product toEntity(AddProductRequest product);
    ProductDto toDto(Product product);
    Product toEntity(UpdateProductRequest product);
    Product toEntity(ProductDto product);

}
