package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @Transient
    private BigDecimal totalPrice;
    @Transient
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "card_id")
    private Cart cart;

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

    private CartItem(Builder builder) {
        this.quantity = builder.quantity;
        this.product = builder.product;
        this.cart = builder.cart;
    }

    public static class Builder {
        private int quantity;
        private Product product;
        private Cart cart;

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }
        public Builder product(Product product) {
            this.product = product;
            return this;
        }
        public Builder cart(Cart cart) {
            this.cart = cart;
            return this;
        }
        public CartItem build() {
            return new CartItem(this);
        }
    }


}
