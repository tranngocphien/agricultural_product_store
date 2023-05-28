package com.example.agricultural_product_store.dto.response;

import lombok.Data;

@Data
public class SupplierResponse {
    private Long id;
    private String name;
    private String location;
    private String description;
    private String brandImage;
    private String idNumber;
    private UserResponse owner;
}
