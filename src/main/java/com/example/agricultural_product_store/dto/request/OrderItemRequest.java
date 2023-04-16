package com.example.agricultural_product_store.dto.request;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long productId;
    private int amount;
}
