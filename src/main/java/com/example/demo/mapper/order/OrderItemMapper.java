package com.example.demo.mapper.order;

import com.example.demo.mapper.IProductMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.dto.order.OrderItemDto;
import com.example.demo.model.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper implements IOrderItemMapper {
    private IProductMapper productMapper;

    public OrderItemMapper(IProductMapper productMapper) {
        this.productMapper = productMapper;
    }
    @Override
    public OrderItemDto toDto(OrderItem orderItem) {
        return new OrderItemDto.Builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .unitePrice(orderItem.getUnitePrice())
                .productDto(productMapper.toDto(orderItem.getProduct()))
                .build();
    }
}
