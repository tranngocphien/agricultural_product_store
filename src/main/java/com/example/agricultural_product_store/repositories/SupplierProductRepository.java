package com.example.agricultural_product_store.repositories;

import com.example.agricultural_product_store.models.entity.SupplierProduct;
import com.example.agricultural_product_store.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Long> {
    List<SupplierProduct> findAllByOwner(User user);
}
