package com.example.agricultural_product_store.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateProductRequest {
    @NotNull
    private Long id;
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
    private String location;
    private String description;
    private List<String> certificateImages;
    private List<String> images;
    private String preservation;
}
