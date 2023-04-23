package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CreateProductRequest;
import com.example.agricultural_product_store.models.entity.Category;
import com.example.agricultural_product_store.models.entity.Comment;
import com.example.agricultural_product_store.models.entity.Product;
import com.example.agricultural_product_store.models.entity.Supplier;
import com.example.agricultural_product_store.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class ProductService extends BaseService<Product, Long>{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final SupplierRepository supplierRepository;
    public ProductService(ProductRepository repository, CategoryRepository categoryRepository, SupplierRepository supplierRepository, CommentRepository commentRepository) {
        super(repository);
        this.productRepository = repository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.commentRepository = commentRepository;
    }
    public Product findProductById(Long id) {
        return ((ProductRepository) repository).findProductById(id);
    }
    public List<Product> findProductByCategoryId(Long id) {
        return productRepository.findAllByCategoryId(id);
    }

    public Product createProduct(CreateProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryID()).orElseThrow(
                () -> new ResourceNotFoundException("Category not found")
        );
        Supplier supplier = supplierRepository.findById(request.getSupplierId()).orElseThrow(
                () -> new ResourceNotFoundException("Supplier not found")
        );
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setSku(request.getSku());
        product.setCategory(category);
        product.setImages(new HashSet<>(request.getImages()));
        product.setCertificateImages(new HashSet<>(request.getCertificateImages()));
        product.setDescription(request.getDescription());
        product.setPreservation(request.getPreservation());
        product.setStock(request.getStock());
        product.setLocation(request.getOriginalLocation());
        product.setCertificateType(request.getCertificateType());
        product.setSupplier(supplier);
        product.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        product.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        return productRepository.save(product);
    }

    public Comment commentProduct(Long id, Comment comment) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found")
        );

        comment.setProduct(product);
        return commentRepository.save(comment);
    }
}
