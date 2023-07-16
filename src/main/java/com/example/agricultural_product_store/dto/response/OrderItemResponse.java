package com.example.agricultural_product_store.dto.response;

import com.example.agricultural_product_store.models.entity.Order;
import com.example.agricultural_product_store.models.entity.Product;
import lombok.Data;

import javax.persistence.*;

@Data
public class OrderItemResponse {
    private Long id;
    private ProductResponse product;
    private int quantity;
    private boolean hasReview;
}
