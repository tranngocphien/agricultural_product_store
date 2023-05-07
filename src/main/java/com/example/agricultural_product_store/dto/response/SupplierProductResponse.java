package com.example.agricultural_product_store.dto.response;

import com.example.agricultural_product_store.models.entity.Category;
import com.example.agricultural_product_store.models.entity.PurchaseOrder;
import com.example.agricultural_product_store.models.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
public class SupplierProductResponse {
    private Long id;
    private String productName;
    private int expectedPrice;
    private String location;
    private String description;
    private String certificateType;
    private Set<String> certificateImages;
    private Set<String> images;
    private String preservation;

    private Category category;


}
