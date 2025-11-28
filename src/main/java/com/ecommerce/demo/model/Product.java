package com.ecommerce.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productId;

    private String pname;
    private Double price;
    private Double discountPrice;

    @Column(length = 500)
    private String pdescription;

    private String pimage;  // You can store URL or path

    private Integer pQuantity;

    private String category;
    private String brand;
    private String sku;
    private Double rating;

    @ManyToOne
    @JoinColumn(name = "sellerId")
    private Seller seller;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}

