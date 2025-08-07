package com.example.ecommerce.mapper;


import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.request.product.AddProductRequest;
import com.example.ecommerce.request.product.UpdateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * ProductMapper is an interface for mapping between Product entity and ProductDTO, AddProductRequest, UpdateProductRequest.
 * It uses MapStruct to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring")
// This interface is used to map between Product entity and ProductDTO, AddProductRequest, Update
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);


    ProductDTO toDto(Product product);
    Product toEntity(ProductDTO productDto);
    Product toEntity(AddProductRequest addProductRequest);
    Product toEntity(UpdateProductRequest updateProductRequest);
}
