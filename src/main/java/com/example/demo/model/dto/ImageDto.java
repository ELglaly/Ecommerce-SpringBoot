package com.example.demo.model.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Blob;


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

    @NotNull(message = "Product cannot be null")
    @Valid
    private ProductDto productDTO;



    private ImageDto (Builder builder)
    {
         this.id = builder.id;
         this.url = builder.url;
         this.image = builder.image;
         this.name = builder.name;
         this.productDTO = builder.productDTO;
    }

    public static class Builder
    {
        private Long id;
        private String url;
        private Blob image;
        private String name;
        private ProductDto productDTO;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }

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

        public Builder productDTO(ProductDto productDTO) {
            this.productDTO = productDTO;
            return this;
        }

        public ImageDto build()
        {
            return new ImageDto(this);
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public ProductDto getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDto productDTO) {
        this.productDTO = productDTO;
    }
}
