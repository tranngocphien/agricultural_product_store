package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.*;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.dto.response.UserResponse;
import com.example.agricultural_product_store.models.entity.ShippingAddress;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/account")
public class AccountController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public AccountController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/info")
    public ResponseData<UserResponse> getUserInfo(Authentication authentication) {
        final User user = userService.getUserByUsername(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseData.onSuccess(modelMapper.map(user, UserResponse.class));
    }

    @PostMapping("update-info")
    public ResponseData<UserResponse> updateUserInfo(Authentication authentication, UpdateUserInfoRequest request) {
        final User user = userService.getUserByUsername(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseData.onSuccess(modelMapper.map(userService.updateUserInfo(user, request), UserResponse.class));
    }

    @PostMapping("update-avatar")
    public ResponseData<UserResponse> updateAvatar(Authentication authentication, UpdateAvatarRequest request) {
        final User user = userService.getUserByUsername(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseData.onSuccess(modelMapper.map(userService.updateAvatar(user, request), UserResponse.class));
    }

    @PostMapping("/change-password")
    public ResponseData<UserResponse> changePassword(Authentication authentication, ChangePasswordRequest request) {
        final User user = userService.getUserByUsername(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseData.onSuccess(modelMapper.map(userService.changePassword(user, request), UserResponse.class));
    }
    @GetMapping("/shipping-address")
    public ResponseData<List<ShippingAddress>> getShippingAddress(Authentication authentication) {
        return ResponseData.onSuccess(userService.getShippingAddress(authentication));
    }

    @PostMapping("/shipping-address/create")
    public ResponseData<ShippingAddress> createShippingAddress(Authentication authentication, CreateShippingAddressRequest request) {
        return ResponseData.onSuccess(userService.createShippingAddress(authentication, request));
    }

    @PostMapping("/shipping-address/update")
    public ResponseData<ShippingAddress> update(Authentication authentication, UpdateShippingAddressRequest request) {
        return ResponseData.onSuccess(userService.updateShippingAddress(authentication, request));
    }

    @PostMapping("/shipping-address/delete/{id}")
    public ResponseData<ShippingAddress> delete(Authentication authentication, @PathVariable Long id) {
        return ResponseData.onSuccess(userService.deleteShippingAddress(authentication, id));
    }


}
