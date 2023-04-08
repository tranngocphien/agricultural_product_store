package com.example.agricultural_product_store.services;

import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService extends BaseService<User, Long>{
    AccountService(UserRepository repository) {
        super(repository);
    }
}
