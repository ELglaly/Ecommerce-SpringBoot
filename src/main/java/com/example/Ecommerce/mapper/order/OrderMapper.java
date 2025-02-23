package com.example.Ecommerce.mapper.order;

import com.example.Ecommerce.model.dto.order.OrderDto;
import com.example.Ecommerce.model.dto.order.OrderItemDto;
import com.example.Ecommerce.model.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderMapper implements IOrderMapper {

    private final IOrderItemMapper orderItemMapper;
    private final ModelMapper modelMapper;

    public OrderMapper(IOrderItemMapper orderItemMapper,ModelMapper modelMapper) {
        this.orderItemMapper = orderItemMapper;
        this.modelMapper = modelMapper;
    }

    public OrderDto toDto(Order order) {
        // Convert OrderItems to OrderItemDtos
        Set<OrderItemDto> orderItemDto = order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
        OrderDto orderDto=modelMapper.map(order,OrderDto.class);
        orderDto.setOrderItems(orderItemDto);
        return orderDto;

    }
}