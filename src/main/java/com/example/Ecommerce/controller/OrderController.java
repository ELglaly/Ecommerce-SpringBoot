package com.example.Ecommerce.controller;

import com.example.Ecommerce.constants.ApiConstants;
import com.example.Ecommerce.enums.OrderStatus;
import com.example.Ecommerce.model.dto.order.OrderDto;
import com.example.Ecommerce.request.order.CreateOrderRequest;
import com.example.Ecommerce.response.ApiResponse;
import com.example.Ecommerce.serivce.order.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.ORDER_ENDPOINT)
public class OrderController {
    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("by-card/{cartId}")
    public ResponseEntity<ApiResponse> placeOrderByCart(@PathVariable Long cartId) {
        OrderDto order = orderService.placeOrderByCart(cartId);
        return ResponseEntity.ok(new ApiResponse(order, "Order created successfully"));
    }
    @PostMapping("/by-product")
    public ResponseEntity<ApiResponse> placeOrderByCart(@RequestBody CreateOrderRequest request) {
        OrderDto order = orderService.placeOrder(request);
        return ResponseEntity.ok(new ApiResponse(order, "Order created successfully"));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(new ApiResponse(order, "Order retrieved successfully"));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getAllOrders(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getAllOrders(userId);
        return ResponseEntity.ok(new ApiResponse(orders, "Orders retrieved successfully"));
    }

    @GetMapping("/status/{userId}")
    public ResponseEntity<ApiResponse> getAllOrders(@PathVariable Long userId, @RequestParam OrderStatus status) {
        List<OrderDto> orders = orderService.getOrdersByUserIdAndStatus(userId,status);
        return ResponseEntity.ok(new ApiResponse(orders, "Orders retrieved successfully"));
    }

}