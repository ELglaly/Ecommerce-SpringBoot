package com.example.demo.model.dto.cart;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.CartItem;
import com.example.demo.model.entity.User;
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
