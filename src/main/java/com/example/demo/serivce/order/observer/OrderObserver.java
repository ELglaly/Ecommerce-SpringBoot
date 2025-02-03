package com.example.demo.serivce.order.observer;

import com.example.demo.model.entity.Order;

public interface OrderObserver {
    void update(Order order);
}
