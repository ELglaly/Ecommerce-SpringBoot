package com.example.demo.mapper;

import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Product;
import com.example.demo.request.product.AddProductRequest;
import com.example.demo.request.product.UpdateProductRequest;
import org.apache.catalina.mapper.Mapper;

public interface IProductMapper extends IEntityToDtoMapper<Product, ProductDto>,
        IDtoToEntityMapper<Product, ProductDto> ,
        IUpdateRequestToEntityMapper<Product, UpdateProductRequest>,
        IAddRequestToEntityMapper<Product, AddProductRequest>
{

}
