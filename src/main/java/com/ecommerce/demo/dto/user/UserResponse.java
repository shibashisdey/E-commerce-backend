package com.ecommerce.demo.dto.user;

import lombok.Data;

@Data
public class UserResponse {

    private String userId;
    private String fullName;
    private String username;
    private String email;
    private String mobileNo;

    private Boolean isVerified;
    private String role;
}
