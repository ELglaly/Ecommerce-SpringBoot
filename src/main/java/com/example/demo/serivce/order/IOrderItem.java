package com.example.demo.serivce.order;

import com.example.demo.model.dto.order.OrderDto;
import com.example.demo.request.order.AddOrderRequest;

public interface IOrderItem {

    OrderDto getOrder();
    void setOrder(AddOrderRequest request);
}
