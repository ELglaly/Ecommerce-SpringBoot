package com.example.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;



@Data
public class CategoryDto {

    private Long id;
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @Pattern(regexp = "^[^\\d]*$", message = "Name must not contain digits")
    private String name;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be Empty")
    @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
    private String description;
    private List<ProductDto> productsDto;


    public CategoryDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.productsDto = builder.productsDto;
    }
    public CategoryDto() { }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private List<ProductDto> productsDto;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder productsDto(List<ProductDto> productsDto) {
            this.productsDto = productsDto;
            return this;
        }
        public CategoryDto build() {
            return new CategoryDto(this);
        }
    }

}
