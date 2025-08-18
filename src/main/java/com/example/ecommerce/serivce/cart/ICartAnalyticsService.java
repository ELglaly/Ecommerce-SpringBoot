package com.example.ecommerce.serivce.cart;

import java.math.BigDecimal;

public interface ICartAnalyticsService {
    BigDecimal getTotalPrice(Long id);
}
