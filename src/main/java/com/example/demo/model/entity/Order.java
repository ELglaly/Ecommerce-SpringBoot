package com.example.demo.model.entity;

import com.example.demo.enums.OrderStatus;
import com.example.demo.exceptions.InvalidFieldException;
import com.example.demo.model.dto.cart.CartItemDto;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @NotNull
    private  LocalDate orderDate;
    @PositiveOrZero
    @Transient
    private  BigDecimal orderTotalPrice;
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private  User user;


    @PrePersist
    public void prePersist() {
        // Set orderDate to current date if not already set
        if (orderDate == null) {
            orderDate = LocalDate.now();  // Set default date as current date
        }
        // Set default orderStatus if not set
        if (orderStatus == null) {
            orderStatus = OrderStatus.PENDING;  // Set default order status
        }
    }

    private Order(Builder builder) {
        this.orderTotalPrice = builder.orderTotalPrice;
        this.orderItems = builder.orderItems;
        this.user = builder.user;
    }

    public static class Builder{
        private BigDecimal orderTotalPrice;
        private Set<OrderItem> orderItems=new HashSet<>();
        private User user;

        public Builder orderTotalPrice(BigDecimal orderTotalPrice) {
            this.orderTotalPrice = orderTotalPrice;
            return this;
        }
        public Builder user(User user) {
            this.user = user;
            return this;
        }
        public Builder orderItems(Set<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }
        public Order build(){
            return new Order(this);
        }
    }

}
