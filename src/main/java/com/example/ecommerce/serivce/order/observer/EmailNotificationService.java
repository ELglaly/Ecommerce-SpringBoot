package com.example.ecommerce.serivce.order.observer;

import com.example.ecommerce.entity.Order;

public class EmailNotificationService implements OrderObserver {
    @Override
    public void update(Order order) {
        System.out.println(order.getOrderId() + " " + order.getOrderStatus());
    }
}
