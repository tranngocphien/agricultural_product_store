package com.example.agricultural_product_store.dto.request;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long productId;
    private int amount;
}
