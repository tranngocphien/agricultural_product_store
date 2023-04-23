package com.example.agricultural_product_store.dto.response;

import com.example.agricultural_product_store.models.entity.OrderItem;
import com.example.agricultural_product_store.models.entity.PaymentType;
import com.example.agricultural_product_store.models.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
public class OrderResponse {
    private Long id;
    private int amount;
    private int shippingFee;
    private String shippingAddress;
    private String status;
    private PaymentType paymentType;
    private Set<OrderItemResponse> items;
    private String phoneNumber;
}
