package com.example.agricultural_product_store.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "order_info")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private int amount;
    @Column(name = "shipping_fee")
    private int shippingFee;
    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "orderInfo")
    private Set<OrderItem> items;

    @Column(name = "phone_number")
    private String phoneNumber;

}
