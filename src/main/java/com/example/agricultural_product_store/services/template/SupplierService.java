package com.example.agricultural_product_store.services.template;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.RegisterSupplierRequest;
import com.example.agricultural_product_store.dto.request.UpdateSupplierRequest;
import com.example.agricultural_product_store.models.entity.ERole;
import com.example.agricultural_product_store.models.entity.Role;
import com.example.agricultural_product_store.models.entity.Supplier;
import com.example.agricultural_product_store.models.entity.User;
import org.springframework.security.core.Authentication;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface SupplierService extends BaseService<Supplier, Long> {

    public Supplier createSupplier(RegisterSupplierRequest request, User owner);

    public Supplier getSupplierInfo(Authentication authentication);

    public Supplier getSupplierInfoByUserId(Long id);

    public Supplier getSupplierInfoById(Long id);

    public Supplier updateSupplier(UpdateSupplierRequest request, Authentication authentication);
}
