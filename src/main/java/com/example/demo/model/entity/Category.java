package com.example.demo.model.entity;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.dto.ProductDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name ="category_id", referencedColumnName = "id")
    private List<Product> products;


    public Category(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.products = builder.products;
    }
    public Category() {}

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private List<Product> products;
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
        public Builder products(List<Product> products) {
            this.products = products;
            return this;
        }
        public Category build() {
            return new Category(this);
        }
    }

}
