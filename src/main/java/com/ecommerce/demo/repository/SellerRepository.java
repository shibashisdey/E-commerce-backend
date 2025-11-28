package com.ecommerce.demo.repository;

import com.ecommerce.demo.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, String> {

    Optional<Seller> findByEmail(String email);

    Optional<Seller> findBySellerName(String sellerName);

    Optional<Seller> findByUsername(String username);
}
