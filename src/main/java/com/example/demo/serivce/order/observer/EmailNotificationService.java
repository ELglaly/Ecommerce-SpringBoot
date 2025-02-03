package com.example.demo.serivce.order.observer;

import com.example.demo.model.entity.Order;

import java.util.Observer;

public class EmailNotificationService implements OrderObserver {
    @Override
    public void update(Order order) {
        System.out.println(order.getOrderId() + " " + order.getOrderStatus());
    }
}
