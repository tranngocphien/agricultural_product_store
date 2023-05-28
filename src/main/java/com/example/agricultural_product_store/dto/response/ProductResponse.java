package com.example.agricultural_product_store.dto.response;

import com.example.agricultural_product_store.models.entity.Comment;
import lombok.Data;

import java.util.Set;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private int price;
    private String sku;
    private int stock;
    private CategoryResponse category;
    private String origin;
    private String description;
    private Set<String> certificateImages;
    private Set<String> images;
    private Set<CommentResponse> comments;
    private String preservation;
}
