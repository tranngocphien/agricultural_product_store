package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.request.CreateProductRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.Product;
import com.example.agricultural_product_store.services.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseData<List<Product>> list() {
        return ResponseData.onSuccess(productService.list());
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<Product> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseData.onSuccess(productService.createProduct(request));
    }

}
