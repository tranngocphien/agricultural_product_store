package com.example.agricultural_product_store.services.template;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface BaseService<T, ID> {
    public List<T> list();
    public List<T> list(Pageable pageable);
    public Page<T> listPage(Pageable pageable);
    public Optional<T> detail(ID id);
}
