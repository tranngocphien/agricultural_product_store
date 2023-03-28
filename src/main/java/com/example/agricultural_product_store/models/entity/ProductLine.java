package com.example.agricultural_product_store.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "product_line")
@Data
public class ProductLine extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "productLine")
    private Set<Product> products;

    @OneToMany(mappedBy = "productLine")
    private Set<ProductionActivity> activities;

    @OneToMany(mappedBy = "productLine")
    private Set<PurchaseOrder> purchaseOrders;

}
