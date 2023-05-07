package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.*;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.dto.response.ShippingAddressResponse;
import com.example.agricultural_product_store.dto.response.UserResponse;
import com.example.agricultural_product_store.models.entity.ShippingAddress;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseData<UserResponse> updateUserInfo(Authentication authentication, @RequestBody UpdateUserInfoRequest request) {
        return ResponseData.onSuccess(modelMapper.map(userService.updateUserInfo(authentication, request), UserResponse.class));
    }

    @PostMapping("update-avatar")
    public ResponseData<UserResponse> updateAvatar(Authentication authentication,@RequestBody UpdateAvatarRequest request) {
        final User user = userService.getUserByUsername(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseData.onSuccess(modelMapper.map(userService.updateAvatar(user, request), UserResponse.class));
    }

    @PostMapping("/change-password")
    public ResponseData<UserResponse> changePassword(Authentication authentication,@RequestBody ChangePasswordRequest request) {
        final User user = userService.getUserByUsername(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseData.onSuccess(modelMapper.map(userService.changePassword(user, request), UserResponse.class));
    }
    @GetMapping("/shipping-address")
    public ResponseData<List<ShippingAddressResponse>> getShippingAddress(Authentication authentication) {
        return ResponseData.onSuccess(userService.getShippingAddress(authentication).stream().map(
                shippingAddress -> modelMapper.map(shippingAddress, ShippingAddressResponse.class)
        ).collect(Collectors.toList()));
    }

    @PostMapping("/shipping-address/create")
    public ResponseData<ShippingAddressResponse> createShippingAddress(Authentication authentication, @RequestBody CreateShippingAddressRequest request) {
        return ResponseData.onSuccess(modelMapper.map(userService.createShippingAddress(authentication, request), ShippingAddressResponse.class));
    }

    @PostMapping("/shipping-address/update")
    public ResponseData<ShippingAddressResponse> update(Authentication authentication,@RequestBody UpdateShippingAddressRequest request) {
        return ResponseData.onSuccess(modelMapper.map(userService.updateShippingAddress(authentication, request), ShippingAddressResponse.class));
    }

    @DeleteMapping("/shipping-address/delete/{id}")
    public ResponseData<ShippingAddress> delete(Authentication authentication, @PathVariable Long id) {
        return ResponseData.onSuccess(userService.deleteShippingAddress(authentication, id));
    }


}
