package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.request.RegisterSupplierRequest;
import com.example.agricultural_product_store.dto.request.UpdateSupplierRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.dto.response.SupplierResponse;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.UserRepository;
import com.example.agricultural_product_store.services.SupplierServiceImpl;
import com.example.agricultural_product_store.services.template.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final SupplierService supplierService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public SupplierController(SupplierService supplierService, UserRepository userRepository, ModelMapper modelMapper) {
        this.supplierService = supplierService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseData<SupplierResponse> createSupplier(Principal principal, @RequestBody RegisterSupplierRequest request) {
        User user = userRepository.findByUsername(principal.getName()).get();
        return ResponseData.onSuccess(modelMapper.map(supplierService.createSupplier(request, user), SupplierResponse.class));
    }

    @GetMapping("/info")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData<SupplierResponse> getSupplierInfo(Authentication authentication) {
        return ResponseData.onSuccess(modelMapper.map(supplierService.getSupplierInfo(authentication), SupplierResponse.class));
    }

    @GetMapping("/{id}")
    public ResponseData<SupplierResponse> getSupplierInfoById(@PathVariable Long id) {
        return ResponseData.onSuccess(modelMapper.map(supplierService.getSupplierInfoById(id), SupplierResponse.class));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_SUPPLIER') or hasRole('ROLE_ADMIN')")
    public ResponseData<SupplierResponse> update(Authentication authentication, @RequestBody UpdateSupplierRequest request) {
        return ResponseData.onSuccess(modelMapper.map(supplierService.updateSupplier(request, authentication), SupplierResponse.class));
    }

    @GetMapping
    public ResponseData<List<SupplierResponse>> getAll() {
        return ResponseData.onSuccess(supplierService.list().stream().map(
                supplier -> modelMapper.map(supplier, SupplierResponse.class)
        ).collect(Collectors.toList()));
    }
}
