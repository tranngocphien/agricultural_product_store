package com.example.agricultural_product_store.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "product")
@Data
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "price")
    private int price;

    @Column(name = "sku")
    private int sku;

    @Column(name = "stock")
    private int stock;

    @Column(name = "harvest_at")
    private Date harvestAt;

    @ManyToOne
    @JoinColumn(name = "product_line_id", nullable = false)
    private ProductLine productLine;

}
