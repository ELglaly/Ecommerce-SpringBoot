package com.example.demo.model.dto;


import com.example.demo.exceptions.InvalidFieldException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Blob;

@Data
public class ImageDto {

    private Long id;
    @NotNull(message = "URL cannot be null")
    @NotBlank(message = "URL cannot be empty")
    private String url;

    @NotNull(message = "Image cannot be null")
    private Blob image;

    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be empty")
    @Size(min = 1, max = 255, message = "name must be between 1 and 255 characters")
    private String name;

    private String type;

    @NotNull(message = "Product cannot be null")
    @Valid
    private ProductDto productDTO;

    @Valid
    private CategoryDto categoryDTO;



    private ImageDto (Builder builder)
    {
         this.url = builder.url;
         this.image = builder.image;
         this.name = builder.name;
         this.type = builder.type;
         this.productDTO = builder.productDTO;
         this.categoryDTO = builder.categoryDTO;
    }

    public static class Builder
    {

        private String url;
        private Blob image;
        private String name;
        private String type;
        private ProductDto productDTO;
        private CategoryDto categoryDTO;


        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder image(Blob image) {
            this.image = image;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder productDTO(ProductDto productDTO) {
            this.productDTO = productDTO;
            return this;
        }
        public Builder categoryDTO(CategoryDto categoryDTO) {
            this.categoryDTO=categoryDTO;
            return this;
        }
        public ImageDto build()
        {
            return new ImageDto(this);
        }

    }
}
