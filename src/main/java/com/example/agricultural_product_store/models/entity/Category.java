package com.example.agricultural_product_store.models.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "category")
@Data
public class Category extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_thumb")
    private String categoryThumb;
}
