package com.example.agricultural_product_store.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "supplier_product")
@Data
public class SupplierProduct extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "expected_price")
    private int expectedPrice;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "certificate_type")
    private String certificateType;

    @ElementCollection
    @CollectionTable(name = "certificate_image", joinColumns = @JoinColumn(name = "supplier_product_id"))
    @Column(name = "image")
    private Set<String> certificateImages;

    @ElementCollection
    @CollectionTable(name = "supplier_product_image", joinColumns = @JoinColumn(name = "supplier_product_id"))
    @Column(name = "image")
    private Set<String> images;

    @Column(name = "preservation")
    private String preservation;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "supplierProduct")
    @JsonIgnore
    private Set<PurchaseOrder> purchaseOrders;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User owner;

}
