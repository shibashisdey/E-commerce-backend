package com.ecommerce.demo.dto.seller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SellerResponse {

    private String sellerId;
    private String sellerName;
    private String username;
    private String companyName;
    private String sellerAddress;
    private String email;
    private String Sphone;
    private Boolean isVerified;
    private Double rating;
}
