package com.ecommerce.demo.repository;

import com.ecommerce.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUserUserId(String userId);

    List<Order> findByOrderStatus(String status);
}
