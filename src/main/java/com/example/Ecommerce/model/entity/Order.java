package com.example.Ecommerce.model.entity;

import com.example.Ecommerce.enums.OrderStatus;
import com.example.Ecommerce.exceptions.InvalidFieldException;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Immutable
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "order_total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal orderTotalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems = new HashSet<>();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;


    @PrePersist
    public void prePersist() {
        if (this.orderStatus == null) {
            this.orderStatus = OrderStatus.PENDING; // Set default order status
        }
    }

    private Order(Builder builder) {
        this.orderTotalPrice = builder.orderTotalPrice;
        this.orderItems = builder.orderItems;
        this.user = builder.user;
        this.orderStatus=OrderStatus.PENDING;
        this.createdAt=LocalDateTime.now();
    }


    public static class Builder{
        private Long orderId;
        private BigDecimal orderTotalPrice;
        private Set<OrderItem> orderItems=new HashSet<>();
        private User user;

        public Builder orderId(Long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder orderTotalPrice(BigDecimal orderTotalPrice) {
            if(orderTotalPrice==null || orderTotalPrice.compareTo(BigDecimal.ZERO)<=0){
                throw new InvalidFieldException("OrderTotalPrice cannot be null or less than or equal to zero");
            }
            this.orderTotalPrice = orderTotalPrice;
            return this;
        }
        public Builder user(User user) {
            if(user==null){
                throw new InvalidFieldException("User cannot be null");
            }
            this.user = user;
            return this;
        }
        public Builder orderItems(Set<OrderItem> orderItems) {
            if(orderItems==null || orderItems.isEmpty()){
                throw new InvalidFieldException("OrderItems cannot be null or Empty");
            }
            this.orderItems = orderItems;
            return this;
        }
        public Order build(){
            return new Order(this);
        }
    }


    public void updateStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

}
