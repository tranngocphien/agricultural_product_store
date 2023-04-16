package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.request.CreateProductRequest;
import com.example.agricultural_product_store.dto.response.ProductResponse;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.Product;
import com.example.agricultural_product_store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseData<List<ProductResponse>> list() {
        List<Product> products = productService.list();
        List<ProductResponse> responses = products.stream().map(product -> {
            return modelMapper.map(product, ProductResponse.class);
        }).collect(Collectors.toList());
        return ResponseData.onSuccess(responses);
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<Product> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseData.onSuccess(productService.createProduct(request));
    }

}
