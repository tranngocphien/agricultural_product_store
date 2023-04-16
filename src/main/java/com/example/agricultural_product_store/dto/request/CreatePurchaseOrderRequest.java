package com.example.agricultural_product_store.dto.request;
import lombok.Data;
import java.util.Date;

@Data
public class CreatePurchaseOrderRequest {
    private Long supplierProductId;
    private int amount;
    private int price;
    private Date harvestAt;
    private String note;
}
