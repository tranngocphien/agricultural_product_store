package com.example.agricultural_product_store.repositories;

import com.example.agricultural_product_store.models.entity.Category;
import com.example.agricultural_product_store.models.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long id);
    List<Product> findAllByCategoryId(Long categoryId);
    List<Product> findAllByCategory_Id(Long categoryId);
    List<Product> findAllByNameContains(String keyword);

    @Query(value = "SELECT * FROM product WHERE product_id IN (SELECT product_id from order_item GROUP BY product_id ORDER BY sum(quantity) DESC) LIMIT 10", nativeQuery = true)
    List<Product> bestSeller();

}
