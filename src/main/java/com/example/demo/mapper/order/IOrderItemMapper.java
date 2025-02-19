package com.example.demo.mapper.order;

import com.example.demo.mapper.IAddRequestToEntityMapper;
import com.example.demo.mapper.IDtoToEntityMapper;
import com.example.demo.mapper.IEntityToDtoMapper;
import com.example.demo.model.dto.order.OrderItemDto;
import com.example.demo.model.entity.OrderItem;
import org.apache.catalina.mapper.Mapper;

public interface IOrderItemMapper extends IEntityToDtoMapper<OrderItem, OrderItemDto>
{

}
