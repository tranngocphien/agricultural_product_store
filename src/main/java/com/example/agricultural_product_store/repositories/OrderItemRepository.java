package com.example.agricultural_product_store.repositories;

import com.example.agricultural_product_store.models.entity.OrderItem;
import com.example.agricultural_product_store.models.entity.SalesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query(value = "SELECT datefield as date, IFNULL(SUM(quantity), 0) as total FROM calendar LEFT JOIN order_item ON date_format(datefield, '%Y-%m-%d') = date_format(create_time, '%Y-%m-%d') AND product_id = :productId WHERE datefield BETWEEN '2023-06-01' AND curdate() GROUP BY datefield", nativeQuery = true)
    List<SalesModel> getDailySales(Long productId);
    @Query(value = "SELECT date_format(datefield, '%Y-%m'), IFNULL(SUM(quantity), 0) as total FROM calendar LEFT JOIN order_item ON date_format(datefield, '%Y-%m-%d') = date_format(create_time, '%Y-%m-%d') AND product_id = :productId WHERE datefield BETWEEN '2023-06-01' AND curdate() GROUP BY date_format(datefield, '%Y-%m')", nativeQuery = true)
    List<SalesModel> getMonthlySales(Long productId);
}

