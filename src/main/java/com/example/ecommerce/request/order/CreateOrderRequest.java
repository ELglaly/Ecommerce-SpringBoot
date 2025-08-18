package com.example.ecommerce.request.order;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
public class CreateOrderRequest {

    private Set<AddOrderItemRequest>orderItems=new HashSet<>();
    @NotNull(message = "Order status is required")
    private Long userId;

}
