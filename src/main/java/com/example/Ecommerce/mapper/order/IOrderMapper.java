package com.example.Ecommerce.mapper.order;

import com.example.Ecommerce.mapper.IEntityToDtoMapper;
import com.example.Ecommerce.model.dto.order.OrderDto;
import com.example.Ecommerce.model.entity.Order;

public interface IOrderMapper extends IEntityToDtoMapper<Order, OrderDto>
{
}
