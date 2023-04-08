package com.example.agricultural_product_store.models.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "comment")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "rate")
    private int rate;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User owner;

}
