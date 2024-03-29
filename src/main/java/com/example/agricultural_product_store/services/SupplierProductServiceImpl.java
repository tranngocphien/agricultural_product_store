package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CreateSupplierProductRequest;
import com.example.agricultural_product_store.dto.request.UpdateSupplierProductRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.Category;
import com.example.agricultural_product_store.models.entity.SupplierProduct;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.CategoryRepository;
import com.example.agricultural_product_store.repositories.SupplierProductRepository;
import com.example.agricultural_product_store.services.template.SupplierProductService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class SupplierProductServiceImpl extends BaseService<SupplierProduct, Long> implements SupplierProductService {
    private SupplierProductRepository supplierProductRepository;
    private CategoryRepository categoryRepository;

    SupplierProductServiceImpl(SupplierProductRepository repository, CategoryRepository categoryRepository) {
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
        supplierProduct.setLocation(request.getLocation());
        supplierProduct.setImages(new HashSet<>(request.getImages()));
        supplierProduct.setCertificateImages(new HashSet<>(request.getCertificateImages()));
        supplierProduct.setPreservation(request.getPreservation());
        supplierProduct.setDescription(request.getDescription());
        supplierProduct.setCategory(category);
        supplierProduct.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        supplierProduct.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(supplierProduct);
    }

    public SupplierProduct update(UpdateSupplierProductRequest request, User owner) {
        Category category = categoryRepository.findById(request.getCategoryID()).orElseThrow(
                () -> new ResourceNotFoundException("Category not found"));
        SupplierProduct supplierProduct = supplierProductRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Supplier product not found"));
        if(!Objects.equals(owner.getId(), supplierProduct.getOwner().getId())) {
            throw new ResourceNotFoundException("You don't have permission to update this product");
        }
        supplierProduct.setProductName(request.getName());
        supplierProduct.setExpectedPrice(request.getExpectedPrice());
        supplierProduct.setLocation(request.getLocation());
        supplierProduct.setImages(new HashSet<>(request.getImages()));
        supplierProduct.setCertificateImages(new HashSet<>(request.getCertificateImages()));
        supplierProduct.setPreservation(request.getPreservation());
        supplierProduct.setDescription(request.getDescription());
        supplierProduct.setCategory(category);
        supplierProduct.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(supplierProduct);
    }


    public ResponseData delete(Long id, User owner) {
        SupplierProduct supplierProduct = supplierProductRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Supplier product not found"));
        if(!Objects.equals(owner.getId(), supplierProduct.getOwner().getId())) {
            throw new ResourceNotFoundException("You don't have permission to update this product");
        }
        repository.delete(supplierProduct);
        return ResponseData.onSuccess("Delete product successfully");
    }
    public List<SupplierProduct> getSupplierProductOfUser(User user) {
        return supplierProductRepository.findAllByOwner(user);
    }

    public List<SupplierProduct> search(String keyword) {
        return supplierProductRepository.findByProductNameContains(keyword);
    }
}
