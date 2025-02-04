package com.example.demo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive
    @Transient
    private BigDecimal totalPrice;
    @PositiveOrZero
    private int totalAmount=0;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
        setTotalAmount();
    }
    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
        setTotalAmount();
    }

    @PrePersist
    public void setTotalAmount() {

        for (CartItem item : items) {
            this.totalAmount += item.getQuantity();
        }
    }

}
