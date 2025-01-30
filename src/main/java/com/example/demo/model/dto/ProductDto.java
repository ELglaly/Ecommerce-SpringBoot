package com.example.demo.model.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.math.BigDecimal;
import java.util.List;


public class ProductDto {

    private Long id;
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Brand cannot be null")
    @NotBlank(message = "Brand cannot be empty")
    @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters")
    private String brand;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be Empty")
    @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have up to 2 decimal places")
    private BigDecimal price;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 10000, message = "Quantity cannot exceed 10,000")
    private int quantity;

    @NotNull(message = "Category cannot be null")
    @Valid
    private CategoryDto categoryDto;

    @Valid
    private List<ImageDto> ImageDto;



    public ProductDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.brand = builder.brand;
        this.description = builder.description;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.categoryDto = builder.categoryDto;
        this.ImageDto=builder.ImageDto;

    }

    public static class Builder {
        private Long id;
        private String name;
        private String brand;
        private String description;
        private BigDecimal price;
        private int quantity;
        private CategoryDto categoryDto;
        private List<ImageDto> ImageDto;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }
        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }
        public Builder categoryDto(CategoryDto categoryDto) {
            this.categoryDto = categoryDto;
            return this;
        }
        public ProductDto build() {
            return new ProductDto(this);

        }
        public Builder ImageDto(List<ImageDto> ImageDto) {
            this.ImageDto = ImageDto;
            return this;
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }
    public List<ImageDto> getImageDto() {
        return ImageDto;
    }
    public void setImageDto(List<ImageDto> imageDto) {
        this.ImageDto = imageDto;
    }

}
