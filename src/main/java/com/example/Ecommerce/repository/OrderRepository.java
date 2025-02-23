package com.example.Ecommerce.repository;

import com.example.Ecommerce.enums.OrderStatus;
import com.example.Ecommerce.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    List<Order> findByOrderStatusAndUserId(OrderStatus status, Long userId);
}
