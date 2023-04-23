package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CreateOrderRequest;
import com.example.agricultural_product_store.models.entity.*;
import com.example.agricultural_product_store.repositories.OrderItemRepository;
import com.example.agricultural_product_store.repositories.OrderRepository;
import com.example.agricultural_product_store.repositories.PaymentTypeRepository;
import com.example.agricultural_product_store.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderService extends BaseService<Order, Long> {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private PaymentTypeRepository paymentTypeRepository;

    private ProductRepository productRepository;
    OrderService(OrderRepository repository, OrderItemRepository orderItemRepository, PaymentTypeRepository paymentTypeRepository, ProductRepository productRepository) {
        super(repository);
        this.orderRepository = repository;
        this.orderItemRepository = orderItemRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request, User user) {
        PaymentType paymentType = paymentTypeRepository.findById(request.getPaymentTypeId()).orElseThrow(
                () -> new ResourceNotFoundException("Payment type not found")
        );
        Order order = new Order();
        order.setPaymentType(paymentType);
        order.setAmount(request.getAmount());
        order.setShippingFee(request.getShippingFee());
        order.setShippingAddress(request.getShippingAddress());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setStatus(OrderStatus.CONFIRMED);
        order.setOwner(user);
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        List<OrderItem> orderItemList = request.getItems().stream().map(item -> {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(
                    () -> new ResourceNotFoundException("Product not found")
            );
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(item.getAmount());
            orderItem.setProduct(product);
            orderItem.setCreateTime(new Timestamp(System.currentTimeMillis()));
            orderItem.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            return orderItem;
        }).collect(Collectors.toList());
        order.setItems(new HashSet<>(orderItemList));
        orderItemList.forEach(orderItem -> orderItem.setOrderInfo(order));
        return orderRepository.save(order);
    }

    public Order cancelOrder(Long id, User user) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order not found")
        );
        if (!Objects.equals(order.getOwner().getId(), user.getId())) {
            throw new ResourceNotFoundException("Order not found");
        }
        if(order.getStatus() == OrderStatus.CONFIRMED) {
            order.setStatus(OrderStatus.CANCELLED);
            return orderRepository.save(order);
        }
        else {
            throw new RuntimeException("Cannot cancel order");
        }

    }

    public List<Order> getListOrderByUser(Long id) {
        return orderRepository.findAllByOwnerId(id);
    }
}
