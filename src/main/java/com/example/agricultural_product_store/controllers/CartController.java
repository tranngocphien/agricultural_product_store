package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.UpdateCartRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.Cart;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.services.CartService;
import com.example.agricultural_product_store.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseData<Cart> getCart(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseData.onSuccess(cartService.getCartUser(user));
    }

    @PostMapping("/update")
    public ResponseData<Cart> updateCart(UpdateCartRequest request, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseData.onSuccess(cartService.updateCart(user, request));
    }
}
