package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.dto.response.UserResponse;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
