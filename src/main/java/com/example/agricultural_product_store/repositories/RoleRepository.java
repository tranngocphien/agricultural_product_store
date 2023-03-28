package com.example.agricultural_product_store.repositories;


import com.example.agricultural_product_store.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
