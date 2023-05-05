package com.example.agricultural_product_store.dto.response;

import com.example.agricultural_product_store.models.entity.User;

import javax.persistence.*;

public class SupplierResponse {
    private Long id;
    private String name;
    private String location;
    private String description;
    private String brandImage;

    private String idNumber;
    private UserResponse owner;
}
