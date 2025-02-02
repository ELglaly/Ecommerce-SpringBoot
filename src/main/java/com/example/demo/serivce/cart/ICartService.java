package com.example.demo.serivce.cart;

import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.entity.Cart;
import com.example.demo.serivce.category.ICategoryManagementService;
import com.example.demo.serivce.category.ICategorySearchService;

import java.math.BigDecimal;

public interface ICartService  extends ICartAnalyticsService,
        ICartSearchService,ICartManagementService {

}
