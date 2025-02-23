package com.example.Ecommerce.model.dto.cart;

import com.example.Ecommerce.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
@Data
@AllArgsConstructor
@Builder
public class CartDto {

    private Long id;
    private BigDecimal totalPrice;
    private int totalAmount=0;
    private Set<CartItemDto> items;
    private UserDto user;

}
