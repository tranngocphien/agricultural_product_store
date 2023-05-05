package com.example.agricultural_product_store.models.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "notification")
@Data
public class Notification extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "isRead")
    private boolean isRead;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "purchase_order_id", referencedColumnName = "id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
