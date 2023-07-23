package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.config.exception.BusinessException;
import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.*;
import com.example.agricultural_product_store.models.entity.ShippingAddress;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.ShippingAddressRepository;
import com.example.agricultural_product_store.repositories.UserRepository;
import com.example.agricultural_product_store.services.template.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.agricultural_product_store.config.Constants.USER_PASSWORD_NOT_MATCH;

@Service
public class UserServiceImpl extends BaseService<User, Long> implements UserService {

    private final UserRepository userRepository;
    private final ShippingAddressRepository shippingAddressRepository;

    private final PasswordEncoder passwordEncoder;
    UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, ShippingAddressRepository shippingAddressRepository) {
        super(repository);
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.shippingAddressRepository = shippingAddressRepository;
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUserInfo(Authentication authentication, UpdateUserInfoRequest request) {
        User user = getUserByUsername(authentication.getName()).orElseThrow(
                () -> new BusinessException("User not found")
        );
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDob(request.getDob());
        user.setAvatar(request.getAvatar());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setLocation(request.getLocation());
        user.setGender(request.getGender());
        user.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);
        return user;
    }

    public User updateAvatar(User user, UpdateAvatarRequest request) {
        user.setAvatar(request.getAvatar());
        return user;
    }

    public User changePassword(User user, ChangePasswordRequest request) {
        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BusinessException(USER_PASSWORD_NOT_MATCH);        }
        else {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            user.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
            return userRepository.save(user);
        }
    }

    public List<ShippingAddress> getShippingAddress(Authentication authentication) {
        User user = getUserByUsername(authentication.getName()).orElseThrow(
                () -> new BusinessException("User not found")
        );
        return shippingAddressRepository.findAllByUserId(user.getId());
    }

    public ShippingAddress createShippingAddress(Authentication authentication, CreateShippingAddressRequest request) {
        User user = getUserByUsername(authentication.getName()).orElseThrow(
                () -> new BusinessException("User not found")
        );
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress(request.getAddress());
        shippingAddress.setUser(user);
        shippingAddress.setName(request.getName());
        shippingAddress.setPhoneNumber(request.getPhoneNumber());
        return shippingAddressRepository.save(shippingAddress);
    }

    public ShippingAddress updateShippingAddress(Authentication authentication, UpdateShippingAddressRequest request) {
        User user = getUserByUsername(authentication.getName()).orElseThrow(
                () -> new BusinessException("User not found")
        );
        ShippingAddress shippingAddress = shippingAddressRepository.findById(request.getId()).orElseThrow(
                () -> new BusinessException("Shipping address not found")
        );
        if(shippingAddress.getUser().getId() != user.getId()) {
            throw new BusinessException("Shipping address not found");
        }
        shippingAddress.setAddress(request.getAddress());
        shippingAddress.setName(request.getName());
        shippingAddress.setPhoneNumber(request.getPhoneNumber());
        return shippingAddressRepository.save(shippingAddress);
    }

    public ShippingAddress deleteShippingAddress(Authentication authentication, Long id) {
        User user = getUserByUsername(authentication.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        ShippingAddress shippingAddress = shippingAddressRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Shipping address not found")
        );
        if(shippingAddress.getUser().getId() != user.getId()) {
            throw new ResourceNotFoundException("Shipping address not found");
        }
        shippingAddressRepository.delete(shippingAddress);
        return shippingAddress;
    }
}
