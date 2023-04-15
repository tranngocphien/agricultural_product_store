package com.example.agricultural_product_store.dto.request;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateProductRequest {
    @NotNull
    private String name;
    @NotNull
    private int price;
    @NotNull
    private String sku;
    @NotNull
    private int stock;
    @NotNull
    private Long categoryID;
    private String originalLocation;
    private String description;
    private String certificateType;
    private List<String> certificateImages;
    private List<String> images;
    private String preservation;
    private Long supplierId;
}
