package com.ecommerce.demo.dto.seller;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SellerRegisterRequest {

    @NotBlank
    private String sellerName;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String companyName;

    @NotBlank
    private String sellerAddress;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String Sphone;

    private String gstNumber;
}
