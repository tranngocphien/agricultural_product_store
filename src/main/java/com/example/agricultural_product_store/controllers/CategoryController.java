package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.request.CreateCategoryRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.Category;
import com.example.agricultural_product_store.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/list")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseData<List<Category>> list() {
        return ResponseData.onSuccess(categoryService.list());
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseData<Category> create(@RequestBody  CreateCategoryRequest request) {
        return ResponseData.onSuccess(categoryService.save(request));
    }
}
