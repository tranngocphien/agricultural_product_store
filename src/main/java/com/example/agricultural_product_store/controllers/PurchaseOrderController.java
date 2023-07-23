package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.request.CreatePurchaseOrderRequest;
import com.example.agricultural_product_store.dto.request.UpdatePurchaseOrderRequest;
import com.example.agricultural_product_store.dto.response.PurchaseOrderResponse;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.services.PurchaseOrderServiceImpl;
import com.example.agricultural_product_store.services.template.PurchaseOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;
    private final ModelMapper modelMapper;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService, ModelMapper modelMapper) {
        this.purchaseOrderService = purchaseOrderService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<PurchaseOrderResponse> create(@RequestBody CreatePurchaseOrderRequest request) {
        return ResponseData.onSuccess(modelMapper.map(purchaseOrderService.create(request), PurchaseOrderResponse.class));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<PurchaseOrderResponse> update(@RequestBody UpdatePurchaseOrderRequest request) {
        return ResponseData.onSuccess(modelMapper.map(purchaseOrderService.update(request), PurchaseOrderResponse.class));
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<PurchaseOrderResponse> delete(@PathVariable Long id) {
        return ResponseData.onSuccess(modelMapper.map(purchaseOrderService.delete(id), PurchaseOrderResponse.class));
    }

    @PostMapping("/confirm/{id}")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData<PurchaseOrderResponse> confirm( @PathVariable Long id) {
        return ResponseData.onSuccess(modelMapper.map(purchaseOrderService.confirmPurchaseOrder(id), PurchaseOrderResponse.class));
    }

    @PostMapping("/reject/{id}")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData<PurchaseOrderResponse> reject( @PathVariable Long id) {
        return ResponseData.onSuccess(modelMapper.map(purchaseOrderService.rejectPurchaseOrder(id), PurchaseOrderResponse.class));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData<List<PurchaseOrderResponse>> getAll(Authentication authentication) {
        return ResponseData.onSuccess(purchaseOrderService.getAllByUserId(authentication).stream().map(
                purchaseOrder -> modelMapper.map(purchaseOrder, PurchaseOrderResponse.class)
        ).collect(Collectors.toList()));
    }
}
