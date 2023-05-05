package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.models.entity.Notification;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.NotificationRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService extends BaseService<Notification, Long> {
    private final NotificationRepository notificationRepository;
    NotificationService(NotificationRepository repository) {
        super(repository);
        this.notificationRepository = repository;
    }

    public List<Notification> getNotifications(Long userId) {
        return notificationRepository.findAllByUserId(userId);
    }

    public Notification readNotification(User user, Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        if(notification.getUser().getId() != user.getId()) {
            throw new ResourceNotFoundException("Notification not found");
        }
        else {
            notification.setRead(true);
            return notificationRepository.save(notification);
        }

    }
}
