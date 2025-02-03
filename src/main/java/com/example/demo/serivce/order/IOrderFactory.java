package com.example.demo.serivce.order;

import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.User;

public interface IOrderFactory {
     Order createOrder(CartDto cart, User user);
}
