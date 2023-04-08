package com.example.agricultural_product_store.models.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "image")
@Data
public class Image {
    @Id
    private Long id;

    @Column(name = "url")
    private String url;
}
