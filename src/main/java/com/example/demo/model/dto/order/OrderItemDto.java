package com.example.demo.model.dto.order;

import com.example.demo.model.dto.ProductDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
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

}
