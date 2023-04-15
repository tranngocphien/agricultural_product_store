package com.example.agricultural_product_store.repositories;

import com.example.agricultural_product_store.models.entity.PurchaseOrder;
import com.example.agricultural_product_store.services.PurchaseOrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
