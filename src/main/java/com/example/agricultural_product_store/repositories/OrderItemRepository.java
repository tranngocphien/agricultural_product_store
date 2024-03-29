package com.example.agricultural_product_store.repositories;

import com.example.agricultural_product_store.models.entity.OrderItem;
import com.example.agricultural_product_store.models.entity.SalesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.List;
import java.util.Map;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query(value = "SELECT datefield as dayStr, IFNULL(SUM(quantity), 0) as total FROM calendar LEFT JOIN order_item ON date_format(datefield, '%Y-%m-%d') = date_format(create_time, '%Y-%m-%d') AND product_id = :productId WHERE datefield BETWEEN '2021-01-01' AND curdate() GROUP BY datefield", nativeQuery = true)
    List<Map<String, Object>> getDailySales(Long productId);
    @Query(value = "SELECT date_format(datefield, '%Y-%m') as dayStr, IFNULL(SUM(quantity), 0) as total FROM calendar LEFT JOIN order_item ON date_format(datefield, '%Y-%m-%d') = date_format(create_time, '%Y-%m-%d') AND product_id = :productId WHERE datefield BETWEEN '2021-01-01' AND curdate() GROUP BY date_format(datefield, '%Y-%m')", nativeQuery = true)
    List<Map<String, Object>> getMonthlySales(Long productId);
}

