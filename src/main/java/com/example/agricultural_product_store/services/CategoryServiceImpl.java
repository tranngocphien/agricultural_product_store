package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CreateCategoryRequest;
import com.example.agricultural_product_store.dto.request.UpdateCategoryRequest;
import com.example.agricultural_product_store.models.entity.Category;
import com.example.agricultural_product_store.repositories.CategoryRepository;
import com.example.agricultural_product_store.services.template.CategoryService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CategoryServiceImpl extends BaseService<Category, Long> implements CategoryService {
    CategoryServiceImpl(CategoryRepository repository) {
        super(repository);
    }
    public Category save(CreateCategoryRequest request) {
        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        category.setCategoryThumb(request.getCategoryThumb());
        category.setCreateTime(new Timestamp(System.currentTimeMillis()));
        category.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return  repository.save(category);
    }

    public Category update(UpdateCategoryRequest request) {
        Category category = repository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setCategoryName(request.getCategoryName());
        category.setCategoryThumb(request.getCategoryThumb());
        category.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return  repository.save(category);
    }

    public Category delete(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        repository.delete(category);
        return category;
    }
    
}
