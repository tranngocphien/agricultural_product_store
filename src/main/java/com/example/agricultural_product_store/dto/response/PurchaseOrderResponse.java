package com.example.agricultural_product_store.dto.response;

import com.example.agricultural_product_store.models.entity.PurchaseOrderStatus;
import com.example.agricultural_product_store.models.entity.SupplierProduct;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class PurchaseOrderResponse {
    private Long id;
    private SupplierProductResponse supplierProduct;
    private int amount;
    private int price;
    private PurchaseOrderStatus status;
    private Date harvestAt;
    private String note;
}
