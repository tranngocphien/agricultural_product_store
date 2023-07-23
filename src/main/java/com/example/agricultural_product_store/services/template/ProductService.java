package com.example.agricultural_product_store.services.template;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CommentRequest;
import com.example.agricultural_product_store.dto.request.CreateProductRequest;
import com.example.agricultural_product_store.dto.request.UpdateProductRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.*;
import com.example.agricultural_product_store.repositories.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface ProductService extends BaseService<Product, Long> {
    Product findProductById(Long id);
    List<Product> findProductByCategoryId(Long id);

    Product createProduct(CreateProductRequest request);

    Product updateProduct(UpdateProductRequest request);

    ResponseData deleteProduct(@PathVariable Long id);

    Comment commentProduct(Authentication authentication, Long id, CommentRequest request);

    List<Product> favouriteProduct(User user, Long id);

    List<Product> searchProduct(String keyword);
}
