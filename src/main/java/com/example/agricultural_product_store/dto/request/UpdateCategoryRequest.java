package com.example.agricultural_product_store.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCategoryRequest {
    @NotNull
    private Long categoryId;
    @NotNull
    private String categoryName;
    @NotNull
    private String categoryThumb;
}
