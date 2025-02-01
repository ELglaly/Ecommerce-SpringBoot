package com.example.demo.repository;

import com.example.demo.enums.OrderStatus;
import com.example.demo.model.dto.order.OrderDto;
import com.example.demo.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    List<Order> findByOrderStatusAndUserId(OrderStatus status, Long userId);
}
