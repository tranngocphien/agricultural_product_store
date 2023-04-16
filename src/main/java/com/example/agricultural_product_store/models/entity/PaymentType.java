package com.example.agricultural_product_store.models.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "payment_type")
@Data
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

}
