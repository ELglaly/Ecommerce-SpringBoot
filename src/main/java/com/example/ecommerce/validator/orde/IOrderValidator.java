package com.example.ecommerce.validator.orde;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.user.User;

import java.math.BigDecimal;
import java.util.Set;

public interface IOrderValidator {
    boolean orderItemsIsValid(Set<OrderItem> orderItems);
    boolean orderItemIsValid(OrderItem orderItem);
    boolean orderIsValid(Order order);
    boolean orderUserIsValid(User user);
    boolean priceIsValid(BigDecimal price);
}
