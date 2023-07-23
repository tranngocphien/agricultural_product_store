package com.example.agricultural_product_store.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BaseService<T, ID> {
    protected JpaRepository<T, ID> repository;
    BaseService(JpaRepository repository) {
        this.repository = repository;
    }
    public List<T> list() {
        return repository.findAll();
    }
    public List<T> list(Pageable pageable){
        return repository.findAll(pageable).get().collect(Collectors.toList());
    }
    public Page<T> listPage(Pageable pageable){
        return repository.findAll(pageable);
    }
    public Optional<T> detail(ID id) {
        return repository.findById(id);
    }

}
