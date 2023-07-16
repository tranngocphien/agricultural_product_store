package com.example.agricultural_product_store.dto.request;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
@Data
public class CreateOrderRequest {
    private int amount;
    private int shippingFee;
    @NotNull
    private Long shippingAddressId;
    private Long paymentTypeId;
    private List<OrderItemRequest> items;
}
