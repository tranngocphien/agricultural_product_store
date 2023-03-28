package com.example.agricultural_product_store.models.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "purchase_order")
public class PurchaseOrder extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_line_id", nullable = false)
    private ProductLine productLine;

    @Column(name = "amount")
    private int amount;

    @Column(name = "price")
    private int price;

    @Column(name = "status")
    private String status;

    @Column(name = "harvest_at")
    private Date harvestAt;


}

