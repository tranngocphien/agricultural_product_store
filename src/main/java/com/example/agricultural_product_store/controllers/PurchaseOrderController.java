package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.request.CreatePurchaseOrderRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.PurchaseOrder;
import com.example.agricultural_product_store.services.PurchaseOrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<PurchaseOrder> create(@RequestBody CreatePurchaseOrderRequest request) {
        return ResponseData.onSuccess(purchaseOrderService.create(request));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<PurchaseOrder> update(@RequestBody CreatePurchaseOrderRequest request) {
        return ResponseData.onSuccess(purchaseOrderService.create(request));
    }


    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<PurchaseOrder> delete(@PathVariable Long id) {
        return ResponseData.onFail();
    }

    @PostMapping("/confirm/{id}")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseData<PurchaseOrder> confirm( @PathVariable Long id) {
        return ResponseData.onSuccess(purchaseOrderService.confirmPurchaseOrder(id));
    }
}
