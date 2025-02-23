package com.example.Ecommerce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Immutable
@NoArgsConstructor(force = true)
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @NotNull(message = "Product is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull(message = "Order is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


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
            this.order = order;
            return this;
        }

        public OrderItem build() {
            if (product == null ) {
                throw new IllegalStateException("Product must be set");
            }
            return new OrderItem(this);
        }
    }

}