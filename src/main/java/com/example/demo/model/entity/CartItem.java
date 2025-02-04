package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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


}
