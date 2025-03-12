package com.example.Ecommerce.request.order;

import com.example.Ecommerce.model.dto.ProductDto;
import com.example.Ecommerce.model.dto.order.OrderDto;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddOrderItemRequest {

    private int quantity;
    private Long productId;
}
