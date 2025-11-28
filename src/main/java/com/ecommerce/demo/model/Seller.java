package com.ecommerce.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sellers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String sellerId;

    private String sellerName;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    private String companyName;
    private String sellerAddress;
    @Column(unique = true)
    private String email;
    private String Sphone;
    private String gstNumber;

    @Builder.Default
    private Boolean isVerified = false;

    private Double rating = 0.0;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Product> products;
}

