package com.ecommerce.demo.dto.user;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserVerificationRequest {

    @NotBlank
    private String token;
}
