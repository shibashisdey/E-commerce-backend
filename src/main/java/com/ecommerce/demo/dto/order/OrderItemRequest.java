package com.ecommerce.demo.dto.order;



import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequest {

    @NotNull
    private String productId;

    @NotNull
    private Integer quantity;
}
