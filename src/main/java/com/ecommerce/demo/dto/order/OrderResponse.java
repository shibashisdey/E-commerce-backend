package com.ecommerce.demo.dto.order;


import lombok.Data;
import java.util.List;

@Data
public class OrderResponse {

    private String orderId;
    private String userId;
    private Double totalAmount;
    private String shippingAddress;
    private String paymentMethod;
    private String paymentStatus;
    private String orderStatus;

    private List<OrderItemResponse> items;
}
