package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.Notification;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.services.NotificationService;
import com.example.agricultural_product_store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(NotificationService service, UserService userService) {
        this.notificationService = service;
        this.userService = userService;
    }

    @GetMapping()
    public ResponseData<List<Notification>> getNotifications(Authentication authentication) {
        final User user = userService.getUserByUsername(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseData.onSuccess(notificationService.getNotifications(user.getId()));
    }

    @PostMapping("/read")
    public ResponseData<Notification> readNotification(Authentication authentication, Long id) {
        final User user = userService.getUserByUsername(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseData.onSuccess(notificationService.readNotification(user, id));
    }

}
