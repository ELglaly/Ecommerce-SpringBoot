package com.example.Ecommerce.serivce.order;

import com.example.Ecommerce.model.dto.order.OrderDto;
import com.example.Ecommerce.request.order.CreateOrderRequest;

public interface IOrderItem {

    OrderDto getOrder();
    void setOrder(CreateOrderRequest request);
}
