package com.example.agricultural_product_store.dto.request;
import com.example.agricultural_product_store.models.entity.OrderStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateOrderRequest {
    private Long id;
    private OrderStatus status;
}
