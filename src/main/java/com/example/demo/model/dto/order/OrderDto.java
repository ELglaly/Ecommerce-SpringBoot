package com.example.demo.model.dto.order;

import com.example.demo.enums.OrderStatus;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long orderId;
    private LocalDate orderDate;
    private BigDecimal orderTotalPrice;
    private OrderStatus orderStatus;
    private Set<OrderItemDto> orderItems=new HashSet<>();
    private UserDto user;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Set<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }


    public static class Builder {
        private Long orderId;
        private LocalDate orderDate;
        private BigDecimal orderTotalPrice;
        private OrderStatus orderStatus;
        private Set<OrderItemDto> orderItems;
        private UserDto user;
        public Builder orderId(Long orderId) {
            this.orderId = orderId;
            return this;
        }
        public Builder orderDate(LocalDate orderDate) {
            this.orderDate = orderDate;
            return this;
        }
        public Builder orderTotalPrice(BigDecimal orderTotalPrice) {
            this.orderTotalPrice = orderTotalPrice;
            return this;
        }
        public Builder orderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }
        public Builder orderItems(Set<OrderItemDto> orderItems) {
            this.orderItems = orderItems;
            return this;
        }
        public Builder user(UserDto user) {
            this.user = user;
            return this;
        }
        public OrderDto build() {
            OrderDto orderDto = new OrderDto();
        }
    }

    private OrderDto(Builder builder) {
        this.orderId = builder.orderId;
        this.orderDate = builder.orderDate;
        this.orderTotalPrice = builder.orderTotalPrice;
        this.orderStatus = builder.orderStatus;
        this.orderItems = builder.orderItems;
        this.user = builder.user;
    }
}
