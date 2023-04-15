package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.dto.request.RegisterSupplierRequest;
import com.example.agricultural_product_store.models.entity.Supplier;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.SupplierRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class SupplierService extends BaseService<Supplier, Long> {
    SupplierService(SupplierRepository repository) {
        super(repository);
    }

    public Supplier createSupplier(RegisterSupplierRequest request, User owner) {
        Supplier supplier = new Supplier();
        supplier.setOwner(owner);
        supplier.setName(request.getName());
        supplier.setLocation(request.getLocation());
        supplier.setDescription(request.getDescription());
        supplier.setBrandImage(request.getBrandImage());
        supplier.setIdNumber(request.getIdNumber());
        supplier.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        supplier.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(supplier);
    }
}
