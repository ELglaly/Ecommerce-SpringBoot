package com.example.demo.mapper;

import com.example.demo.mapper.order.IOrderItemMapper;
import com.example.demo.mapper.order.IOrderMapper;
import com.example.demo.model.dto.order.OrderDto;
import com.example.demo.model.dto.order.OrderItemDto;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.OrderItem;
import com.example.demo.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final IProductMapper productMapper;
    private final IUserMapper userMapper;
    private final IOrderItemMapper orderItemMapper;

    public OrderMapper(IProductMapper productMapper, IUserMapper userMapper, IOrderItemMapper orderItemMapper) {
        this.productMapper = productMapper;
        this.userMapper = userMapper;
        this.orderItemMapper = orderItemMapper;
    }

    public OrderDto toDto(Order order) {

        // Convert OrderItems to OrderItemDtos
        Set<OrderItemDto> orderItemDto = order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
        // Convert User to UserDto
        UserDto userDto = userMapper.toDto(order.getUser());

        return new OrderDto.Builder()
                .orderId(order.getOrderId())
                .orderItems(orderItemDto)
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .orderTotalPrice(order.getOrderTotalPrice())
                .user(userDto)
                .build();

    }
}