package com.example.agricultural_product_store.services.template;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CreateCategoryRequest;
import com.example.agricultural_product_store.dto.request.UpdateCategoryRequest;
import com.example.agricultural_product_store.models.entity.Category;

import java.sql.Timestamp;

public interface CategoryService extends BaseService<Category, Long> {
    public Category save(CreateCategoryRequest request);

    public Category update(UpdateCategoryRequest request);

    public Category delete(Long id);
}
