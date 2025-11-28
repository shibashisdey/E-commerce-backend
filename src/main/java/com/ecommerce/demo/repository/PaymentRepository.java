package com.ecommerce.demo.repository;

import com.ecommerce.demo.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    List<Payment> findByUserUserId(String userId);

    List<Payment> findByOrderOrderId(String orderId);
}
