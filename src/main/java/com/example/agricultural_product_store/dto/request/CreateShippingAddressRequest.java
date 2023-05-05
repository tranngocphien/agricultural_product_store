package com.example.agricultural_product_store.dto.request;

import com.example.agricultural_product_store.models.entity.User;
import lombok.Data;

import javax.persistence.*;

@Data
public class CreateShippingAddressRequest {
    private String provinceId;
    private String districtId;
    private String wardId;
    private String street;
    private String name;
    private String phoneNumber;
}
