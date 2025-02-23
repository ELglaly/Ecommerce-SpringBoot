package com.example.Ecommerce.serivce.order.observer;

import com.example.Ecommerce.model.entity.Order;

public interface OrderObserver {
    void update(Order order);
}
