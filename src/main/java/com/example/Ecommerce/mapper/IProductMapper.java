package com.example.Ecommerce.mapper;

import com.example.Ecommerce.model.dto.ProductDto;
import com.example.Ecommerce.model.entity.Product;
import com.example.Ecommerce.request.product.AddProductRequest;
import com.example.Ecommerce.request.product.UpdateProductRequest;

public interface IProductMapper extends IEntityToDtoMapper<Product, ProductDto>,
        IDtoToEntityMapper<Product, ProductDto> ,
        IUpdateRequestToEntityMapper<Product, UpdateProductRequest>,
        IAddRequestToEntityMapper<Product, AddProductRequest>
{

}
