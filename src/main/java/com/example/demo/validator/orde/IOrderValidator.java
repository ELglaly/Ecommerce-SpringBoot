package com.example.demo.validator.orde;

import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.OrderItem;
import com.example.demo.model.entity.User;

import java.math.BigDecimal;
import java.util.Set;

public interface IOrderValidator {
    boolean orderItemsIsValid(Set<OrderItem> orderItems);
    boolean orderItemIsValid(OrderItem orderItem);
    boolean orderIsValid(Order order);
    boolean orderUserIsValid(User user);
    boolean priceIsValid(BigDecimal price);
}
