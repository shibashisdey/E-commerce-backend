package com.ecommerce.demo.dto.product;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCreateRequest {

    @NotBlank
    private String pname;

    @NotNull
    private Double price;

    private Double discountPrice;

    @NotBlank
    private String pdescription;

    private String pimage;

    @NotNull
    private Integer pQuantity;

    @NotBlank
    private String category;

    private String brand;

    private String sku;

    @NotBlank
    private String sellerId;
}
