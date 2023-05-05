package com.example.agricultural_product_store.dto.request;

import lombok.Data;

@Data
public class UpdateSupplierRequest {
    private Long id;
    private String name;
    private String location;
    private String description;
    private String brandImage;
    private String idNumber;
}
