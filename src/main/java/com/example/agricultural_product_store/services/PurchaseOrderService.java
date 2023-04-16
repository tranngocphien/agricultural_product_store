package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CreatePurchaseOrderRequest;
import com.example.agricultural_product_store.models.entity.PurchaseOrder;
import com.example.agricultural_product_store.models.entity.SupplierProduct;
import com.example.agricultural_product_store.repositories.PurchaseOrderRepository;
import com.example.agricultural_product_store.repositories.SupplierProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class PurchaseOrderService extends BaseService<PurchaseOrder, Long> {
    private PurchaseOrderRepository purchaseOrderRepository;
    private SupplierProductRepository supplierProductRepository;

    PurchaseOrderService(PurchaseOrderRepository repository, SupplierProductRepository supplierProductRepository) {
        super(repository);
        this.purchaseOrderRepository = repository;
        this.supplierProductRepository = supplierProductRepository;
    }

    public PurchaseOrder create(CreatePurchaseOrderRequest request) {
        SupplierProduct supplierProduct = supplierProductRepository.findById(request.getSupplierProductId()).orElseThrow(
                () -> new ResourceNotFoundException("Supplier product not found")
        );
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSupplierProduct(supplierProduct);
        purchaseOrder.setNote(request.getNote());
        purchaseOrder.setAmount(request.getAmount());
        purchaseOrder.setPrice(request.getPrice());
        purchaseOrder.setStatus("IDLE");
        purchaseOrder.setHarvestAt(request.getHarvestAt());
        purchaseOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
        purchaseOrder.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder confirmPurchaseOrder(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Purchase order not found")
        );
        purchaseOrder.setStatus("CONFIRMED");
        purchaseOrder.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return purchaseOrderRepository.save(purchaseOrder);
    }


}
