package com.example.agricultural_product_store.dto.response;

import lombok.Data;

@Data
public class PaginationInfo {
    private int page;
    private int size;
    private int totalPage;
    private long totalElement;
}
