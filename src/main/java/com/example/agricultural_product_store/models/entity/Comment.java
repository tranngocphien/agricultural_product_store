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

    @OneToOne
    @JoinColumn(name = "product_line_id", referencedColumnName = "id")
    private ProductLine productLine;

}
