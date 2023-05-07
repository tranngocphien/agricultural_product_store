package com.example.agricultural_product_store.dto.response;

import com.example.agricultural_product_store.models.entity.CartItem;
import com.example.agricultural_product_store.models.entity.User;
import lombok.Data;
import java.util.List;

@Data
public class CartResponse {
    private Long id;

    private List<CartItem> items;
}
