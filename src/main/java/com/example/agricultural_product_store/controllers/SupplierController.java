package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.request.RegisterSupplierRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.Supplier;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.UserRepository;
import com.example.agricultural_product_store.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final SupplierService supplierService;
    private final UserRepository userRepository;
    public SupplierController(SupplierService supplierService, UserRepository userRepository) {
        this.supplierService = supplierService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseData<Supplier> createSupplier(Principal principal, @RequestBody RegisterSupplierRequest request) {
        User user = userRepository.findByUsername(principal.getName()).get();
        return ResponseData.onSuccess(supplierService.createSupplier(request, user));
    }
}
