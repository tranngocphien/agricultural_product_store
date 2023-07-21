package com.example.agricultural_product_store.dto.response;
import com.example.agricultural_product_store.models.entity.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String location;
    private String avatar;
    private int gender;
    private Date dob;
    private List<Role> roles;
}
