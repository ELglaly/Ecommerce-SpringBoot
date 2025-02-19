package com.example.demo.serivce.order;

import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.entity.Cart;
import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.User;

public interface IOrderFactory {
     Order createOrder(Cart cart, User user);
     Order createOrder(Product product, User user,int quantity) ;

}
