package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.RegisterSupplierRequest;
import com.example.agricultural_product_store.dto.request.UpdateSupplierRequest;
import com.example.agricultural_product_store.models.entity.ERole;
import com.example.agricultural_product_store.models.entity.Role;
import com.example.agricultural_product_store.models.entity.Supplier;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.RoleRepository;
import com.example.agricultural_product_store.repositories.SupplierRepository;
import com.example.agricultural_product_store.repositories.UserRepository;
import com.example.agricultural_product_store.services.template.SupplierService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class SupplierServiceImpl extends BaseService<Supplier, Long> implements SupplierService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    SupplierServiceImpl(SupplierRepository repository, RoleRepository roleRepository, UserRepository userRepository) {
        super(repository);
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.supplierRepository = repository;
    }

    public Supplier createSupplier(RegisterSupplierRequest request, User owner) {
        Role roleSupplier = roleRepository.findByName(ERole.ROLE_SUPPLIER);
        boolean isAdmin = owner.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN));
        if(!isAdmin) {
            owner.getRoles().add(roleSupplier);
            userRepository.save(owner);
        }
        Supplier supplier = new Supplier();
        if(!isAdmin) {
            supplier.setOwner(owner);
        }
        supplier.setName(request.getName());
        supplier.setLocation(request.getLocation());
        supplier.setDescription(request.getDescription());
        supplier.setBrandImage(request.getBrandImage());
        supplier.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        supplier.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(supplier);
    }

    public Supplier getSupplierInfo(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new ResourceNotFoundException("Error: User is not found.")
        );

        return supplierRepository.findByOwner_Id(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Error: Supplier is not found.")
        );
    }

    public Supplier getSupplierInfoByUserId(Long id) {
        return supplierRepository.findByOwner_Id(id).orElseThrow(
                () -> new ResourceNotFoundException("Error: Supplier is not found.")
        );
    }

    public Supplier getSupplierInfoById(Long id) {
        return supplierRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Error: Supplier is not found.")
        );
    }

    public Supplier updateSupplier(UpdateSupplierRequest request, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new ResourceNotFoundException("Error: User is not found.")
        );
        Supplier supplier = supplierRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Error: Supplier is not found.")
        );
        supplier.setName(request.getName());
        supplier.setLocation(request.getLocation());
        supplier.setDescription(request.getDescription());
        supplier.setBrandImage(request.getBrandImage());
        supplier.setIdNumber(request.getIdNumber());
        supplier.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(supplier);
    }
}
