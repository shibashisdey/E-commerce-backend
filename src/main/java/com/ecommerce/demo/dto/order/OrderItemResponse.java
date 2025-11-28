package com.ecommerce.demo.dto.order;


import lombok.Data;

@Data
public class OrderItemResponse {

    private String productId;
    private String pname;
    private Integer quantity;
    private Double price;
}
