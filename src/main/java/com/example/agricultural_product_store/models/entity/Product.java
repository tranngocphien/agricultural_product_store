package com.example.agricultural_product_store.models.entity;

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

    @Column(name = "price")
    private int price;

    @Column(name = "sku")
    private int sku;

    @Column(name = "stock")
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "certificate_type")
    private String certificateType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "certificate_image",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private Set<Image> certificateImages;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_image",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private Set<Image> images;

    @Column(name = "preservation")
    private String preservation;

    @OneToMany(mappedBy = "product")
    private Set<Comment> comments;

}
