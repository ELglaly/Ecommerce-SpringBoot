package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @Transient
    private BigDecimal unitePrice;
    @Transient
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Builder pattern constructor to create instances
    private OrderItem(Builder builder) {
        this.quantity = builder.quantity;
        this.unitePrice = builder.unitePrice;
        this.totalPrice = builder.totalPrice;
        this.order = builder.order;
        this.product = builder.product;
    }

    // Static builder class
    public static class Builder {
        private int quantity;
        private BigDecimal unitePrice;
        private BigDecimal totalPrice;
        private Product product;
        private Order order;

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder unitePrice(BigDecimal unitePrice) {
            this.unitePrice = unitePrice;
            return this;
        }

        public Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
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
