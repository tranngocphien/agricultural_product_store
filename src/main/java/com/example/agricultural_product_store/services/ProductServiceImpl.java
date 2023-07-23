package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CommentRequest;
import com.example.agricultural_product_store.dto.request.CreateProductRequest;
import com.example.agricultural_product_store.dto.request.UpdateProductRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.*;
import com.example.agricultural_product_store.repositories.*;
import com.example.agricultural_product_store.services.template.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ProductServiceImpl extends BaseService<Product, Long> implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    public ProductServiceImpl(ProductRepository repository, CategoryRepository categoryRepository, SupplierRepository supplierRepository, CommentRepository commentRepository, UserRepository userRepository) {
        super(repository);
        this.productRepository = repository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
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
        product.setOrigin(request.getLocation());
        product.setSupplier(supplier);
        product.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        product.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        return productRepository.save(product);
    }

    public Product updateProduct(UpdateProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryID()).orElseThrow(
                () -> new ResourceNotFoundException("Category not found")
        );
        Product product = productRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Product not found")
        );
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setSku(request.getSku());
        product.setCategory(category);
        product.setImages(new HashSet<>(request.getImages()));
        product.setCertificateImages(new HashSet<>(request.getCertificateImages()));
        product.setDescription(request.getDescription());
        product.setPreservation(request.getPreservation());
        product.setStock(request.getStock());
        product.setOrigin(request.getLocation());
        product.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        return productRepository.save(product);
    }

    public ResponseData deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found")
        );
        productRepository.delete(product);
        return ResponseData.onSuccess("Delete product successfully");
    }

    public Comment commentProduct(Authentication authentication, Long id, CommentRequest request) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found")
        );
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        Comment comment = new Comment();
        comment.setProduct(product);
        comment.setContent(request.getContent());
        comment.setRate(request.getRate());
        comment.setProduct(product);
        comment.setOwner(user);
        return commentRepository.save(comment);
    }

    public List<Product> favouriteProduct(User user, Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found")
        );
        user.getFavourite().add(product);
        userRepository.save(user);
        List<Product> products = new ArrayList<>();
        products.addAll(user.getFavourite());
        return products;
    }

    public List<Product> searchProduct(String keyword) {
        return productRepository.findAllByNameContains(keyword);
    }
    public List<Product> bestSellerProduct() {
        return productRepository.bestSeller();
    }
}
