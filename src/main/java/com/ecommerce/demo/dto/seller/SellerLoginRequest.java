package com.ecommerce.demo.dto.seller;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SellerLoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
