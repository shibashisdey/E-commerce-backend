package com.ecommerce.demo.dto.user;



import lombok.Data;

@Data
public class UserVerificationResponse {

    private String userId;
    private Boolean verified;
    private String message;
}
