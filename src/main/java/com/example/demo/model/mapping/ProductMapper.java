package com.example.demo.model.mapping;

import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Product;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ProductMapper {

    public static ProductDto toDto(Product product) {
        
        return ProductDto.builder()
                .id(product.getId())
                .brand(product.getBrand())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .categoryDto(CategoryMapper.toDto(product.getCategory()))
                .build();
    }


    public static Product toEntity(ProductDto productDto) {

        return Product.builder()
                .brand(productDto.getBrand())
                .description(productDto.getDescription())
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .category(CategoryMapper.toEntity(productDto.getCategoryDto()))
                .images(productDto.getImageDto())
                .build();
    }
}
