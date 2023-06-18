package com.example.agricultural_product_store.dto.request;

import lombok.Data;

@Data
public class UpdateShippingAddressRequest {
    private Long id;
    private String address;
    private String name;
    private String phoneNumber;
}
