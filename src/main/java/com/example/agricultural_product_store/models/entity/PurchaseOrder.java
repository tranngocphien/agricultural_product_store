package com.example.agricultural_product_store.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "purchase_order")
@Data
public class PurchaseOrder extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_product_id", nullable = false)
    private SupplierProduct supplierProduct;

    @Column(name = "amount")
    private int amount;

    @Column(name = "price")
    private int price;

    @Column(name = "status")
    private String status;

    @Column(name = "harvest_at")
    private Date harvestAt;

    @Column(name = "note")
    private String note;

}

