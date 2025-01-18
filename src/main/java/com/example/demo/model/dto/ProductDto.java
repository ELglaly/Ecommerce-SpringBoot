package com.example.demo.model.dto;

import com.example.demo.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@Builder
public class ProductDto {

    public Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int quantity;
    private List<ImageDto> imageDto ;
    private CategoryDto categoryDto ;

}
