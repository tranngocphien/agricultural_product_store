package com.example.agricultural_product_store.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "order_info")
@Getter
@Setter
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private int amount;
    @Column(name = "shipping_fee")
    private int shippingFee;
    @OneToOne
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private ShippingAddress shippingAddress;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "payment_type_id", nullable = false)
    @JsonIgnore
    private PaymentType paymentType;

    @OneToMany(mappedBy = "orderInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<OrderItem> items;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User owner;

    @Column(name = "isReview")
    private boolean isReview;

}
