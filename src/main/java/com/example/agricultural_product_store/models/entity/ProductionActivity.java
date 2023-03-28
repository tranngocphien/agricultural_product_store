package com.example.agricultural_product_store.models.entity;

import javax.persistence.*;

@Entity(name = "production_activity")
public class ProductionActivity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_name")
    private String activityName;

    @Column(name = "description")
    private String description;

    @Column(name = "activity_type")
    private String activityType;

    @ManyToOne
    @JoinColumn(name = "product_line_id", nullable = false)
    private ProductLine productLine;

}
