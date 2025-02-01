package com.example.demo.controller;


import com.example.demo.constants.ApiConstants;
import com.example.demo.serivce.cart.CartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.ORDER_ENDPOINT)
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
}
