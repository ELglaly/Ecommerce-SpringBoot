package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalPrice;
    private int totalAmount=0;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
        setTotalAmount();
        updateTotalPrice();
    }
    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
        setTotalAmount();
        updateTotalPrice();
    }

    public void updateTotalPrice() {
        this.totalPrice = items.stream().map(items ->{
            BigDecimal unitPrice = items.getUnitPrice();
                    if(unitPrice==null)
                    {
                        return BigDecimal.ZERO;
                    }
                   return unitPrice.multiply(new BigDecimal(items.getQuantity()));
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setTotalAmount() {

        for (CartItem item : items) {
            this.totalAmount += item.getQuantity();
        }
    }
    public int getTotalAmount() {
       return totalAmount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Set<CartItem> getItems() {
        return items;
    }

    public void setItems(Set<CartItem> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
