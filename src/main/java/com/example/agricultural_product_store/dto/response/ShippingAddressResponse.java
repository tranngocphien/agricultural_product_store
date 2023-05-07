package com.example.agricultural_product_store.dto.response;
import lombok.Data;

@Data
public class ShippingAddressResponse {
    private Long id;
    private String provinceId;
    private String districtId;
    private String wardId;
    private String street;
    private String name;
    private String phoneNumber;
}
