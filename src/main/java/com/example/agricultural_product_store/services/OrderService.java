package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.config.exception.BusinessException;
import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CommentRequest;
import com.example.agricultural_product_store.dto.request.CreateOrderRequest;
import com.example.agricultural_product_store.dto.response.PredictSaleResponse;
import com.example.agricultural_product_store.models.entity.*;
import com.example.agricultural_product_store.repositories.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService extends BaseService<Order, Long> {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private PaymentTypeRepository paymentTypeRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final CommentRepository commentRepository;
    @PersistenceContext
    private EntityManager manager;

    private ProductRepository productRepository;
    OrderService(OrderRepository repository, OrderItemRepository orderItemRepository, PaymentTypeRepository paymentTypeRepository, ProductRepository productRepository, ShippingAddressRepository shippingAddressRepository, CommentRepository commentRepository) {
        super(repository);
        this.orderRepository = repository;
        this.orderItemRepository = orderItemRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.productRepository = productRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request, User user) {
        PaymentType paymentType = paymentTypeRepository.findById(request.getPaymentTypeId()).orElseThrow(
                () -> new ResourceNotFoundException("Payment type not found")
        );
        ShippingAddress shippingAddress = shippingAddressRepository.findById(request.getShippingAddressId()).orElseThrow(
                () -> new ResourceNotFoundException("Shipping address not found"));
        Order order = new Order();
        order.setPaymentType(paymentType);
        order.setAmount(request.getAmount());
        order.setShippingFee(request.getShippingFee());
        order.setShippingAddress(shippingAddress);
        order.setStatus(OrderStatus.IDLE);
        order.setOwner(user);
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        List<OrderItem> orderItemList = request.getItems().stream().map(item -> {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(
                    () -> new ResourceNotFoundException("Product not found")
            );
            if(product.getStock() < item.getAmount()) {
                throw new BusinessException("Not enough product in stock");
            }
            product.setStock(product.getStock() - item.getAmount());
            productRepository.save(product);
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
        if(order.getStatus() == OrderStatus.IDLE) {
            order.setStatus(OrderStatus.CANCELLED);
            return orderRepository.save(order);
        }
        else {
            throw new BusinessException("Không thể hủy đơn hàng này");
        }
    }

    public Order updateStatus(Long id, User user, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order not found")
        );
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<Order> getListOrderByUser(Long id) {
        return orderRepository.findAllByOwnerId(id);
    }

    public Comment commentProduct(User user, CommentRequest request) {
        OrderItem orderItem = orderItemRepository.findById(request.getOrderItemId()).orElseThrow(
                () -> new ResourceNotFoundException("Order item not found")
        );
        if (!Objects.equals(orderItem.getOrderInfo().getOwner().getId(), user.getId())) {
            throw new ResourceNotFoundException("Order item not found");
        }
        orderItem.setHasReview(true);
        orderItemRepository.save(orderItem);
        Product product = orderItem.getProduct();
        Comment comment = new Comment();
        comment.setOwner(user);
        comment.setProduct(product);
        comment.setRate(request.getRate());
        comment.setContent(request.getContent());
        comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
        comment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return commentRepository.save(comment);
    }

    public List<Map<String, Object>> getSalesByMonth(Long productId) {
        return orderItemRepository.getMonthlySales(productId);

    }

    public List<Map<String, Object>> getDailySales(Long productId) {
        return orderItemRepository.getDailySales(productId);    }

    public List<PredictSaleResponse> predictSales(Long productId) {
        List sales = getSalesByMonth(productId);

//        List<Double> saleValue = sales.stream().map(entry -> entry.getTotal()).collect(Collectors.toList());
        List<Double> saleValue = new ArrayList<>();
        List<Double> predict = TripleExponentialSmoothing.forecast(saleValue, 0.1, 0.1, 0.1, 12, 6);
        List<PredictSaleResponse> result = new ArrayList<>();
        for (int i = 0; i < sales.size(); i++) {
//            result.add(new PredictSaleResponse(
//                    sales.get(i).getKey(),
//                    sales.get(i).getValue().intValue(),
//                    predict.get(i)
//            ));
        }
        for(int i = sales.size(); i < predict.size(); i++) {
            result.add(new PredictSaleResponse(
                    "2021-" + (i + 1),
                    0,
                    predict.get(i)
            ));
        }
        return result;
    }
}
