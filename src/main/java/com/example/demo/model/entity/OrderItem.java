package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @Transient
    private BigDecimal unitPrice;
    @Transient
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @PostLoad
    public void postLoad() {
        // Set unitePrice to current date
        unitPrice = product.getPrice();  // Set default date as current date
        // Set default totalPrice if not set
        totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity)); // Set default order status
    }
    @PrePersist
    public void prePersist() {
        // Set unitePrice to current date
        unitPrice = product.getPrice();  // Set default date as current date
        // Set default totalPrice if not set
        totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity)); // Set default order status
    }

    // Builder pattern constructor to create instances
    private OrderItem(Builder builder) {
        this.quantity = builder.quantity;
        this.order = builder.order;
        this.product = builder.product;
    }

    // Static builder class
    public static class Builder {
        private int quantity;
        private Product product;
        private Order order;

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder order(Order order) {
            this.order = order;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
