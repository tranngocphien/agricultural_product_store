package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.request.CreateCategoryRequest;
import com.example.agricultural_product_store.dto.request.UpdateCategoryRequest;
import com.example.agricultural_product_store.dto.response.CategoryResponse;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.Category;
import com.example.agricultural_product_store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    @CrossOrigin
    public ResponseData<List<Category>> list() {
        return ResponseData.onSuccess(categoryService.list().stream().map(category -> modelMapper.map(category, Category.class)).collect(Collectors.toList()));
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<CategoryResponse> create(@RequestBody  CreateCategoryRequest request) {
        return ResponseData.onSuccess(modelMapper.map(categoryService.save(request), CategoryResponse.class));
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<CategoryResponse> update(@RequestBody UpdateCategoryRequest request) {
        Category category = categoryService.update(request);
        return ResponseData.onSuccess(modelMapper.map(category, CategoryResponse.class));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<Category> delete(@PathVariable("id") Long id) {
        return ResponseData.onSuccess(categoryService.delete(id));
    }
}
