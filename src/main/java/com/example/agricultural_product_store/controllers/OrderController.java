package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.request.CreateOrderRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.Order;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.UserRepository;
import com.example.agricultural_product_store.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "api/orders")
public class OrderController  {
    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseData<Order> create(Principal principal, @RequestBody CreateOrderRequest request) {
        User user = userRepository.findByUsername(principal.getName()).get();
        return ResponseData.onSuccess(orderService.createOrder(request, user));
    }

    @GetMapping() // get all order
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseData<List<Order>> getAll(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        return ResponseData.onSuccess(orderService.getListOrderByUser(user.getId()));
    }
}
