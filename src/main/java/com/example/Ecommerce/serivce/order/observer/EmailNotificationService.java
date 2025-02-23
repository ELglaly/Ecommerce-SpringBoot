package com.example.Ecommerce.serivce.order.observer;

import com.example.Ecommerce.model.entity.Order;

public class EmailNotificationService implements OrderObserver {
    @Override
    public void update(Order order) {
        System.out.println(order.getOrderId() + " " + order.getOrderStatus());
    }
}
