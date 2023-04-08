package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.models.entity.Product;
import com.example.agricultural_product_store.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService<Product, Long>{
    public ProductService(ProductRepository repository) {
        super(repository);
    }
}
