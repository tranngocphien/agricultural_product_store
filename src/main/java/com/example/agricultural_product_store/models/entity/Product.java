package com.example.agricultural_product_store.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "product")
@Data
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "sku")
    private String sku;

    @Column(name = "stock")
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;

    @Column(name = "origin")
    private String origin;

    @Column(name = "description")
    private String description;
    @ElementCollection
    @CollectionTable(name = "certificate_image", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image")
    private Set<String> certificateImages;

    @ElementCollection
    @CollectionTable(name = "product_image", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image")
    private Set<String> images;

    @Column(name = "preservation")
    private String preservation;

    @Column(name = "direction_for_use")
    private String directionForUse;

    @OneToMany(mappedBy = "product")
    private Set<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
}


