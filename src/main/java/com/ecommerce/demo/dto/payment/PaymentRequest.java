package com.ecommerce.demo.dto.payment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotBlank
    private String orderId;

    @NotBlank
    private String paymentMethod;

    private String transactionId; // Can be nullable for some payment methods like COD
}
