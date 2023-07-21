package com.example.agricultural_product_store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class PredictSaleResponse {
    private String date;
    private double total;
    private double predict;
}
