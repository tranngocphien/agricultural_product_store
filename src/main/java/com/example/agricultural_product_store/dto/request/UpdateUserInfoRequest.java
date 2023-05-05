package com.example.agricultural_product_store.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateUserInfoRequest {
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String location;
    private int gender;
    private Date dob;
}
