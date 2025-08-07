package com.example.ecommerce.controller;

import com.example.ecommerce.constants.ApiConstants;
import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.dto.order.OrderDTO;
import com.example.ecommerce.request.order.CreateOrderRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.serivce.order.IOrderService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
        OrderDTO order = orderService.placeOrderByCart(cartId);
        return ResponseEntity.ok(new ApiResponse(order, "Order created successfully"));
    }
    @PostMapping("/by-product")
    public ResponseEntity<ApiResponse> placeOrderByProduct(@RequestBody @Valid CreateOrderRequest request) {
        OrderDTO order = orderService.placeProductOrder(request);
        return ResponseEntity.ok(new ApiResponse(order, "Order created successfully"));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        OrderDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(new ApiResponse(order, "Order retrieved successfully"));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllOrders(HttpServletResponse response) {
        String token = response.getHeader("Authorization").replace("Bearer ", "");
        List<OrderDTO> orders = orderService.getAllOrders(token);
        return ResponseEntity.ok(new ApiResponse(orders, "Orders retrieved successfully"));
    }

    @GetMapping("/status/{userId}")
    public ResponseEntity<ApiResponse> getAllOrders(@RequestParam OrderStatus status, HttpServletResponse response) {
        String token = response.getHeader("Authorization").replace("Bearer ", "");
        List<OrderDTO> orders = orderService.getOrdersByUserIdAndStatus(token,status);
        return ResponseEntity.ok(new ApiResponse(orders, "Orders retrieved successfully"));
    }

}