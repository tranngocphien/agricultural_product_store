package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.response.*;
import com.example.agricultural_product_store.models.entity.Order;
import com.example.agricultural_product_store.models.entity.PurchaseOrder;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.services.OrderServiceImpl;
import com.example.agricultural_product_store.services.PurchaseOrderServiceImpl;
import com.example.agricultural_product_store.services.SupplierServiceImpl;
import com.example.agricultural_product_store.services.UserServiceImpl;
import com.example.agricultural_product_store.services.template.OrderService;
import com.example.agricultural_product_store.services.template.PurchaseOrderService;
import com.example.agricultural_product_store.services.template.SupplierService;
import com.example.agricultural_product_store.services.template.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final OrderService orderService;
    private final PurchaseOrderService purchaseOrderService;
    private final SupplierService supplierService;
    private final ModelMapper modelMapper;

    public AdminController(UserService userService, OrderService orderService, PurchaseOrderService purchaseOrderService, SupplierService supplierService, ModelMapper modelMapper) {
        this.userService = userService;
        this.orderService = orderService;
        this.supplierService = supplierService;
        this.modelMapper = modelMapper;
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/users")
    public ResponseData<List<UserResponse>> getUsers(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userService.listPage(pageable);
        List<UserResponse> users = userPage.getContent().stream().map(product -> {
            return modelMapper.map(product, UserResponse.class);
        }).collect(Collectors.toList());
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setPage(userPage.getNumber());
        paginationInfo.setSize(users.size());
        paginationInfo.setTotalPage(userPage.getTotalPages());
        paginationInfo.setTotalElement(userPage.getTotalElements());
        return ResponseData.onSuccess(users, paginationInfo);
    }

    @GetMapping("/orders")
    public ResponseData<List<OrderResponse>> getOrders(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size,  Sort.by("createTime").descending());
        Page<Order> orderPage = orderService.listPage(pageable);
        List<OrderResponse> orders = orderPage.getContent().stream().map(order -> {
            return modelMapper.map(order, OrderResponse.class);
        }).collect(Collectors.toList());
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setPage(orderPage.getNumber());
        paginationInfo.setSize(orders.size());
        paginationInfo.setTotalPage(orderPage.getTotalPages());
        paginationInfo.setTotalElement(orderPage.getTotalElements());
        return ResponseData.onSuccess(orders, paginationInfo);
    }

    @GetMapping("/purchaseOrders")
    public ResponseData<List<PurchaseOrderResponse>> getPurchaseOrders(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Page<PurchaseOrder> orderPage = purchaseOrderService.listPage(pageable);
        List<PurchaseOrderResponse> orders = orderPage.getContent().stream().map(order -> {
            return modelMapper.map(order, PurchaseOrderResponse.class);
        }).collect(Collectors.toList());
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setPage(orderPage.getNumber());
        paginationInfo.setSize(orders.size());
        paginationInfo.setTotalPage(orderPage.getTotalPages());
        paginationInfo.setTotalElement(orderPage.getTotalElements());
        return ResponseData.onSuccess(orders, paginationInfo);
    }

    @GetMapping("/monthlySales/{productId}")
    public ResponseData<List<PredictSaleResponse>> getMonthlySales(
            @PathVariable Long productId) {
        return ResponseData.onSuccess(orderService.predictMonthlySales(productId));
    }

    @GetMapping("/dailySales/{productId}")
    public ResponseData<List<PredictSaleResponse>> getDailySales(
            @PathVariable Long productId) {
        return ResponseData.onSuccess(orderService.predictDailySales(productId));
    }



}
