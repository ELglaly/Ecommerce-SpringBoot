package com.example.demo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddCategoryRequest {

    private Long id;
    private String name;
    private String description;
}
