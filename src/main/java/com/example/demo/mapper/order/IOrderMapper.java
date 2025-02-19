package com.example.demo.mapper.order;

import com.example.demo.mapper.IAddRequestToEntityMapper;
import com.example.demo.mapper.IDtoToEntityMapper;
import com.example.demo.mapper.IEntityToDtoMapper;
import com.example.demo.model.dto.order.OrderDto;
import com.example.demo.model.entity.Order;
import com.example.demo.request.order.AddOrderRequest;

public interface IOrderMapper extends IEntityToDtoMapper<Order, OrderDto>
{
}
