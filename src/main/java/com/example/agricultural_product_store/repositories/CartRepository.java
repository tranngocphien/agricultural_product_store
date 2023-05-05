package com.example.agricultural_product_store.repositories;

import com.example.agricultural_product_store.models.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByOwner_Id(Long id);
}
