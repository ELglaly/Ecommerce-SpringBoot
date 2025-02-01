package com.example.demo.serivce.order;

import com.example.demo.model.dto.order.OrderDto;

public interface IOrderItem {

    OrderDto getOrder();
    void setOrder(AddOrderRequest request);
}
