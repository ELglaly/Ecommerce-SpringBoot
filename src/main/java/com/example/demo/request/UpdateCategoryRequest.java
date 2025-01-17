package com.example.demo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCategoryRequest {
    private String name;
    private String description;
}
