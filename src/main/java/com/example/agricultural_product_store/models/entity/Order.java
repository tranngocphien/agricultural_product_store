package com.example.agricultural_product_store.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "order_info")
@Data
public class Order extends BaseEntity{

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

    @ManyToOne
    @JoinColumn(name = "payment_type_id", nullable = false)
    @JsonIgnore
    private PaymentType paymentType;

    @OneToMany(mappedBy = "orderInfo")
    private Set<OrderItem> items;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User owner;

}
