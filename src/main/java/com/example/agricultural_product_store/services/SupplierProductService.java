package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CreateSupplierProductRequest;
import com.example.agricultural_product_store.models.entity.Category;
import com.example.agricultural_product_store.models.entity.SupplierProduct;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.CategoryRepository;
import com.example.agricultural_product_store.repositories.SupplierProductRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class SupplierProductService extends BaseService<SupplierProduct, Long> {
    private SupplierProductRepository supplierProductRepository;
    private CategoryRepository categoryRepository;

    SupplierProductService(SupplierProductRepository repository, CategoryRepository categoryRepository) {
        super(repository);
        this.supplierProductRepository = repository;
        this.categoryRepository = categoryRepository;
    }

    public SupplierProduct create(CreateSupplierProductRequest request, User owner) {
        Category category = categoryRepository.findById(request.getCategoryID()).orElseThrow(
                () -> new ResourceNotFoundException("Category not found"));
        SupplierProduct supplierProduct = new SupplierProduct();
        supplierProduct.setOwner(owner);
        supplierProduct.setProductName(request.getName());
        supplierProduct.setExpectedPrice(request.getExpectedPrice());
        supplierProduct.setLocation(request.getOriginalLocation());
        supplierProduct.setImages(new HashSet<>(request.getImages()));
        supplierProduct.setCertificateImages(new HashSet<>(request.getCertificateImages()));
        supplierProduct.setPreservation(request.getPreservation());
        supplierProduct.setDescription(request.getDescription());
        supplierProduct.setCategory(category);
        supplierProduct.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        supplierProduct.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(supplierProduct);
    }
    public List<SupplierProduct> getSupplierProductOfUser(User user) {
        return supplierProductRepository.findAllByOwner(user);
    }
}
