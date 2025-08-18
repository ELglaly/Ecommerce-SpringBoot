package com.example.ecommerce.serivce.order;

import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.user.User;

import java.util.Set;

public interface IOrderFactory {
     Order createOrder(Cart cart, User user);

     Order createOrder(Set<OrderItem> orderItemSet, User user);

     OrderItem createOrderItem(Product product, int quantity);
}

