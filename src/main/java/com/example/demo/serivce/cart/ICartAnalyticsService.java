package com.example.demo.serivce.cart;

import java.math.BigDecimal;

public interface ICartAnalyticsService {
    BigDecimal getTotalPrice(Long id);
}
