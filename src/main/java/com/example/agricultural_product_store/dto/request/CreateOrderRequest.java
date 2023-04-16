package com.example.agricultural_product_store.dto.request;
import lombok.Data;
import java.util.List;
@Data
public class CreateOrderRequest {
    private int amount;
    private int shippingFee;
    private String shippingAddress;
    private Long paymentTypeId;
    private List<OrderItemRequest> items;
    private String phoneNumber;

}
