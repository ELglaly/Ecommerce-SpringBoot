package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name="Products")
@Setter
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int quantity;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name="product_id",referencedColumnName = "id")
    private List<Image> images;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;

    public Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.brand = builder.brand;
        this.description = builder.description;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.images = builder.images;
        this.category = builder.category;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String brand;
        private String description;
        private BigDecimal price;
        private int quantity;
        private List<Image> images;
        private Category category;
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
        public Builder images(List<Image> images) {
            this.images = images;
            return this;
        }
        public Builder category(Category category) {
            this.category = category;
            return this;
        }
        public Product build() {
            return new Product(this);
        }
    }

}
