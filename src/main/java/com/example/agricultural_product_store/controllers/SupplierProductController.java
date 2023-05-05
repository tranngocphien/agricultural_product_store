package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.request.CreateSupplierProductRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.SupplierProduct;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.UserRepository;
import com.example.agricultural_product_store.services.SupplierProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier-products")
public class SupplierProductController {
    private final SupplierProductService supplierProductService;
    private final UserRepository userRepository;
    public SupplierProductController(SupplierProductService supplierProductService, UserRepository userRepository) {
        this.supplierProductService = supplierProductService;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData create(Authentication authentication, @RequestBody CreateSupplierProductRequest request) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        return ResponseData.onSuccess(supplierProductService.create(request, user ));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData update(Authentication authentication, @RequestBody CreateSupplierProductRequest request) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        return ResponseData.onSuccess(supplierProductService.create(request, user ));
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData delete(Authentication authentication, @PathVariable("id") Long id) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        return ResponseData.onFail();
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<List<SupplierProduct>> getAll() {
        return ResponseData.onSuccess(supplierProductService.list());
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData<List<SupplierProduct>> getListProductOfSupplier(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        return ResponseData.onSuccess(supplierProductService.getSupplierProductOfUser(user));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData<List<SupplierProduct>> search(@RequestParam String keyword) {
        return ResponseData.onSuccess(supplierProductService.search(keyword));
    }
}
