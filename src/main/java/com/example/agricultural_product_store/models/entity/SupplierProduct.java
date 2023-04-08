package com.example.agricultural_product_store.models.entity;

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

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "certificate_type")
    private String certificateType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "certificate_image",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private Set<Image> certificateImages;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "supplier_product_image",
            joinColumns = @JoinColumn(name = "supplier_product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private Set<Image> images;

    @Column(name = "preservation")
    private String preservation;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "supplierProduct")
    private Set<PurchaseOrder> purchaseOrders;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User owner;

}
