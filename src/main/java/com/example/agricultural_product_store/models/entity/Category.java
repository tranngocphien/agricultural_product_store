package com.example.agricultural_product_store.models.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "category")
@Data
public class Category extends BaseEntity {
    @Id
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_thumb")
    private String categoryThumb;
}
