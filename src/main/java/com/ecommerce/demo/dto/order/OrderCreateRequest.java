package com.ecommerce.demo.dto.order;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreateRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String shippingAddress;

    @NotBlank
    private String paymentMethod;  // COD / CARD / UPI

    @NotNull
    private List<OrderItemRequest> items;
}
