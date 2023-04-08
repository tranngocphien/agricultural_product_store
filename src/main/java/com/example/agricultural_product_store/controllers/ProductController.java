package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.Product;
import com.example.agricultural_product_store.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("list")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseData<List<Product>> list() {
        return ResponseData.onSuccess(productService.list());
    }
}
