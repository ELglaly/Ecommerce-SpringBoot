package com.example.ecommerce.serivce.order;

import com.example.ecommerce.dto.order.OrderDTO;
import com.example.ecommerce.request.order.CreateOrderRequest;

public interface IOrderItem {

    OrderDTO getOrder();
    void setOrder(CreateOrderRequest request);
}
