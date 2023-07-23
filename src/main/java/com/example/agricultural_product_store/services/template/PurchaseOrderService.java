package com.example.agricultural_product_store.services.template;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CreatePurchaseOrderRequest;
import com.example.agricultural_product_store.dto.request.UpdatePurchaseOrderRequest;
import com.example.agricultural_product_store.models.entity.PurchaseOrder;
import com.example.agricultural_product_store.models.entity.PurchaseOrderStatus;
import com.example.agricultural_product_store.models.entity.SupplierProduct;
import com.example.agricultural_product_store.models.entity.User;
import org.springframework.security.core.Authentication;

import java.sql.Timestamp;
import java.util.List;

public interface PurchaseOrderService extends BaseService<PurchaseOrder, Long> {
    public PurchaseOrder create(CreatePurchaseOrderRequest request);

    public PurchaseOrder delete(Long id);

    public PurchaseOrder update(UpdatePurchaseOrderRequest request);

    public PurchaseOrder confirmPurchaseOrder(Long id);

    public PurchaseOrder rejectPurchaseOrder(Long id);

    public List<PurchaseOrder> getAllByUserId(Authentication authentication);
}
