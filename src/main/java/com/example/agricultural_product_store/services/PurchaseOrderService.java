package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.models.entity.PurchaseOrder;
import com.example.agricultural_product_store.repositories.PurchaseOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderService extends BaseService<PurchaseOrder, Long> {
    private PurchaseOrderRepository purchaseOrderRepository;

    PurchaseOrderService(PurchaseOrderRepository repository) {
        super(repository);
        this.purchaseOrderRepository = repository;
    }


}
