package com.example.Ecommerce.request.cart;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateCartRequest {
    List<UpdateCartItemRequest> items;
}
