package com.example.ecommerce.serivce.order.observer;

import com.example.ecommerce.entity.Order;

public interface OrderObserver {
    void update(Order order);
}
