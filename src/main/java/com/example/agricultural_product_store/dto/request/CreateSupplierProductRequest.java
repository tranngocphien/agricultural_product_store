package com.example.agricultural_product_store.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateSupplierProductRequest {
    @NotNull
    private String name;
    @NotNull
    private Long categoryID;
    @NotNull
    private int expectedPrice;
    private String location;
    private String description;
    private List<String> certificateImages;
    private List<String> images;
    private String preservation;
}
