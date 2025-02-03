package com.example.demo.model.dto.order;

import com.example.demo.model.dto.ProductDto;

import java.math.BigDecimal;

public class OrderItemDto {

    private Long id;
    private int quantity;
    private BigDecimal unitePrice;
    private BigDecimal totalPrice;
    private OrderDto orderDto;
    private ProductDto productDto;

    private OrderItemDto(Builder builder) {
        this.id = builder.id;
        this.quantity = builder.quantity;
        this.unitePrice = builder.unitePrice;
        this.totalPrice = builder.totalPrice;
        this.orderDto = builder.orderDto;
        this.productDto = builder.productDto;
    }

    public static class Builder {
        private Long id;
        private int quantity;
        private BigDecimal unitePrice;
        private BigDecimal totalPrice;
        private OrderDto orderDto;
        private ProductDto productDto;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }
        public Builder unitePrice(BigDecimal unitePrice) {
            this.unitePrice = unitePrice;
            return this;
        }
        public Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }
        public Builder orderDto(OrderDto orderDto) {
            this.orderDto = orderDto;
            return this;
        }
        public Builder productDto(ProductDto productDto) {
            this.productDto = productDto;
            return this;
        }
        public OrderItemDto build() {
            return new OrderItemDto(this);
        }
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitePrice() {
        return unitePrice;
    }

    public void setUnitePrice(BigDecimal unitePrice) {
        this.unitePrice = unitePrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderDto getOrderId() {
        return orderDto;
    }

}
