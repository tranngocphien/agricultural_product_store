package com.example.agricultural_product_store.dto.request;

import lombok.Data;

import javax.persistence.Column;

@Data
public class RegisterSupplierRequest {
    private String name;
    private String location;
    private String description;
    private String brandImage;
    private String idNumber;
}
