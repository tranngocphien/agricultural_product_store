package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.UpdateCartRequest;
import com.example.agricultural_product_store.dto.response.CartResponse;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.Cart;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.services.CartService;
import com.example.agricultural_product_store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public CartController(CartService cartService, UserService userService, ModelMapper modelMapper) {
        this.cartService = cartService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseData<CartResponse> getCart(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseData.onSuccess(modelMapper.map(cartService.getCartUser(user), CartResponse.class));
    }

    @PostMapping("/update")
    public ResponseData<Cart> updateCart(@RequestBody UpdateCartRequest request, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseData.onSuccess(cartService.updateCart(user, request));
    }
}
