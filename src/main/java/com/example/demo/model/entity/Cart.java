package com.example.demo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
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
    @Transient
    private BigDecimal totalPrice=BigDecimal.ZERO;
    @PositiveOrZero
    private int totalAmount=0;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items=new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
        updateTotals();
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
        updateTotals();
    }

    private void updateTotals() {
        this.totalPrice = items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalAmount = items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
    @PostLoad
    public void post()
    {
        totalPrice=BigDecimal.ZERO;
        totalAmount=0;
        items.forEach(
                item -> {
                    totalPrice= totalPrice.add(item.getTotalPrice());
                    totalAmount+= item.getQuantity();
                });
    }

}
