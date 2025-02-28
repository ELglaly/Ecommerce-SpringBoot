package com.example.Ecommerce.model.dto.order;

import com.example.Ecommerce.enums.OrderStatus;
import com.example.Ecommerce.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderDto {

    private Long orderId;
    private LocalDateTime orderDate;
    private BigDecimal orderTotalPrice;
    private OrderStatus orderStatus;
    private Set<OrderItemDto> orderItems=new HashSet<>();
    private UserDto user;


    public static class Builder {
        private Long orderId;
        private LocalDateTime orderDate;
        private BigDecimal orderTotalPrice;
        private OrderStatus orderStatus;
        private Set<OrderItemDto> orderItems;
        private UserDto user;
        public Builder orderId(Long orderId) {
            this.orderId = orderId;
            return this;
        }
        public Builder orderDate(LocalDateTime orderDate) {
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
             return new OrderDto(this);
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
