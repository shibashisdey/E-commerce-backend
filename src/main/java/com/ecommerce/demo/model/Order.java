package com.ecommerce.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Double totalAmount;

    private String shippingAddress;

    private String paymentMethod;   // COD / CARD / UPI
    private String paymentStatus;   // pending / success / failed
    private String orderStatus;     // pending / shipped / delivered

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
}

