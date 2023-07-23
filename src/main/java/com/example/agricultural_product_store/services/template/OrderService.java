package com.example.agricultural_product_store.services.template;

import com.example.agricultural_product_store.dto.request.CommentRequest;
import com.example.agricultural_product_store.dto.request.CreateOrderRequest;
import com.example.agricultural_product_store.dto.response.PredictSaleResponse;
import com.example.agricultural_product_store.models.entity.*;

import java.util.*;

public interface OrderService extends BaseService<Order, Long> {
    public Order createOrder(CreateOrderRequest request, User user);

    public Order cancelOrder(Long id, User user);

    public Order updateStatus(Long id, User user, OrderStatus status);
    public List<Order> getListOrderByUser(Long id);

    public Comment commentProduct(User user, CommentRequest request);

    public List<Map<String, Object>> getSalesByMonth(Long productId);


    public List<Map<String, Object>> getDailySales(Long productId);

    public List<PredictSaleResponse> predictMonthlySales(Long productId) ;
}
