package com.example.demo.serivce.category;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.request.category.AddCategoryRequest;
import com.example.demo.request.category.UpdateCategoryRequest;

import java.util.List;

public interface ICategoryService extends ICategoryAnalyticsService,
        ICategoryManagementService, ICategorySearchService{

}
