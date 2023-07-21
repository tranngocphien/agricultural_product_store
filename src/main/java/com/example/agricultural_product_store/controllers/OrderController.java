package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CommentRequest;
import com.example.agricultural_product_store.dto.request.CreateOrderRequest;
import com.example.agricultural_product_store.dto.request.UpdateOrderRequest;
import com.example.agricultural_product_store.dto.response.CommentResponse;
import com.example.agricultural_product_store.dto.response.OrderResponse;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.Order;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.UserRepository;
import com.example.agricultural_product_store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/orders")
public class OrderController  {
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, UserRepository userRepository,ModelMapper modelMapper) {
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/create")
    public ResponseData<OrderResponse> placeOrder(Principal principal, @RequestBody CreateOrderRequest request) {
        User user = userRepository.findByUsername(principal.getName()).get();
        return ResponseData.onSuccess(modelMapper.map(orderService.createOrder(request, user), OrderResponse.class));
    }

    @PostMapping(value = "/updateStatus")
    public ResponseData<OrderResponse> updateStatusOrder(Principal principal, @RequestBody UpdateOrderRequest request) {
        User user = userRepository.findByUsername(principal.getName()).get();
        return ResponseData.onSuccess(modelMapper.map(orderService.updateStatus(request.getId(), user, request.getStatus()), OrderResponse.class));
    }

    @PostMapping(value = "/cancel/{id}")
    public ResponseData<OrderResponse> cancelOrder(Principal principal, @PathVariable("id") Long id) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return ResponseData.onSuccess(modelMapper.map(orderService.cancelOrder(id, user), OrderResponse.class));
    }

    @GetMapping() // get own order
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseData<List<OrderResponse>> getAll(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        List<Order> ownOrder = orderService.getListOrderByUser(user.getId());
        List<OrderResponse> result = ownOrder.stream().map(order -> modelMapper.map(order, OrderResponse.class)).collect(Collectors.toList());
        return ResponseData.onSuccess(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseData<OrderResponse> detail(Principal principal, @PathVariable Long id) {
        User user = userRepository.findByUsername(principal.getName()).get();
        Order order = orderService.detail(id).orElseThrow(
                () -> new ResourceNotFoundException("Order not found")
        );
        if(!Objects.equals(user.getId(), order.getOwner().getId())) {
            throw new ResourceNotFoundException("Order not found");
        }
        return ResponseData.onSuccess(modelMapper.map(order, OrderResponse.class));
    }

    @PostMapping(value = "/rate")
    public ResponseData<CommentResponse> commentProduct(Principal principal, @RequestBody CommentRequest request) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return ResponseData.onSuccess(modelMapper.map(orderService.commentProduct(user, request), CommentResponse.class));
    }
}
