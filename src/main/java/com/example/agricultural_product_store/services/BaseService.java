package com.example.agricultural_product_store.services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class BaseService<T, ID> {
    protected JpaRepository<T, ID> repository;

    BaseService(JpaRepository repository) {
        this.repository = repository;
    }

    public List<T> list() {
        return repository.findAll();
    }

    public Optional<T> detail(ID id) {
        return repository.findById(id);
    }

}
