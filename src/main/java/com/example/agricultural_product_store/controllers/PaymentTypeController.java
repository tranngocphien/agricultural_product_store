package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.PaymentType;
import com.example.agricultural_product_store.services.PaymentTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payment-types")
public class PaymentTypeController {
    private final PaymentTypeService paymentTypeService;

    public PaymentTypeController(PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping
    public ResponseData<List<PaymentType>> getPaymentTypes() {
        return ResponseData.onSuccess(paymentTypeService.list());
    }
}
