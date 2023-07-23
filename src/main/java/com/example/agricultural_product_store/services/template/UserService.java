package com.example.agricultural_product_store.services.template;

import com.example.agricultural_product_store.config.exception.BusinessException;
import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.*;
import com.example.agricultural_product_store.models.entity.ShippingAddress;
import com.example.agricultural_product_store.models.entity.User;
import org.springframework.security.core.Authentication;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.agricultural_product_store.config.Constants.USER_PASSWORD_NOT_MATCH;

public interface UserService extends BaseService<User, Long> {
    public Optional<User> getUserByUsername(String username);
    public User updateUserInfo(Authentication authentication, UpdateUserInfoRequest request);
    public User updateAvatar(User user, UpdateAvatarRequest request);
    public User changePassword(User user, ChangePasswordRequest request);
    public List<ShippingAddress> getShippingAddress(Authentication authentication);
    public ShippingAddress createShippingAddress(Authentication authentication, CreateShippingAddressRequest request);
    public ShippingAddress updateShippingAddress(Authentication authentication, UpdateShippingAddressRequest request);
    public ShippingAddress deleteShippingAddress(Authentication authentication, Long id);
}
