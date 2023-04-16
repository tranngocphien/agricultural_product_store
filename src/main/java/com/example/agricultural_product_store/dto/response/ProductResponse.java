package com.example.agricultural_product_store.dto.response;

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
    private String location;
    private String description;
    private String certificateType;
    private Set<String> certificateImages;
    private Set<String> images;
    private String preservation;
}
