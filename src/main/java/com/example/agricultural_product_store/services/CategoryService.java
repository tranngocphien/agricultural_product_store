package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.dto.request.CreateCategoryRequest;
import com.example.agricultural_product_store.models.entity.Category;
import com.example.agricultural_product_store.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CategoryService extends BaseService<Category, Long>{
    CategoryService(CategoryRepository repository) {
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
    
}
