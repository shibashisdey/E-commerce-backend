package com.ecommerce.demo.dto.product;


import lombok.Data;

@Data
public class ProductResponse {

    private String productId;
    private String pname;
    private Double price;
    private Double discountPrice;
    private String pdescription;
    private String pimage;
    private Integer pQuantity;
    private String category;
    private String brand;
    private String sku;
    private Double rating;

    private String sellerId;
    private String sellerName;
}
