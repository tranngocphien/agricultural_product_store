package com.example.agricultural_product_store.services.template;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CreateSupplierProductRequest;
import com.example.agricultural_product_store.dto.request.UpdateSupplierProductRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.Category;
import com.example.agricultural_product_store.models.entity.SupplierProduct;
import com.example.agricultural_product_store.models.entity.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public interface SupplierProductService extends BaseService<SupplierProduct, Long>{
    public SupplierProduct create(CreateSupplierProductRequest request, User owner);
    public SupplierProduct update(UpdateSupplierProductRequest request, User owner);


    public ResponseData delete(Long id, User owner);
    public List<SupplierProduct> getSupplierProductOfUser(User user);

    public List<SupplierProduct> search(String keyword);
}
