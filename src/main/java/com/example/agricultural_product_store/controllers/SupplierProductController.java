package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CreateSupplierProductRequest;
import com.example.agricultural_product_store.dto.request.UpdateSupplierProductRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.dto.response.SupplierProductResponse;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.UserRepository;
import com.example.agricultural_product_store.services.SupplierProductService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/supplier-products")
public class SupplierProductController {
    private final SupplierProductService supplierProductService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public SupplierProductController(SupplierProductService supplierProductService, UserRepository userRepository, ModelMapper modelMapper) {
        this.supplierProductService = supplierProductService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData<SupplierProductResponse> create(Authentication authentication, @RequestBody CreateSupplierProductRequest request) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return ResponseData.onSuccess(modelMapper.map(supplierProductService.create(request, user ), SupplierProductResponse.class));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData<SupplierProductResponse> update(Authentication authentication, @RequestBody UpdateSupplierProductRequest request) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return ResponseData.onSuccess(modelMapper.map(supplierProductService.update(request, user ), SupplierProductResponse.class));
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData delete(Authentication authentication, @PathVariable("id") Long id) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return supplierProductService.delete(id, user);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<List<SupplierProductResponse>> getAll() {
        return ResponseData.onSuccess(supplierProductService.list().stream().map(
                supplierProduct -> modelMapper.map(supplierProduct, SupplierProductResponse.class)
        ).collect(Collectors.toList()));
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData<List<SupplierProductResponse>> getListProductOfSupplier(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        return ResponseData.onSuccess(supplierProductService.getSupplierProductOfUser(user).stream().map(
                supplierProduct -> modelMapper.map(supplierProduct, SupplierProductResponse.class)
        ).collect(Collectors.toList()));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData<List<SupplierProductResponse>> search(@RequestParam String keyword) {
        return ResponseData.onSuccess(supplierProductService.search(keyword).stream().map(
                supplierProduct -> modelMapper.map(supplierProduct, SupplierProductResponse.class)
        ).collect(Collectors.toList()));
    }
}
