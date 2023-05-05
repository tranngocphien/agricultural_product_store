package com.example.agricultural_product_store.models.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "shipping_address")
@Data
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "province_id")
    private String provinceId;
    @Column(name = "district_id")
    private String districtId;
    @Column(name = "ward_id")
    private String wardId;
    @Column(name = "street")
    private String street;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
