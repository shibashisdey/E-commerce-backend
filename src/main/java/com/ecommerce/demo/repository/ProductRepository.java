package com.ecommerce.demo.repository;

import com.ecommerce.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByPnameContainingIgnoreCase(String keyword);

    List<Product> findByCategory(String category);

    List<Product> findBySellerSellerId(String sellerId);
}
