package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.models.entity.PaymentType;
import com.example.agricultural_product_store.repositories.PaymentTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentTypeService extends BaseService<PaymentType, Long> {
    private PaymentTypeRepository paymentTypeRepository;
    PaymentTypeService(PaymentTypeRepository repository) {
        super(repository);
        this.paymentTypeRepository = repository;
    }

}
