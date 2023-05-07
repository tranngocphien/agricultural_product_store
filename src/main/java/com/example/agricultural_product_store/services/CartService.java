package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.UpdateCartRequest;
import com.example.agricultural_product_store.models.entity.Cart;
import com.example.agricultural_product_store.models.entity.CartItem;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.CartRepository;
import com.example.agricultural_product_store.repositories.ProductRepository;
import com.example.agricultural_product_store.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService extends BaseService {
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    CartService(CartRepository repository, UserRepository userRepository, ProductRepository productRepository) {
        super(repository);
        this.cartRepository = repository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
    public Cart getCartUser(User user) {
        return cartRepository.findByOwner_Id(user.getId()).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setOwner(user);
            cart.setCreateTime(new Timestamp(System.currentTimeMillis()));
            cart.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            return cartRepository.save(cart);
        });
    }

    public Cart updateCart(User user, UpdateCartRequest request) {
        Cart cart = getCartUser(user);
        List<CartItem> items = request.getItems().stream().map(
                item -> {
                    CartItem cartItem = new CartItem();
                    cartItem.setCart(cart);
                    cartItem.setProduct(productRepository.findById(item.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
                    cartItem.setQuantity(item.getAmount());
                    cartItem.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    cartItem.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    return cartItem;
                }
        ).collect(Collectors.toList());
        cart.setItems(new HashSet<>(items));
        cart.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return cartRepository.save(cart);
    }
}
