package com.example.agricultural_product_store.repositories;

import com.example.agricultural_product_store.models.entity.SupplierProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Long> {
}
