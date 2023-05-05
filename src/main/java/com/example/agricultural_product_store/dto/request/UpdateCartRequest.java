package com.example.agricultural_product_store.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateCartRequest {
    List<CartItemRequest> items;
}
