package com.example.Ecommerce.mapper.order;

import com.example.Ecommerce.model.dto.ProductDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.Ecommerce.model.dto.order.OrderItemDto;
import com.example.Ecommerce.model.entity.OrderItem;

@Component
public class OrderItemMapper implements IOrderItemMapper {
    private final ModelMapper modelMapper;

    public OrderItemMapper(ModelMapper modelMapper) {
       this.modelMapper = modelMapper;
    }
    @Override
    public OrderItemDto toDto(OrderItem orderItem) {
        return new OrderItemDto.Builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .unitePrice(orderItem.getUnitPrice())
                .productDto(modelMapper.map(orderItem.getProduct(), ProductDto.class))
                .build();
    }

}
