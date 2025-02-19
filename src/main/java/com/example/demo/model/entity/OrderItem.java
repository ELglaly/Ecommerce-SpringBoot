package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Immutable
@NoArgsConstructor(force = true)
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private final int quantity;

    @Column(nullable = false, updatable = false, precision = 10, scale = 2)
    private final BigDecimal unitPrice;

    @Column(nullable = false, updatable = false, precision = 10, scale = 2)
    private final BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private final Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private final Product product;



    // Private constructor used by the builder
    private OrderItem(Builder builder) {
        this.quantity = builder.quantity;
        this.order = builder.order;
        this.product = builder.product;
        this.unitPrice = builder.product.getPrice();
        this.totalPrice = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }


    // Static builder class
    public static class Builder {
        private int quantity;
        private Product product;
        private Order order;

        public Builder quantity(int quantity) {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be positive");
            }
            this.quantity = quantity;
            return this;
        }

        public Builder product(Product product) {
            if (product == null) {
                throw new IllegalArgumentException("Product cannot be null");
            }
            this.product = product;
            return this;
        }

        public Builder order(Order order) {
            if (order == null) {
                throw new IllegalArgumentException("Order cannot be null");
            }
            this.order = order;
            return this;
        }

        public OrderItem build() {
            if (product == null || order == null) {
                throw new IllegalStateException("Product and Order must be set");
            }
            return new OrderItem(this);
        }
    }

}