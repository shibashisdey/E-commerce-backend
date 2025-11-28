package com.ecommerce.demo.controller;

import com.ecommerce.demo.dto.AuthResponse;
import com.ecommerce.demo.dto.seller.SellerRegisterRequest;
import com.ecommerce.demo.dto.seller.SellerResponse;
import com.ecommerce.demo.dto.seller.SellerLoginRequest;
import com.ecommerce.demo.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/register")
    public ResponseEntity<SellerResponse> registerSeller(@Valid @RequestBody SellerRegisterRequest request) {
        SellerResponse seller = sellerService.registerSeller(request);
        return ResponseEntity.ok(seller);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(@Valid @RequestBody SellerLoginRequest request) {
        AuthResponse response = sellerService.loginSeller(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<SellerResponse> getSellerById(@PathVariable String sellerId) {
        SellerResponse seller = sellerService.getSellerById(sellerId);
        return ResponseEntity.ok(seller);
    }
}
