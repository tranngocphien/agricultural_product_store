package com.example.agricultural_product_store.dto.response;

import com.example.agricultural_product_store.models.entity.Product;
import com.example.agricultural_product_store.models.entity.User;
import lombok.Data;

import javax.persistence.*;

@Data
public class CommentResponse {
    private Long id;
    private String content;
    private int rate;
    private UserResponse owner;
}
