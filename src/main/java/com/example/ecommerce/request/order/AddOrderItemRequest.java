package com.example.ecommerce.request.order;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddOrderItemRequest {

    private int quantity;
    private Long productId;
}
