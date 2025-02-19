package com.example.demo.validator.orde;

import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.OrderItem;
import com.example.demo.model.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class OrderValidator implements IOrderValidator {
    @Override
    public boolean orderItemsIsValid(Set<OrderItem> orderItems) {
        return Optional.ofNullable(orderItems)
                .map(items -> (items.stream().allMatch(this::orderItemIsValid)))
                .orElse(false);
    }

    @Override
    public boolean orderItemIsValid(OrderItem orderItem) {
        return Optional.ofNullable(orderItem)
                .map(item -> (item.getQuantity() > 0 &&
                        item.getProduct() != null &&
                        item.getOrder() != null &&
                        item.getProduct().getQuantity() >= item.getQuantity()&&
                        priceIsValid(item.getTotalPrice())&&
                        priceIsValid(item.getUnitPrice())))
                .orElse(false);
    }

    @Override
    public boolean orderIsValid(Order order) {
        return Optional.ofNullable(order)
                .map(o -> (orderUserIsValid(o.getUser()) &&
                        dateIsValid(o.getCreatedAt()) &&
                        priceIsValid(o.getOrderTotalPrice())))
                .orElse(false);
    }

    private boolean dateIsValid(LocalDateTime createdAt) {
        return createdAt != null;
    }

    @Override
    public boolean orderUserIsValid(User user) {
        return user==null;
    }

    @Override
    public boolean priceIsValid(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) > 0;
    }
}
