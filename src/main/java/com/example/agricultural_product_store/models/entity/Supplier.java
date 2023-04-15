package com.example.agricultural_product_store.models.entity;

import lombok.Data;
import javax.persistence.*;

@Entity(name = "supplier")
@Data
public class Supplier extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "location")
    private String location;
    @Column(name = "description")
    private String description;
    @Column(name = "brand_image")
    private String brandImage;

    @Column(name = "id_number")
    private String idNumber;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User owner;
}