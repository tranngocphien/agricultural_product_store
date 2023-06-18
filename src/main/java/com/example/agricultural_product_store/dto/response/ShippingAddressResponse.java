package com.example.agricultural_product_store.dto.response;
import lombok.Data;

@Data
public class ShippingAddressResponse {
    private Long id;
    private String address;
    private String name;
    private String phoneNumber;
}
