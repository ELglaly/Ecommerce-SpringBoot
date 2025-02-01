package com.example.demo.model.dto.cart;

import com.example.demo.model.entity.CartItem;
import com.example.demo.model.entity.User;

import java.math.BigDecimal;
import java.util.Set;

public class CartDto {

    private Long id;
    private BigDecimal totalPrice;
    private int totalAmount=0;
    private Set<CartItem> items;
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalAmount() {
        return totalAmount;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
