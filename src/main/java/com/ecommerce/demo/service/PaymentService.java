package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.payment.PaymentRequest;
import com.ecommerce.demo.dto.payment.PaymentResponse;
import com.ecommerce.demo.model.Order;
import com.ecommerce.demo.model.Payment;
import com.ecommerce.demo.repository.OrderRepository;
import com.ecommerce.demo.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public PaymentResponse makePayment(PaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // In a real scenario, you might have more complex logic to check if payment is already made.
        
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setUser(order.getUser());
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionId(request.getTransactionId());
        payment.setStatus("PAID"); // Assuming payment is successful immediately

        Payment savedPayment = paymentRepository.save(payment);

        // Update order status
        order.setPaymentStatus("PAID");
        order.setOrderStatus("PROCESSING"); // Move order to next stage
        orderRepository.save(order);

        return mapToResponse(savedPayment);
    }

    public List<PaymentResponse> getPaymentsByUser(String userId) {
        return paymentRepository.findByUserUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PaymentResponse> getPaymentsByOrder(String orderId) {
        return paymentRepository.findByOrderOrderId(orderId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PaymentResponse mapToResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(payment.getPaymentId());
        response.setOrderId(payment.getOrder().getOrderId());
        response.setUserId(payment.getUser().getUserId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setTransactionId(payment.getTransactionId());
        response.setStatus(payment.getStatus());
        return response;
    }
}
