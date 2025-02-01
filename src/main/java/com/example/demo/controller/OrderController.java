package com.example.demo.controller;

import com.example.demo.constants.ApiConstants;
import com.example.demo.serivce.order.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.CART_ENDPOINT)
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

}
