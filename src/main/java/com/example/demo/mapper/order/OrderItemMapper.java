package com.example.demo.mapper.order;

import org.springframework.stereotype.Component;

import com.example.demo.mapper.IProductMapper;
import com.example.demo.model.dto.order.OrderItemDto;
import com.example.demo.model.entity.OrderItem;

@Component
public class OrderItemMapper implements IOrderItemMapper {
    private final IProductMapper productMapper;

    public OrderItemMapper(IProductMapper productMapper) {
        this.productMapper = productMapper;
    }
    @Override
    public OrderItemDto toDto(OrderItem orderItem) {
        return new OrderItemDto.Builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .unitePrice(orderItem.getUnitPrice())
                .productDto(productMapper.toDto(orderItem.getProduct()))
                .build();
    }

    @Override
    public OrderItem toEntityFromDto(OrderItemDto dto) {
        return null;
    }
}
