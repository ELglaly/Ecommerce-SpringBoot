package com.example.Ecommerce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(nullable = false)
    private int quantity;

    @Transient
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Transient
    private BigDecimal unitPrice;

    @NotNull(message = "Product is mandatory")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull(message = "Cart is mandatory")
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;


    @PostLoad
    public void postLoad() {
        // Set unitePrice to current date
        unitPrice = product.getPrice();  // Set default date as current date
        // Set default totalPrice if not set
        totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity)); // Set default order status
    }

    private CartItem(Builder builder) {
        this.quantity = builder.quantity;
        this.product = builder.product;
        this.unitPrice=product.getPrice();
        this.totalPrice=unitPrice.multiply(BigDecimal.valueOf(quantity));
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
