package com.ecommerce.demo.dto.payment;



import lombok.Data;

@Data
public class PaymentResponse {

    private String paymentId;
    private String orderId;
    private String userId;
    private Double amount;
    private String paymentMethod;
    private String transactionId;
    private String status;
}
