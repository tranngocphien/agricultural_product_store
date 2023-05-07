package com.example.agricultural_product_store.repositories;

import com.example.agricultural_product_store.models.entity.PurchaseOrder;
import com.example.agricultural_product_store.services.PurchaseOrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    @Query("SELECT p FROM purchase_order p WHERE p.supplierProduct.owner.id = ?1")
    List<PurchaseOrder> findPurchaseOrderByUserId(Long id);
}
