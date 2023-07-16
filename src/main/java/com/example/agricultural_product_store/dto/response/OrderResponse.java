package com.example.agricultural_product_store.dto.response;

import com.example.agricultural_product_store.models.entity.PaymentType;
import com.example.agricultural_product_store.models.entity.User;
import lombok.Data;
import java.util.Date;
import java.util.Set;

@Data
public class OrderResponse {
    private Long id;
    private int amount;
    private int shippingFee;
    private ShippingAddressResponse shippingAddress;
    private String status;
    private PaymentType paymentType;
    private Set<OrderItemResponse> items;
    private Date createTime;
    private User owner;
}
