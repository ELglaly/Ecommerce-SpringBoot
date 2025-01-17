package com.example.demo.request;

import com.example.demo.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AddProductRequest {
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int quantity;
    private Category category;
}
