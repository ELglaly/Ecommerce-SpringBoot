package com.example.Ecommerce.serivce.order;

import com.example.Ecommerce.model.entity.Cart;
import com.example.Ecommerce.model.entity.Order;
import com.example.Ecommerce.model.entity.OrderItem;
import com.example.Ecommerce.model.entity.Product;
import com.example.Ecommerce.model.entity.User;

import java.util.Set;

public interface IOrderFactory {
     Order createOrder(Cart cart, User user);

     Order createOrder(Set<OrderItem> orderItemSet, User user);

     OrderItem createOrderItem(Product product, int quantity);
}

