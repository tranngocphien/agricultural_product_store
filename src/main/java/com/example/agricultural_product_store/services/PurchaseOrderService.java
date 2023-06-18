package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CreatePurchaseOrderRequest;
import com.example.agricultural_product_store.dto.request.UpdatePurchaseOrderRequest;
import com.example.agricultural_product_store.models.entity.PurchaseOrder;
import com.example.agricultural_product_store.models.entity.PurchaseOrderStatus;
import com.example.agricultural_product_store.models.entity.SupplierProduct;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.PurchaseOrderRepository;
import com.example.agricultural_product_store.repositories.SupplierProductRepository;
import com.example.agricultural_product_store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PurchaseOrderService extends BaseService<PurchaseOrder, Long> {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierProductRepository supplierProductRepository;
    private final UserRepository userRepository;

    PurchaseOrderService(PurchaseOrderRepository repository, SupplierProductRepository supplierProductRepository, UserRepository userRepository) {
        super(repository);
        this.purchaseOrderRepository = repository;
        this.supplierProductRepository = supplierProductRepository;
        this.userRepository = userRepository;
    }

    public PurchaseOrder create(CreatePurchaseOrderRequest request) {
        SupplierProduct supplierProduct = supplierProductRepository.findById(request.getSupplierProductId()).orElseThrow(
                () -> new ResourceNotFoundException("Supplier product not found")
        );
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSupplierProduct(supplierProduct);
        purchaseOrder.setNote(request.getNote());
        purchaseOrder.setAmount(request.getAmount());
        purchaseOrder.setPrice(request.getPrice());
        purchaseOrder.setStatus(PurchaseOrderStatus.IDLE);
        purchaseOrder.setHarvestAt(request.getHarvestAt());
        purchaseOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
        purchaseOrder.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder delete(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Supplier product not found")
        );
        purchaseOrderRepository.delete(purchaseOrder);
        return purchaseOrder;
    }

    public PurchaseOrder update(UpdatePurchaseOrderRequest request) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Purchase order not found")
        );
        purchaseOrder.setNote(request.getNote());
        purchaseOrder.setAmount(request.getAmount());
        purchaseOrder.setPrice(request.getPrice());
        purchaseOrder.setStatus(request.getStatus());
        purchaseOrder.setHarvestAt(request.getHarvestAt());
        purchaseOrder.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder confirmPurchaseOrder(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Purchase order not found")
        );
        purchaseOrder.setStatus(PurchaseOrderStatus.PROCESS);
        purchaseOrder.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public List<PurchaseOrder> getAllByUserId(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return purchaseOrderRepository.findPurchaseOrderByUserId(user.getId());
    }
}
