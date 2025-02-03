package com.example.demo.mapper.order;

import com.example.demo.model.dto.order.OrderItemDto;
import com.example.demo.model.entity.OrderItem;

public interface IOrderItemMapper {

    OrderItemDto toDto(OrderItem orderItem);
}
