package com.example.demo.controller;


import com.example.demo.constants.ApiConstants;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.response.ApiResponse;
import com.example.demo.serivce.cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.ORDER_ENDPOINT)
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        CartDto cartDto =cartService.getCartById(cartId);
        return ResponseEntity.ok(new ApiResponse (cartDto,"Cart Retravied Successfully"));
    }
    @DeleteMapping
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok(new ApiResponse (null,"Cart Retravied Successfully"));
    }

}
