package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.request.CreateSupplierProductRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.services.SupplierProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/supplier-products")
public class SupplierProductController {
    private final SupplierProductService supplierProductService;

    public SupplierProductController(SupplierProductService supplierProductService) {
        this.supplierProductService = supplierProductService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData create(@RequestBody CreateSupplierProductRequest request) {
        return ResponseData.onSuccess("a");
    }
}
