package com.example.agricultural_product_store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    @Email
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String location;
    private int gender;
    private Date dob;


}
