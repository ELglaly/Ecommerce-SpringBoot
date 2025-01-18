package com.example.demo.model.dto;


import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private Long id;
    private String name;
    private String description;
    private List<ProductDto> productsDto;


}
