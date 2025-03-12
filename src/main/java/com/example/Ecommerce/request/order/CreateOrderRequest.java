package com.example.Ecommerce.request.order;

import com.example.Ecommerce.enums.OrderStatus;
import com.example.Ecommerce.model.dto.UserDto;
import com.example.Ecommerce.model.entity.OrderItem;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class CreateOrderRequest {

    private Set<AddOrderItemRequest>orderItems=new HashSet<>();
    @NotNull(message = "Order status is required")
    private Long userId;

}
