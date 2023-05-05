package com.example.agricultural_product_store.repositories;

import com.example.agricultural_product_store.models.entity.Category;
import com.example.agricultural_product_store.models.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long id);
    List<Product> findAllByCategoryId(Long categoryId);
    List<Product> findAllByCategory_Id(Long categoryId);
    List<Product> findAllByNameContains(String keyword);

}
